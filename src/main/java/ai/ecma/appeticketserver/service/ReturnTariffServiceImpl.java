package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Event;
import ai.ecma.appeticketserver.entity.ReturnTariff;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.ReturnTariffMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.ReturnTariffDto;
import ai.ecma.appeticketserver.repository.EventRepo;
import ai.ecma.appeticketserver.repository.ReturnTariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReturnTariffServiceImpl implements ReturnTariffService {
    private final ReturnTariffRepository returnTariffRepository;
    private final ReturnTariffMapper returnTariffMapper;
    private final EventRepo eventRepository;

    @Override
    public ApiResult<List<ReturnTariffDto>> getAllByEvent(UUID eventId) {
        List<ReturnTariffDto> returnTariffDtoList = returnTariffRepository.findAllByEventIdOrderByToTimeDesc(eventId).stream().map(returnTariffMapper::toDto).collect(Collectors.toList());
        return ApiResult.successResponse(returnTariffDtoList);
    }

    @Override
    public ApiResult<?> addTariffToEvent(ReturnTariffDto tariffDto) {
        Event event = eventRepository.findById(tariffDto.getEventId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("EVENT_NOT_FOUND")));

        if (returnTariffRepository.existsByEventIdAndToTime(event.getId(), tariffDto.getToTime()))
            throw RestException.restThrow(MessageService.getMessage("RETURN_TARIFF_DUPLICATE"), HttpStatus.CONFLICT);

        returnTariffRepository.save(
                new ReturnTariff(tariffDto.getToTime(), tariffDto.getPercent(), event, tariffDto.isActive())
        );

        return ApiResult.successResponse(MessageService.getMessage("RETURN_TARIFF_SAVED"));
    }

    @Override
    public ApiResult<?> changeStatus(UUID id) {
        ReturnTariff returnTariff = returnTariffRepository.findById(id).orElseThrow(() -> RestException.notFound(MessageService.getMessage("RETURN_TARIFF_NOT_FOUND")));
        returnTariff.setActive(!returnTariff.isActive());
        returnTariffRepository.save(returnTariff);
        return ApiResult.successResponse(MessageService.getMessage("RETURN_TARIFF_EDITED"));
    }

    @Override
    public ApiResult<?> edit(UUID id, ReturnTariffDto tariffDto) {
        ReturnTariff returnTariff = returnTariffRepository.findById(id).orElseThrow(() -> RestException.notFound(MessageService.getMessage("RETURN_TARIFF_NOT_FOUND")));

        if (returnTariffRepository.existsByEventIdAndToTimeAndIdNot(tariffDto.getEventId(), tariffDto.getToTime(), id))
            throw RestException.restThrow(MessageService.getMessage("RETURN_TARIFF_DUPLICATE"), HttpStatus.CONFLICT);


        returnTariff.setPercent(tariffDto.getPercent());
        returnTariff.setToTime(tariffDto.getToTime());
        returnTariffRepository.save(returnTariff);

        return ApiResult.successResponse(MessageService.getMessage("RETURN_TARIFF_EDITED"));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            returnTariffRepository.deleteById(id);
            return ApiResult.successResponse(MessageService.getMessage("RETURN_TARIFF_DELETED"));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw RestException.notFound("RETURN_TARIFF_NOT_FOUND");
        }
    }
}

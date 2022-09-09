package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.ReturnTariffDto;
import ai.ecma.appeticketserver.service.ReturnTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReturnTariffControllerImpl implements ReturnTariffController{
    private final ReturnTariffService returnTariffService;

    @Override
    public ApiResult<List<ReturnTariffDto>> getAllByEvent(UUID eventId) {
        return returnTariffService.getAllByEvent(eventId);
    }

    @Override
    public ApiResult<?> addTariffToEvent(ReturnTariffDto tariffDto) {
        return returnTariffService.addTariffToEvent(tariffDto);
    }

    @Override
    public ApiResult<?> changeStatus(UUID id) {
        return returnTariffService.changeStatus(id);
    }

    @Override
    public ApiResult<?> edit(UUID id, ReturnTariffDto tariffDto) {
        return returnTariffService.edit(id, tariffDto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return returnTariffService.delete(id);
    }
}

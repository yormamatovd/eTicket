package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.SeatTemplate;
import ai.ecma.appeticketserver.entity.TemplateChair;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.ChairCreateDto;
import ai.ecma.appeticketserver.payload.req.RowCreateDto;
import ai.ecma.appeticketserver.payload.req.SectorCreateDto;
import ai.ecma.appeticketserver.repository.SeatTemplateRepository;
import ai.ecma.appeticketserver.repository.TemplateChairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChairServiceImpl implements ChairService {
    private final TemplateChairRepository chairRepository;
    private final SeatTemplateRepository templateRepository;

    @Override
    public ApiResult<?> createSector(SectorCreateDto chairDto) {
        SeatTemplate template = templateRepository.findById(chairDto.getTemplateId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TEMPLATE_NOT_FOUND")));
        if (chairRepository.existsBySectorAndTemplateId(chairDto.getSectorName(), chairDto.getTemplateId()))
            throw RestException.restThrow(MessageService.getMessage("SECTOR_NAME_ALREADY_EXISTS"), HttpStatus.CONFLICT);

        List<TemplateChair> chairList = new ArrayList<>();
        for (int i = 0; i < chairDto.getRowCount(); i++) {
            for (int j = 0; j < chairDto.getChairCount(); j++) {
                chairList.add(new TemplateChair(template, (j + 1) + "", chairDto.getSectorName(), (i + 1) + "", chairDto.getPrice(), chairDto.getStatus()));
            }
        }
        chairRepository.saveAll(chairList);
        return ApiResult.successResponse(MessageService.getMessage("SECTOR_SAVED"));
    }

    @Override
    public ApiResult<?> createRow(RowCreateDto rowDto) {
        SeatTemplate template = templateRepository.findById(rowDto.getTemplateId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TEMPLATE_NOT_FOUND")));
        if (!chairRepository.existsBySectorAndTemplateId(rowDto.getSectorName(), rowDto.getTemplateId()))
            throw RestException.restThrow(MessageService.getMessage("SECTOR_NOT_FOUND"), HttpStatus.CONFLICT);

        String rowName;
        if (rowDto.getRowName() != null) {
            rowName = rowDto.getRowName();
            if (chairRepository.existsBySectorAndTemplateIdAndRow(rowDto.getSectorName(), template.getId(), rowDto.getRowName()))
                throw RestException.restThrow(MessageService.getMessage("ROW_ALREADY_EXISTS"), HttpStatus.CONFLICT);
        }else {
            long l = chairRepository.countRowByTemplateIdAndSector(template.getId(), rowDto.getSectorName());
            rowName = String.valueOf(l+1);
        }

        List<TemplateChair> chairList = new ArrayList<>();
        for (int i = 0; i < rowDto.getChairCount(); i++) {
            chairList.add(new TemplateChair(template, String.valueOf(i + 1), rowDto.getSectorName(), rowName, rowDto.getPrice(), rowDto.getStatus()));
        }
        chairRepository.saveAll(chairList);
        return ApiResult.successResponse(MessageService.getMessage("ROW_SAVED"));
    }

    @Override
    public ApiResult<?> createChair(ChairCreateDto chairDto) {
        SeatTemplate template = templateRepository.findById(chairDto.getTemplateId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TEMPLATE_NOT_FOUND")));
        if (!chairRepository.existsBySectorAndRowAndTemplateId(chairDto.getSectorName(), chairDto.getRowName(), template.getId())) {
            throw RestException.restThrow(MessageService.getMessage("ROW_NOT_FOUND"), HttpStatus.CONFLICT);
        }
        long max = chairRepository.getMaxChairByTemplateAndSectionAndRow(template.getId(), chairDto.getSectorName(), chairDto.getRowName());

        TemplateChair chair = new TemplateChair(
                template, String.valueOf(max + 1), chairDto.getSectorName(), chairDto.getRowName(), chairDto.getPrice(), chairDto.getStatus()
        );
        chairRepository.save(chair);

        return ApiResult.successResponse(MessageService.getMessage("CHAIR_SAVED"));
    }
}

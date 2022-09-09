package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.entity.SeatTemplate;
import ai.ecma.appeticketserver.entity.TemplateChair;
import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.TemplateChairMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.TemplateCreateDto;
import ai.ecma.appeticketserver.payload.req.TemplateEditeDto;
import ai.ecma.appeticketserver.payload.resp.ChairResDto;
import ai.ecma.appeticketserver.payload.resp.RowDto;
import ai.ecma.appeticketserver.payload.resp.SectorResDto;
import ai.ecma.appeticketserver.payload.resp.TemplateResDto;
import ai.ecma.appeticketserver.repository.AttachmentRepository;
import ai.ecma.appeticketserver.repository.TemplateChairRepository;
import ai.ecma.appeticketserver.repository.SeatTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService{
    private final SeatTemplateRepository templateRepository;
    private final TemplateChairRepository templateChairRepository;
    private final TemplateChairMapper chairMapper;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<List<TemplateResDto>> getAll() {
        return ApiResult.successResponse(templateRepository.allByMaxMin());
    }

    @Override
    public ApiResult<List<SectorResDto>> getAllSectorsByTemplate(UUID templateId) {
        return ApiResult.successResponse(templateRepository.getAllSectors(templateId));
    }

    @Override
    public ApiResult<List<RowDto>> getRowsBySector(UUID templateId, String sectorName) {
        List<TemplateChair> chairList = templateChairRepository.findAllBySectorAndTemplateIdOrderByRow(sectorName, templateId);

        Set<String> allRows = chairList.stream().map(TemplateChair::getRow).collect(Collectors.toSet());

//        List<RowDto> rowDtoList = new ArrayList<>();
//        for (String row : allRows) {
//            rowDtoList.add(new RowDto(row, chairList.stream().filter(chair -> chair.getRow().equals(row)).map(chairMapper::toDto).collect(Collectors.toList())));
//        }
//        return ApiResult.successResponse(rowDtoList);

        List<RowDto> rowDtoList = new ArrayList<>();
        for (String rowName : allRows) {
            List<ChairResDto> chairDtoList = new ArrayList<>();
            for (TemplateChair chair : chairList) {
                if (chair.getRow().equals(rowName)) {
                    chairDtoList.add(
                            chairMapper.toDto(chair)
                    );
                }
            }
            RowDto rowDto = new RowDto(rowName, chairDtoList);
            rowDtoList.add(rowDto);
        }
        return ApiResult.successResponse(rowDtoList);
    }

    @Override
    public ApiResult<?> add(TemplateCreateDto templateCreateDto) {
        Attachment schema = attachmentRepository.findById(templateCreateDto.getSchemaId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("SCHEMA_TON_FOUND")));
        SeatTemplate template = new SeatTemplate(templateCreateDto.getName(), schema);
        templateRepository.save(template);
        return ApiResult.successResponse(MessageService.getMessage("TEMPLATE_SUCCESS_SAVE"));
    }

    @Override
    public ApiResult<?> addSector(UUID templateId, String name) {
        SeatTemplate template = templateRepository.findById(templateId).orElseThrow(() -> RestException.notFound("TEMPLATE_NOT_FOUND"));

        return null;
    }

    @Override
    public ApiResult<?> edit(UUID templateId, TemplateEditeDto templateEditeDto) {
        SeatTemplate template = templateRepository.findById(templateId).orElseThrow(() -> RestException.notFound("TEMPLATE_NOT_FOUND"));
        List<TemplateChair> allByTemplateId = templateChairRepository.findAllByTemplateId(templateId);
        allByTemplateId.forEach(templateChair -> {
            templateChair.setPrice(templateEditeDto.getPrice() != null ? templateEditeDto.getPrice() : templateChair.getPrice());
            templateChair.setStatus(TicketStatusEnum.valueOf(templateEditeDto.getStatus()));
        });
        templateChairRepository.saveAll(allByTemplateId);
        return ApiResult.successResponse(MessageService.getMessage("TEMPLATE_SUCCESS_EDITED"));
    }
}

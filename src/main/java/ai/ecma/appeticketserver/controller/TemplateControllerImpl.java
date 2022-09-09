package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.TemplateCreateDto;
import ai.ecma.appeticketserver.payload.req.TemplateEditeDto;
import ai.ecma.appeticketserver.payload.resp.RowDto;
import ai.ecma.appeticketserver.payload.resp.SectorResDto;
import ai.ecma.appeticketserver.payload.resp.TemplateResDto;
import ai.ecma.appeticketserver.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TemplateControllerImpl implements TemplateController{
    private final TemplateService templateService;

    @Override
    public ApiResult<List<TemplateResDto>> getAll() {
        return templateService.getAll();
    }

    @Override
    public ApiResult<List<SectorResDto>> getAllSectorsByTemplate(UUID templateId) {
        return templateService.getAllSectorsByTemplate(templateId);
    }

    @Override
    public ApiResult<List<RowDto>> getRowsBySector(UUID templateId, String sectorName) {
        return templateService.getRowsBySector(templateId, sectorName);
    }

    @Override
    public ApiResult<?> add(TemplateCreateDto templateCreateDto) {
        return templateService.add(templateCreateDto);
    }

    @Override
    public ApiResult<?> addSector(UUID templateId, String name) {
        return templateService.addSector(templateId, name);
    }

    @Override
    public ApiResult<?> edit(UUID templateId, TemplateEditeDto templateEditeDto) {
        return templateService.edit(templateId, templateEditeDto);
    }
}

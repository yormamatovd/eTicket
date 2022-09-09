package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.TemplateCreateDto;
import ai.ecma.appeticketserver.payload.req.TemplateEditeDto;
import ai.ecma.appeticketserver.payload.resp.RowDto;
import ai.ecma.appeticketserver.payload.resp.SectorResDto;
import ai.ecma.appeticketserver.payload.resp.TemplateResDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * This interface not documented :(
 *
 * @author Muhammad Mo'minov
 * @since 23.12.2021
 */
public interface TemplateService {

    ApiResult<List<TemplateResDto>> getAll();

    ApiResult<List<SectorResDto>> getAllSectorsByTemplate(UUID templateId);

    ApiResult<List<RowDto>> getRowsBySector(UUID templateId, String sectorName);

    ApiResult<?> add(TemplateCreateDto templateCreateDto);

    ApiResult<?> addSector(UUID templateId, String name);

    ApiResult<?> edit(UUID templateId, TemplateEditeDto templateEditeDto);
}

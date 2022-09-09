package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.SeatReqDto;
import ai.ecma.appeticketserver.payload.req.TemplateCreateDto;
import ai.ecma.appeticketserver.payload.req.TemplateEditeDto;
import ai.ecma.appeticketserver.payload.resp.RowDto;
import ai.ecma.appeticketserver.payload.resp.SectorResDto;
import ai.ecma.appeticketserver.payload.resp.TemplateResDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(TemplateController.TEMPLATE_CONTROLLER)
public interface TemplateController {
    String TEMPLATE_CONTROLLER = AppConstant.BASE_PATH + "/template";

    @GetMapping
    ApiResult<List<TemplateResDto>> getAll();

    @GetMapping("/{templateId}")
    ApiResult<List<SectorResDto>> getAllSectorsByTemplate(@PathVariable UUID templateId);

    @GetMapping("/sector")
    ApiResult<List<RowDto>> getRowsBySector(@RequestParam UUID templateId, @RequestParam String sectorName);

    @PostMapping()
    ApiResult<?> add(@RequestBody @Valid TemplateCreateDto templateCreateDto);

    @PostMapping("/{templateId}")
    ApiResult<?> addSector(@PathVariable UUID templateId, @RequestBody @Valid String name);

    @PutMapping("/{templateId}")
    ApiResult<?> edit(@PathVariable UUID templateId, @RequestBody @Valid TemplateEditeDto templateEditeDto);
}

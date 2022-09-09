package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.ChairCreateDto;
import ai.ecma.appeticketserver.payload.req.RowCreateDto;
import ai.ecma.appeticketserver.payload.req.SectorCreateDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@RequestMapping(ChairController.CHAIR_CONTROLLER)
public interface ChairController {
    String CHAIR_CONTROLLER = AppConstant.BASE_PATH + "/chair";

    @PostMapping("/sector")
    ApiResult<?> createSector(@RequestBody @Valid SectorCreateDto chairDto);

    @PostMapping("/row")
    ApiResult<?> createRow(@RequestBody @Valid RowCreateDto rowCreateDto);

    @PostMapping("/chair")
    ApiResult<?> createChair(@RequestBody @Valid ChairCreateDto chairDto);
}

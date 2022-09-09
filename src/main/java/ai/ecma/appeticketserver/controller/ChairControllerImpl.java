package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.ChairCreateDto;
import ai.ecma.appeticketserver.payload.req.RowCreateDto;
import ai.ecma.appeticketserver.payload.req.SectorCreateDto;
import ai.ecma.appeticketserver.service.ChairService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChairControllerImpl implements ChairController{
    private final ChairService chairService;

    @Override
    public ApiResult<?> createSector(SectorCreateDto chairDto) {
        return chairService.createSector(chairDto);
    }

    @Override
    public ApiResult<?> createRow(RowCreateDto rowCreateDto) {
        return chairService.createRow(rowCreateDto);
    }

    @Override
    public ApiResult<?> createChair(ChairCreateDto chairDto) {
        return chairService.createChair(chairDto);
    }
}

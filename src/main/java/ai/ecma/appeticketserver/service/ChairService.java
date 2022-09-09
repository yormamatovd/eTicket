package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.ChairCreateDto;
import ai.ecma.appeticketserver.payload.req.RowCreateDto;
import ai.ecma.appeticketserver.payload.req.SectorCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ChairService {
    ApiResult<?> createSector(SectorCreateDto chairDto);

    ApiResult<?> createRow(RowCreateDto rowCreateDto);

    ApiResult<?> createChair(ChairCreateDto chairDto);
}

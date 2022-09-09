package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.PayTypeDto;

import java.util.List;
import java.util.UUID;

public interface PayTypeService {

    ApiResult<List<PayTypeDto>> getAll();

    ApiResult<PayTypeDto> getOne(UUID id);

    ApiResult<?> create(PayTypeDto payTypeDto);

    ApiResult<?> update(UUID id, PayTypeDto payTypeDto);

    ApiResult<?> delete(UUID id);

}

package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.ReturnTariffDto;

import java.util.List;
import java.util.UUID;

public interface ReturnTariffService {
    ApiResult<List<ReturnTariffDto>> getAllByEvent(UUID eventId);

    ApiResult<?> addTariffToEvent(ReturnTariffDto tariffDto);

    ApiResult<?> changeStatus(UUID id);

    ApiResult<?> edit(UUID id, ReturnTariffDto tariffDto);

    ApiResult<?> delete(UUID id);
}

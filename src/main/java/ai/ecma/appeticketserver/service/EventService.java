package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Event;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EventService {
    ApiResult<CustomPage<EventRespDto>> getAll(Integer page, Integer size);

    ApiResult<EventRespDto> get(UUID id);

    ApiResult<?> create(EventReqDto dto);

    ApiResult<?> edit(UUID id, EventReqDto dto);

    ApiResult<?> delete(UUID id);

    CustomPage<EventRespDto> makeCustomPage(Page<Event> event);

    ApiResult<CustomPage<EventRespDto>> getAllByCategory(UUID categoryId, Integer page, Integer size);
}

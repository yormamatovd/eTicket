package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Event;
import ai.ecma.appeticketserver.entity.EventSession;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.req.EventSessionReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.payload.resp.EventSessionRespDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EventSessionService {
    ApiResult<CustomPage<EventSessionRespDto>> getAll(Integer page, Integer size);

    ApiResult<EventSessionRespDto> get(UUID id);

    ApiResult<?> create(EventSessionReqDto dto);

    ApiResult<?> edit(UUID id, EventSessionReqDto dto);

    ApiResult<?> delete(UUID id);

    CustomPage<EventSessionRespDto> makeCustomPage(Page<EventSession> eventSessionService);

}

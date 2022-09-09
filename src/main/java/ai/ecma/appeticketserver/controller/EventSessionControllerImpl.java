package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.req.EventSessionReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.payload.resp.EventSessionRespDto;
import ai.ecma.appeticketserver.service.EventService;
import ai.ecma.appeticketserver.service.EventSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;



@RestController
@RequiredArgsConstructor
public class EventSessionControllerImpl implements EventSessionController{
    private final EventSessionService eventSessionService;

    @Override
    public ApiResult<CustomPage<EventSessionRespDto>> getAll(Integer page, Integer size) {
        return eventSessionService.getAll(page, size);
    }

    @Override
    public ApiResult<EventSessionRespDto> get(UUID id) {
        return eventSessionService.get(id);
    }

    @Override
    public ApiResult<?> create(EventSessionReqDto dto) {
        return eventSessionService.create(dto);
    }

    @Override
    public ApiResult<?> edit(UUID id, EventSessionReqDto dto) {
        return eventSessionService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return eventSessionService.delete(id);
    }
}

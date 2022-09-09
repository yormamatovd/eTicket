package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController{
    private final EventService eventService;

    @Override
    public ApiResult<CustomPage<EventRespDto>> getAll(Integer page, Integer size) {
        return eventService.getAll(page, size);
    }

    @Override
    public ApiResult<EventRespDto> get(UUID id) {
        return eventService.get(id);
    }

    @Override
    public ApiResult<?> create(EventReqDto dto) {
        return eventService.create(dto);
    }

    @Override
    public ApiResult<?> edit(UUID id, EventReqDto dto) {
        return eventService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return eventService.delete(id);
    }

    @Override
    public ApiResult<CustomPage<EventRespDto>> getAllByCategory(UUID categoryId, Integer page, Integer size) {
        return eventService.getAllByCategory(categoryId,page,size);
    }
}

package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.req.EventSessionReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.payload.resp.EventSessionRespDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.*;

@RequestMapping(EventSessionController.EVENT_SESSION_CONTROLLER)
public interface EventSessionController {
    String EVENT_SESSION_CONTROLLER = BASE_PATH + "/eventSession";

    @GetMapping
    ApiResult<CustomPage<EventSessionRespDto>> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/{id}")
    ApiResult<EventSessionRespDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<?> create(@RequestBody @Valid EventSessionReqDto dto);

    @PutMapping("/{id}")
    ApiResult<?> edit(@PathVariable UUID id, @RequestBody @Valid EventSessionReqDto dto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}

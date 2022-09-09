package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.*;

@RequestMapping(EventController.EVENT_CONTROLLER)
public interface EventController {
    String EVENT_CONTROLLER = BASE_PATH + "/event";

    @GetMapping
    ApiResult<CustomPage<EventRespDto>> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                               @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/{id}")
    ApiResult<EventRespDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<?> create(@RequestBody @Valid EventReqDto dto);

    @PutMapping("/{id}")
    ApiResult<?> edit(@PathVariable UUID id, @RequestBody @Valid EventReqDto dto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @GetMapping("/get/{categoryId}")
    ApiResult<CustomPage<EventRespDto>> getAllByCategory(@PathVariable(value = "categoryId") UUID categoryId,
                                               @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                               @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);



}

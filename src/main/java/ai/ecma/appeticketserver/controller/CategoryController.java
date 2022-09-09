package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_SIZE;


@RequestMapping(CategoryController.CATEGORY_CONTROLLER)
public interface CategoryController {
    String CATEGORY_CONTROLLER = AppConstant.BASE_PATH + "/category";

    @GetMapping
    ApiResult<CustomPage<CategoryRespDto>> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                  @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/{id}")
    ApiResult<CategoryRespDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<?> create(@RequestBody @Valid CategoryReqDto categoryDto);

    @PutMapping("/{id}")
    ApiResult<?> edit(@RequestBody @Valid CategoryReqDto categoryDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}

package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.controller.CategoryController;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;

    @Override
    public ApiResult<CustomPage<CategoryRespDto>> getAll(Integer page, Integer size) {
        return categoryService.getAll(page, size);
    }

    @Override
    public ApiResult<CategoryRespDto> get(UUID id) {
        return categoryService.get(id);
    }

    @Override
    public ApiResult<?> create(CategoryReqDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @Override
    public ApiResult<?> edit(CategoryReqDto categoryDto, UUID id) {
        return categoryService.edit(categoryDto, id);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return categoryService.delete(id);
    }
}

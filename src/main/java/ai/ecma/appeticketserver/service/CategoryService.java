package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    ApiResult<CustomPage<CategoryRespDto>> getAll(Integer page, Integer size);

    ApiResult<CategoryRespDto> get(UUID id);

    ApiResult<?> create(CategoryReqDto categoryDto);

    ApiResult<?> edit(CategoryReqDto categoryDto, UUID id);

    ApiResult<?> delete(UUID id);

    CustomPage<CategoryRespDto> makeCustomPage(Page<Category> page);
}

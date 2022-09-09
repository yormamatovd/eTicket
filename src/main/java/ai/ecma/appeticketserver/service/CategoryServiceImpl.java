package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.config.MessageConfig;
import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.CategoryMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MessageService messageService;

    @Override
    public ApiResult<CustomPage<CategoryRespDto>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Category> all = categoryRepository.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(all));
    }

    @Override
    public ApiResult<CategoryRespDto> get(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(categoryMapper.toCategoryRespDto(category));
    }

    @Override
    public ApiResult<?> create(CategoryReqDto categoryDto) {
        Category parentCategory = null;
        if (categoryDto.getParentCategoryId() != null)
            parentCategory = categoryRepository.findById(categoryDto.getParentCategoryId()).orElseThrow(() -> new RestException(messageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));

            boolean exists = categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId());
            if (exists)
                throw new RestException(MessageService.getMessage("CATEGORY_NAME_ALREADY_EXISTS"), HttpStatus.CONFLICT);

        Category category = new Category(categoryDto.getName(), parentCategory);
        categoryRepository.save(category);
        return ApiResult.successResponse(MessageService.getMessage("CATEGORY_SAVED"));

//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        if (categoryDto.getParentCategoryId() != null) {
//            Category parentCategory = categoryRepository.findById(categoryDto.getParentCategoryId()).orElseThrow(() -> new RestException(messageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));
//            category.setParentCategory(parentCategory);
//        }
//        categoryRepository.save(category);
//        return ApiResult.successResponse(messageService.getMessage("CATEGORY_SAVED"));

    }

    @Override
    public ApiResult<?> edit(CategoryReqDto categoryDto, UUID id) {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }

    @Override
    public CustomPage<CategoryRespDto> makeCustomPage(Page<Category> page) {
        return new CustomPage<>(
                page.getContent().stream().map(categoryMapper::toCategoryRespDto).collect(Collectors.toList()),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}

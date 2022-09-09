package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;


@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface CategoryMapper {

    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    CategoryRespDto toCategoryRespDto(Category category);


}

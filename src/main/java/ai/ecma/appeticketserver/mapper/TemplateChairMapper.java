package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.TemplateChair;
import ai.ecma.appeticketserver.payload.resp.ChairResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateChairMapper {
    ChairResDto toDto(TemplateChair chair);
}

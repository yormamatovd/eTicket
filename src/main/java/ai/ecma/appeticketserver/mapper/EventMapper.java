package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.Event;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface EventMapper {
    @Mapping(source = "place.id", target = "placeId")
    @Mapping(source = "banner.id", target = "bannerId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "schema.id", target = "schemaId")
    EventRespDto toEventRespDto(Event event);
}

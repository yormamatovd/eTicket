package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.EventSession;
import ai.ecma.appeticketserver.payload.resp.EventSessionRespDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface EventSessionMapper {
    EventSessionRespDto toEventSessionRespDto(EventSession eventSession);
}

package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.ReturnTariff;
import ai.ecma.appeticketserver.payload.ReturnTariffDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReturnTariffMapper {
    @Mapping(target = "eventId", source = "event.id")
    ReturnTariffDto toDto(ReturnTariff returnTariff);

}

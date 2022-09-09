package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.PromoCode;
import ai.ecma.appeticketserver.payload.resp.PromoCodeRespDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface PromoCodeMapper {


    PromoCodeRespDto toPromoCodeDto(PromoCode promoCode);

    PromoCode toPromoCode(PromoCodeRespDto promoCodeRespDto);
}

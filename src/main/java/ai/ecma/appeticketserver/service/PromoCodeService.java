package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.PromoCode;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.resp.PromoCodeRespDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PromoCodeService {
    ApiResult<CustomPage<PromoCodeRespDto>> getAll(Integer page, Integer size);

    ApiResult<PromoCodeRespDto> get(UUID id);

    ApiResult<?> create(PromoCodeRespDto promoCodeRespDto);

    ApiResult<?> edit(PromoCodeRespDto promoCodeRespDto, UUID id);

    ApiResult<?> delete(UUID id);

    CustomPage<PromoCodeRespDto> makeCustomPage(Page<PromoCode> page);
}

package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.PromoCode;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.PromoCodeMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.resp.PromoCodeRespDto;
import ai.ecma.appeticketserver.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepo;
    private final MessageService messageService;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public ApiResult<CustomPage<PromoCodeRespDto>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PromoCode> all = promoCodeRepo.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(all));
    }

    @Override
    public ApiResult<PromoCodeRespDto> get(UUID id) {
        PromoCode promoCode = promoCodeRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(promoCodeMapper.toPromoCodeDto(promoCode));
    }


    @Override
    public ApiResult<?> create(PromoCodeRespDto promoCodeRespDto) {
        if (promoCodeRepo.existsPromoCodesByUserId(promoCodeRespDto.getUserId())) {
            PromoCode promoCode = promoCodeMapper.toPromoCode(promoCodeRespDto);
            PromoCode save = promoCodeRepo.save(promoCode);
            return ApiResult.successResponse(save);
        } else {
            throw new RestException(messageService.getMessage("PROMO_CODE_NOT_FOUND"), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ApiResult<?> edit(PromoCodeRespDto promoCodeRespDto, UUID id) {
        PromoCode promoCode = promoCodeRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("PROMO_CODE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        PromoCode promoCode = promoCodeRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("PROMO_CODE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        promoCodeRepo.delete(promoCode);
        return ApiResult.successResponse(messageService.getMessage("PROMO_CODE_DELETED"));
    }

    @Override
    public CustomPage<PromoCodeRespDto> makeCustomPage(Page<PromoCode> page) {
        return new CustomPage<>(
                page.getContent().stream().map(promoCodeMapper::toPromoCodeDto).collect(Collectors.toList()),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}

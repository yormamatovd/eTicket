package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.payload.resp.PromoCodeRespDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_SIZE;


@RequestMapping(PromoCodeController.PROMO_CODE_CONTROLLER)
public interface PromoCodeController {
    String PROMO_CODE_CONTROLLER = AppConstant.BASE_PATH + "/promoCode";

    @GetMapping
    ApiResult<CustomPage<PromoCodeRespDto>> getAll(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                   @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @GetMapping("/{id}")
    ApiResult<PromoCodeRespDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<?> create(@RequestBody @Valid PromoCodeRespDto categoryDto);

    @PutMapping("/{id}")
    ApiResult<?> edit(@RequestBody @Valid PromoCodeRespDto categoryDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

}

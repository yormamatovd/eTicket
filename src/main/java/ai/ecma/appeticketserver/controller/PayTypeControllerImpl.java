package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.PayType;
import ai.ecma.appeticketserver.enums.PayTypeEnum;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.PayTypeDto;
import ai.ecma.appeticketserver.service.PayTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PayTypeControllerImpl implements PayTypeController {

    private final PayTypeService payTypeService;

    @Override
    public ApiResult<List<PayTypeDto>> getAll() {
        return payTypeService.getAll();
    }

    @Override
    public ApiResult<PayTypeDto> getOne(UUID id) {
        return payTypeService.getOne(id);
    }





    @Override
    public ApiResult<?> create(PayTypeDto payTypeDto) {
        return payTypeService.create(payTypeDto);
    }
//
//    @Override
//    public ApiResult<?> update(UUID id, PayTypeReqDto payTypeReqDto) {
//        return payTypeService.update(id, payTypeReqDto);
//    }
//
//    @Override
//    public ApiResult<?> delete(UUID id) {
//        return payTypeService.delete(id);
    }
//

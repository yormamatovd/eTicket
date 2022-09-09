package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.PayType;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.PayTypeDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(PayTypeController.PAY_TYPE_CONTROLLER)
public interface PayTypeController {

    String PAY_TYPE_CONTROLLER = AppConstant.BASE_PATH + "/pay-type";

    @GetMapping
    ApiResult<List<PayTypeDto>> getAll();

    @GetMapping("/{id}")
    ApiResult<PayTypeDto> getOne(@PathVariable UUID id);

    @PostMapping()
    ApiResult<?> create(@RequestBody @Valid PayTypeDto payTypeDto);

//    @PutMapping("/{id}")
//    ApiResult<?> update(@PathVariable UUID id,@RequestBody @Valid PayTypeReqDto payTypeReqDto);
//
//    @DeleteMapping("/{id}")
//    ApiResult<?> delete(@PathVariable UUID id);


}

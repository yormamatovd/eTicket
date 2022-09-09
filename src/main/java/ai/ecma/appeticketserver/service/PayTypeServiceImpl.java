package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.PayType;
import ai.ecma.appeticketserver.enums.PayTypeEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.req.PayTypeDto;
import ai.ecma.appeticketserver.repository.PayTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayTypeServiceImpl implements PayTypeService {

    private final PayTypeRepository payTypeRepository;
    private final MessageService messageService;

    @Override
    public ApiResult<List<PayTypeDto>> getAll() {
        List<PayType> payTypeList = payTypeRepository.findAll();
        List<PayTypeDto> payTypeDtos = new ArrayList<>();
        for (PayType payType : payTypeList) {
            PayTypeDto dto = new PayTypeDto(
                    payType.getId(),
                    payType.getName(),
                    payType.isActive()
            );
            payTypeDtos.add(dto);
        }
        return ApiResult.successResponse(payTypeDtos);
    }


    @Override
    public ApiResult<PayTypeDto> getOne(UUID id) {
        PayType payType = payTypeRepository.findById(id).orElseThrow(() ->
                new RestException(MessageService.getMessage("PAY_TYPE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(String.valueOf(payType));
    }


    @Override
    public ApiResult<?> create(PayTypeDto payTypeDto) {
        if (payTypeRepository.existsByName(payTypeDto.getName())) {
            throw RestException.restThrow(MessageService.getMessage("PAY_TYPE_ALL_READY_EXIST"), HttpStatus.CONFLICT);
        }
        PayType payType = new PayType();
//        payType.
        payTypeRepository.save(payType);
        return ApiResult.successResponse(MessageService.getMessage("PAY_TYPE_CREATED"));
    }


    @Override
    public ApiResult<?> update(UUID id, PayTypeDto payTypeDto) {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }
}



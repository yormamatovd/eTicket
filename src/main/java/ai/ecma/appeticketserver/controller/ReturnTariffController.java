package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.ReturnTariffDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(ReturnTariffController.RETURN_TARIFF_CONTROLLER)
public interface ReturnTariffController {
    String RETURN_TARIFF_CONTROLLER = AppConstant.BASE_PATH + "/return-tariff";

    @GetMapping("/event/{eventId}")
    ApiResult<List<ReturnTariffDto>> getAllByEvent(@PathVariable UUID eventId);

    @PostMapping
    ApiResult<?> addTariffToEvent(@RequestBody @Valid ReturnTariffDto tariffDto);

    @PutMapping("/status/{id}")
    ApiResult<?> changeStatus(@PathVariable UUID id);

    @PutMapping("/{id}")
    ApiResult<?> edit(@PathVariable UUID id, @RequestBody @Valid ReturnTariffDto tariffDto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}

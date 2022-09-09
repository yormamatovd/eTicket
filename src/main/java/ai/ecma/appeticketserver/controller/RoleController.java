package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.CategoryReqDto;
import ai.ecma.appeticketserver.payload.req.RoleReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_NUMBER;
import static ai.ecma.appeticketserver.utils.AppConstant.DEFAULT_PAGE_SIZE;


@RequestMapping(RoleController.ROLE_CONTROLLER)
public interface RoleController {
    String ROLE_CONTROLLER = AppConstant.BASE_PATH + "/role";



    @GetMapping("/{id}")
    ApiResult<RoleRespDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<?> create(@RequestBody @Valid RoleReqDto roleReqDto,
                        @RequestParam (value = "permissions") Set<PermissionEnum> permissionEnum);

    @PutMapping("/{id}")
    ApiResult<?> edit(@RequestBody @Valid RoleReqDto roleReqDto,
                      @PathVariable UUID id,
                      @RequestParam (value = "permissions") Set<PermissionEnum> permissionEnum );


    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}

package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.RoleReqDto;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import ai.ecma.appeticketserver.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;


    @Override
    public ApiResult<RoleRespDto> get(UUID id) {
        return roleService.get(id);
    }

    @Override
    public ApiResult<?> create(RoleReqDto roleReqDto, Set<PermissionEnum> permissionEnum) {
        return roleService.create(roleReqDto,permissionEnum);
    }

    @Override
    public ApiResult<?> edit(RoleReqDto roleReqDto, UUID id, Set<PermissionEnum> permissionEnum) {
        return roleService.edit(roleReqDto,id,permissionEnum);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return roleService.delete(id);
    }
}

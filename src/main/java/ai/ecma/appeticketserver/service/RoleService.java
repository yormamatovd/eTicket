package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.entity.Role;
import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.RoleReqDto;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    ApiResult<RoleRespDto> get(UUID id);

    ApiResult<?> create(RoleReqDto roleReqDto, Set<PermissionEnum> permissionEnum);

    ApiResult<?> edit(RoleReqDto roleReqDto, UUID id,Set<PermissionEnum> permissionEnum);

    ApiResult<?> delete(UUID id);





}

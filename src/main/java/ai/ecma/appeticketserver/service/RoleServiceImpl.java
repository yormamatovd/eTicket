package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.entity.Role;
import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.enums.RoleEnum;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.RoleMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.RoleReqDto;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import ai.ecma.appeticketserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    //    private final MessageService messageService;
    private final RoleMapper roleMapper;


    @Override
    public ApiResult<RoleRespDto> get(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ROLE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(roleMapper.toRoleRespDto(role));
    }

    @Override
    public ApiResult<?> create(RoleReqDto roleReqDto, Set<PermissionEnum> permissionEnum) {
        boolean existsByName = roleRepository.existsByName(roleReqDto.getName());
        if (existsByName) {
            throw new RestException(MessageService.getMessage("ROLE_NAME_ALREADY_EXISTS"), HttpStatus.CONFLICT);
        }
        roleRepository.save(roleMapper.toRole(roleReqDto,permissionEnum)).setType(RoleEnum.ROLE_CUSTOM);

        return ApiResult.successResponse(MessageService.getMessage("ROLE_SAVED"));
    }

    @Override
    public ApiResult<?> edit(RoleReqDto roleReqDto, UUID id, Set<PermissionEnum> permissionEnum) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ROLE_NOT_FOUND"), HttpStatus.NOT_FOUND));

        boolean byNameAndIdNot = roleRepository.existsByNameAndIdNot(roleReqDto.getName(), id);
        if (byNameAndIdNot) {
            throw new RestException(MessageService.getMessage("ROLE_NAME_ALREADY_EXISTS"), HttpStatus.CONFLICT);
        }
        roleRepository.save(roleMapper.toRole(role, roleReqDto, permissionEnum)).setType(RoleEnum.ROLE_CUSTOM);
        return ApiResult.successResponse(MessageService.getMessage("ROLE_EDITED"));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ROLE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        if (!role.getType().equals(RoleEnum.ROLE_CUSTOM)){
            throw new RestException(MessageService.getMessage("CAN_NOT_DELETED"),HttpStatus.CONFLICT);
        }
        roleRepository.delete(role);
        return ApiResult.successResponse(MessageService.getMessage("ROLE_DELETED"));
    }


}

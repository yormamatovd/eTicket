package ai.ecma.appeticketserver.payload.resp;

import ai.ecma.appeticketserver.enums.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleRespDto {
    private UUID id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissionEnums;

}
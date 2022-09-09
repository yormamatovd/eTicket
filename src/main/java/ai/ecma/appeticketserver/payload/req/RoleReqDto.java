package ai.ecma.appeticketserver.payload.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RoleReqDto {

    @NotBlank(message = "{ROLE_NAME_REQUIRED}")
    private String name;

    private String description;


}

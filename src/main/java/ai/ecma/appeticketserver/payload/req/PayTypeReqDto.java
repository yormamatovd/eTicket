package ai.ecma.appeticketserver.payload.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PayTypeReqDto {

    @NotBlank(message = "{PAY_TYPE}")
    private String name;
}

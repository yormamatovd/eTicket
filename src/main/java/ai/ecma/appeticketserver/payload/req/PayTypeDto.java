package ai.ecma.appeticketserver.payload.req;

import ai.ecma.appeticketserver.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayTypeDto {

    private UUID id;

    @NotBlank(message = "{PAY_TYPE}")
    private PayTypeEnum name;

    private boolean active;
}

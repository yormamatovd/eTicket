package ai.ecma.appeticketserver.payload.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromoCodeReqDto {

    private Long number;

    private Double percent;

    @ApiModelProperty(example = "a6210c27-a0aa-422c-9098-5cf1f88f7fbd")
    private UUID userId;

    private Boolean isUsed;
}
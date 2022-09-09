package ai.ecma.appeticketserver.payload.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryReqDto implements Serializable {

    @ApiModelProperty(example = "Concert", required = true)
    @NotBlank(message = "{CATEGORY_NAME_REQUIRED}")
    private String name;

    @ApiModelProperty(example = "a6210c27-a0aa-422c-9098-5cf1f88f7fbd")
    private UUID parentCategoryId;
}

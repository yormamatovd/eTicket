package ai.ecma.appeticketserver.payload.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TemplateEditeDto {

    @NotNull
    private Double price;

    @NotBlank(message = "STATUS_NOT_FOUND")
    private String status;
}

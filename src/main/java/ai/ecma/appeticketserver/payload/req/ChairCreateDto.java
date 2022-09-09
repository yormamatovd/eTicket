package ai.ecma.appeticketserver.payload.req;

import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.context.support.UiApplicationContextUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChairCreateDto {
    @NotNull
    private UUID templateId;

    @NotBlank
    private String sectorName;

    @NotBlank
    private String rowName;

    @NotNull
    private Double price;

    @NotNull
    private TicketStatusEnum status;
}

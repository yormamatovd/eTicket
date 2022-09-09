package ai.ecma.appeticketserver.payload.req;

import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RowCreateDto {
    @NotNull
    private UUID templateId;

    @NotBlank
    private String sectorName;

    private String rowName;

    private Integer chairCount;

    private Double price;

    private TicketStatusEnum status;
}

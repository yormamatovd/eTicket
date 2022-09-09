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
public class SectorCreateDto {
    @NotNull
    private UUID templateId;

    @NotBlank
    private String sectorName;

    @NotNull
    private Integer rowCount;

    @NotNull
    private Integer chairCount;

    @NotNull
    private Double price;

    @NotNull
    private TicketStatusEnum status;
}

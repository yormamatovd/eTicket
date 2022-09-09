package ai.ecma.appeticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnTariffDto {
    private UUID id;

    @Positive
    private Integer toTime;//soat

    @Positive
    private Double percent;

    @NotNull
    private UUID eventId;

    private boolean active;
}

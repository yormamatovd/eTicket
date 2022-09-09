package ai.ecma.appeticketserver.payload.resp;

import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChairResDto {
    private UUID id;
    private String name;
    private Double price;
    private TicketStatusEnum status;
}

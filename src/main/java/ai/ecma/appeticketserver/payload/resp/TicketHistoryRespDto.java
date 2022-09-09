package ai.ecma.appeticketserver.payload.resp;

import ai.ecma.appeticketserver.enums.TicketStatusEnum;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TicketHistoryRespDto {
    private UUID id;

    private UUID ticketId;

    private Timestamp actionTime;

    private TicketStatusEnum status;
}
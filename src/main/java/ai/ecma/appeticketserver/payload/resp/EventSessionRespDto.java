package ai.ecma.appeticketserver.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventSessionRespDto {
    private Timestamp startTime;
    private Timestamp endTime;
    private UUID eventId;
}

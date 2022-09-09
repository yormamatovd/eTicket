package ai.ecma.appeticketserver.payload.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventSessionDto {
    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;
}

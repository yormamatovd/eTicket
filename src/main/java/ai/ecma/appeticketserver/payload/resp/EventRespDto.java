package ai.ecma.appeticketserver.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventRespDto {
    private UUID id;

    private String name;

    private UUID placeId;

    private String description;

    private UUID bannerId;

    private boolean hasReturning;

    private UUID categoryId;

    private UUID schemaId;
}

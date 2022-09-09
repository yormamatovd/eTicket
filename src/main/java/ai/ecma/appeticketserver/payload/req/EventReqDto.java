package ai.ecma.appeticketserver.payload.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EventReqDto {
    @ApiModelProperty(example = "Box championship", required = true)
    @NotBlank(message = "{EVENT_NAME_REQUIRED}")
    private String name;

    @NotNull
    private UUID placeId;

    private String description;

    @NotNull
    private UUID bannerId;

    private boolean hasReturning;

    private UUID categoryId;

    @NotNull
    private UUID schemaId;

    @NotNull
    private UUID templateId;

    @NotEmpty
    private List<ArtistEventDto> artists;

    @NotEmpty
    private List<EventSessionDto> sessions;

}
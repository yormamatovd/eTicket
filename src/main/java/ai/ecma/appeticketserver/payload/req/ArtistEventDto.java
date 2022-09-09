package ai.ecma.appeticketserver.payload.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistEventDto {
    @NotNull
    private UUID artistId;
    private boolean main;
    @NotBlank
    private String specification;
}

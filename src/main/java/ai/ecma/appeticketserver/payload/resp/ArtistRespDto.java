package ai.ecma.appeticketserver.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtistRespDto {

    private UUID id;

    private String name;

    private String description;

    private UUID profilePhoto;
}

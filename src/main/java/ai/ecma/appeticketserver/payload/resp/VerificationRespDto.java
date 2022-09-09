package ai.ecma.appeticketserver.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VerificationRespDto {
    private String accessToken;
    private String refreshToken;
    private boolean registered;

    public VerificationRespDto() {
        this.accessToken = null;
        this.refreshToken = null;
        this.registered = false;
    }

    public VerificationRespDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.registered = true;
    }
}

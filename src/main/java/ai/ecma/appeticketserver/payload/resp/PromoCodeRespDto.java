package ai.ecma.appeticketserver.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromoCodeRespDto {

    private Long number;
    private Double percent;
    private UUID userId;
    private Boolean isUsed;
    private Timestamp expireDate;  //  2021-03-24 16:48:05.591
}
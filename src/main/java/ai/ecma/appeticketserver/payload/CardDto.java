package ai.ecma.appeticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDto {
    @NotBlank
    @CreditCardNumber
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2}")
    private String expireMonth;

    @NotBlank
    @Pattern(regexp = "^[0-9]{2}")
    private String expireYear;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}")
    private String cvc;

}

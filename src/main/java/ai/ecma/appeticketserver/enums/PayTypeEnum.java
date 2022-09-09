package ai.ecma.appeticketserver.enums;

public enum PayTypeEnum {
    CASH("CASH"),
    APELSIN("APELSIN"),
    CLICK("CLICK"),
    VISA("VISA"),
    PAYME("PAYME"),
    PAYNET("PAYNET"),
    UZCARD("UZCARD"),
    MASTERCARD("MASTERCARD"),
    STRIPE("STRIPE")
    ;

    private final String code;

    PayTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

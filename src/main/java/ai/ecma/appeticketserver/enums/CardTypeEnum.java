package ai.ecma.appeticketserver.enums;

public enum CardTypeEnum {
    UZCARD("UZCARD"),
    HUMO("HUMO"),
    VISA("VISA");

    private final String code;

    CardTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

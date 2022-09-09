package ai.ecma.appeticketserver.enums;

public enum TicketStatusEnum {
    AVAILABLE("AVAILABLE"),
    BOOKED("BOOKED"),
    SOLD("SOLD"),
    VIP("VIP"),
    RESERVED("RESERVED");

    private final String code;

    TicketStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

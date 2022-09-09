package ai.ecma.appeticketserver.payload.resp;

import java.util.UUID;

public interface TemplateResDto {
    UUID getId();

    String getName();

    Integer getCountChairs();

    Double getMaxPrice();

    Double getMinPrice();
}

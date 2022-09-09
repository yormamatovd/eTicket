package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.entity.TicketHistory;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.resp.CategoryRespDto;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import ai.ecma.appeticketserver.payload.resp.TicketHistoryRespDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface TicketHistoryService {
    ApiResult<CustomPage<TicketHistoryRespDto>> getAll(Integer page, Integer size);

    CustomPage<TicketHistoryRespDto> makeCustomPage(Page<TicketHistory> page);

    ApiResult<CustomPage<TicketHistoryRespDto>> getAllByTicketId(UUID ticketId, Integer page, Integer size);
}

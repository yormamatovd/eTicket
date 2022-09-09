package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.resp.TicketHistoryRespDto;
import ai.ecma.appeticketserver.service.TicketHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class TicketHistoryControllerImpl implements TicketHistoryController{

    private final TicketHistoryService ticketHistoryService;

    @Override
    public ApiResult<CustomPage<TicketHistoryRespDto>> getAll(Integer page, Integer size) {
        return ticketHistoryService.getAll(page,size);

    }

    @Override
    public ApiResult<CustomPage<TicketHistoryRespDto>> getAllByTicketId(UUID ticketId, Integer page, Integer size) {
        return ticketHistoryService.getAllByTicketId(ticketId,page,size);
    }
}

package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Category;
import ai.ecma.appeticketserver.entity.Role;
import ai.ecma.appeticketserver.entity.TicketHistory;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.TicketHistoryMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.resp.RoleRespDto;
import ai.ecma.appeticketserver.payload.resp.TicketHistoryRespDto;
import ai.ecma.appeticketserver.repository.TicketHistoryRepository;
import ai.ecma.appeticketserver.repository.TicketRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketHistoryServiceImpl implements TicketHistoryService{
    private final TicketHistoryRepository historyRepository;
    private final TicketHistoryMapper historyMapper;
    private final TicketRepository ticketRepository;


    @Override
    public ApiResult<CustomPage<TicketHistoryRespDto>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("actionTime")));
        Page<TicketHistory> all = historyRepository.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(all));
    }

    @Override
    public ApiResult<CustomPage<TicketHistoryRespDto>> getAllByTicketId(UUID ticketId, Integer page, Integer size) {
        ticketRepository.findById(ticketId).orElseThrow(() -> new RestException(MessageService.getMessage("TICKET_NOT_FOUND"),HttpStatus.NOT_FOUND));
        Pageable pageable = PageRequest.of(page,size);
        Page<TicketHistory> ticketHistoryByTicketId = historyRepository.getAllByTicketId(ticketId,pageable);
        return ApiResult.successResponse(makeCustomPage(ticketHistoryByTicketId));
    }

    @Override
    public CustomPage<TicketHistoryRespDto> makeCustomPage(Page<TicketHistory> page) {
        return new CustomPage<>(
                page.getContent().stream().map(historyMapper::toTicketHistoryRespDto).collect(Collectors.toList()),
                page.getNumberOfElements(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}

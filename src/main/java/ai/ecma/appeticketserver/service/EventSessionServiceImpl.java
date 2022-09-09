package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Event;
import ai.ecma.appeticketserver.entity.EventSession;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.EventSessionMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.req.EventSessionReqDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.payload.resp.EventSessionRespDto;
import ai.ecma.appeticketserver.repository.EventRepo;
import ai.ecma.appeticketserver.repository.EventSessionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSessionServiceImpl implements EventSessionService{
    private final EventSessionRepo eventSessionRepo;
    private final EventRepo eventRepo;
    private final EventSessionMapper eventSessionMapper;
    private final MessageService messageService;

    @Override
    public ApiResult<CustomPage<EventSessionRespDto>> getAll(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("startTime")));
        Page<EventSession> sessions = eventSessionRepo.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(sessions));
    }

    @Override
    public ApiResult<EventSessionRespDto> get(UUID id) {
        EventSession session = eventSessionRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_SESSION_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(eventSessionMapper.toEventSessionRespDto(session));
    }

    @Override
    public ApiResult<?> create(EventSessionReqDto dto) {
        Timestamp startTime = new Timestamp(dto.getStartTime());
        Timestamp endTime = new Timestamp(dto.getEndTime());
        Event event = eventRepo.findById(dto.getEventId()).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_NOT_FOUND"), HttpStatus.NOT_FOUND));
        EventSession savedSession = eventSessionRepo.save(new EventSession(startTime, endTime, event));
        return ApiResult.successResponse(eventSessionMapper.toEventSessionRespDto(savedSession));
    }

    @Override
    public ApiResult<?> edit(UUID id, EventSessionReqDto dto) {
        EventSession session = eventSessionRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_SESSION_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Event event = eventRepo.findById(dto.getEventId()).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_NOT_FOUND"), HttpStatus.NOT_FOUND));
        EventSession editedSession = EventSession.editSession(session, new Timestamp(dto.getStartTime()), new Timestamp(dto.getEndTime()), event);
        return ApiResult.successResponse(eventSessionMapper.toEventSessionRespDto(editedSession));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        EventSession session = eventSessionRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_SESSION_NOT_FOUND"), HttpStatus.NOT_FOUND));
        eventSessionRepo.delete(session);
        return ApiResult.successResponse(messageService.getMessage("EVENT_DELETED"));
    }

    @Override
    public CustomPage<EventSessionRespDto> makeCustomPage(Page<EventSession> session) {
        return new CustomPage<>(
                session.getContent().stream().map(eventSessionMapper::toEventSessionRespDto).collect(Collectors.toList()),
                session.getNumberOfElements(),
                session.getNumber(),
                session.getTotalElements(),
                session.getTotalPages(),
                session.getSize()
        );
    }
}


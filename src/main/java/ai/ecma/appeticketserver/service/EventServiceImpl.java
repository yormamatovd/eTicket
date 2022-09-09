package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.*;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.EventMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.ArtistEventDto;
import ai.ecma.appeticketserver.payload.req.EventReqDto;
import ai.ecma.appeticketserver.payload.req.EventSessionDto;
import ai.ecma.appeticketserver.payload.resp.EventRespDto;
import ai.ecma.appeticketserver.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepo eventRepo;
    private final EventMapper eventMapper;
    private final MessageService messageService;
    private final PlaceRepo placeRepo;
    private final AttachmentRepo attachmentRepo;
    private final CategoryRepository categoryRepo;
    private final ArtistRepository artistRepository;
    private final ArtistEventRepository artistEventRepository;
    private final EventSessionRepository eventSessionRepository;
    private final SeatTemplateRepository seatTemplateRepository;
    private final TemplateChairRepository templateChairRepository;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    @Override
    public ApiResult<CustomPage<EventRespDto>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Event> events = eventRepo.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(events));
    }

    @Override
    public ApiResult<EventRespDto> get(UUID id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("EVENT_NOT_FOUND"), HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(eventMapper.toEventRespDto(event));
    }

    @Override
    @Transactional
// todo row sectorlar ticketga beriladi
    public ApiResult<?> create(EventReqDto dto) {
        Place place = placeRepo.findById(dto.getPlaceId()).orElseThrow(() -> new RestException(MessageService.getMessage("PLACE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Attachment banner = attachmentRepo.findById(dto.getBannerId()).orElseThrow(() -> new RestException(MessageService.getMessage("BANNER_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Attachment schema = attachmentRepo.findById(dto.getSchemaId()).orElseThrow(() -> new RestException(MessageService.getMessage("SCHEMA_NOT_FOUND"), HttpStatus.NOT_FOUND));

        Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new RestException(MessageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Event savedEvent = eventRepo.save(new Event(dto.getName(), place, dto.getDescription(), banner, dto.isHasReturning(), category, schema));

        List<ArtistEvent> artistEventsList = new ArrayList<>();
        for (ArtistEventDto artistDto : dto.getArtists()) {
            Artist artist = artistRepository.findById(artistDto.getArtistId()).orElseThrow(() -> RestException.notFound(MessageService.getMessage("ARTIST_NOT_FOUND")));
            artistEventsList.add(
                    new ArtistEvent(
                            savedEvent, artist, artistDto.getSpecification(), artistDto.isMain()
                    )
            );
        }
        artistEventRepository.saveAll(artistEventsList);

        List<EventSession> sessionList = new ArrayList<>();
        for (EventSessionDto session : dto.getSessions()) {
            Timestamp startTime = new Timestamp(session.getStartTime());
            Timestamp endTime = new Timestamp(session.getEndTime());

            if (eventSessionRepository.eventSessionTimeIsDuplicate(place.getId(), startTime, endTime))
                throw RestException.restThrow(MessageService.getMessage("EVENT_SESSION_IS_DUPLICATE"), HttpStatus.CONFLICT);

            sessionList.add(new EventSession(startTime, endTime, savedEvent));
        }
        eventSessionRepository.saveAll(sessionList);

        new Thread(() -> {
            try {
                createTicketsPerEventSession(sessionList, dto.getTemplateId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return ApiResult.successResponse(eventMapper.toEventRespDto(savedEvent));
    }

    public void createTicketsPerEventSession(List<EventSession> sessions, UUID templateId) {
        SeatTemplate template = seatTemplateRepository.findById(templateId).orElseThrow(() -> RestException.notFound(MessageService.getMessage("TEMPLATE_NOT_FOUND")));
        List<TemplateChair> chairs = templateChairRepository.findAllByTemplateId(template.getId());

        List<Ticket> ticketList = new ArrayList<>();
        for (EventSession session : sessions) {
            ticketList.addAll(ticketService.chairsToTickets(chairs, session));
        }
        ticketRepository.saveAll(ticketList);
    }

    @Override
    public ApiResult<?> edit(UUID id, EventReqDto dto) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Place place = placeRepo.findById(dto.getPlaceId()).orElseThrow(() -> new RestException(messageService.getMessage("PLACE_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Attachment banner = null;
        if (dto.getBannerId() != null)
            banner = attachmentRepo.findById(dto.getBannerId()).orElseThrow(() -> new RestException(messageService.getMessage("BANNER_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Attachment schema = null;
        if (dto.getSchemaId() != null)
            schema = attachmentRepo.findById(dto.getSchemaId()).orElseThrow(() -> new RestException(messageService.getMessage("SCHEMA_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Category category = categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new RestException(messageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));

        Event editedEvent = eventRepo.save(Event.editEvent(event, dto.getName(), place, dto.getDescription(), banner, dto.isHasReturning(), category, schema));
        return ApiResult.successResponse(eventMapper.toEventRespDto(editedEvent));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        Event event = eventRepo.findById(id).orElseThrow(() -> new RestException(messageService.getMessage("EVENT_NOT_FOUND"), HttpStatus.NOT_FOUND));
        eventRepo.delete(event);
        return ApiResult.successResponse(MessageService.getMessage("EVENT_DELETED"));
    }

    @Override
    public CustomPage<EventRespDto> makeCustomPage(Page<Event> event) {
        return new CustomPage<>(
                event.getContent().stream().map(eventMapper::toEventRespDto).collect(Collectors.toList()),
                event.getNumberOfElements(),
                event.getNumber(),
                event.getTotalElements(),
                event.getTotalPages(),
                event.getSize()
        );
    }

    @Override
    public ApiResult<CustomPage<EventRespDto>> getAllByCategory(UUID categoryId, Integer page, Integer size) {
        categoryRepo.findById(categoryId).orElseThrow(() ->  new RestException(MessageService.getMessage("CATEGORY_NOT_FOUND"), HttpStatus.NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Event> allByCategoryId = eventRepo.findAllByCategoryId(categoryId, pageable);
        return ApiResult.successResponse(makeCustomPage(allByCategoryId));
    }
}

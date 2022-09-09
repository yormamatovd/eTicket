package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Artist;
import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.entity.AttachmentContent;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.mapper.ArtistMapper;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.ArtistCreateDto;
import ai.ecma.appeticketserver.payload.resp.ArtistRespDto;
import ai.ecma.appeticketserver.repository.ArtistRepository;
import ai.ecma.appeticketserver.repository.AttachmentContentRepository;
import ai.ecma.appeticketserver.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository contentRepository;

    @Override
    public ApiResult<ArtistRespDto> getArtist(UUID id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND));

        if (artist.isDeleted())
            throw new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND);

        return ApiResult.successResponse(artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto())));
    }

    @Override
    public ApiResult<CustomPage<ArtistRespDto>> getAllArtists(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("name")));
        Page<Artist> artists = artistRepository.findAll(pageable);
        return ApiResult.successResponse(makeCustomPage(artists));
    }

    @Override
    public ApiResult<ArtistRespDto> addArtist(ArtistCreateDto dto) {

        Optional<Artist> artistOptional = artistRepository.findByNameAndDeleted(dto.getName(), false);
        if (artistOptional.isPresent()) {
            throw new RestException(MessageService.getMessage("ARTIST_NAME_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
        }

        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setDescription(dto.getDescription());

        return ApiResult.successResponse(artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto())));
    }

    @Override
    public ApiResult<ArtistRespDto> updateArtist(UUID id, ArtistCreateDto dto) {

        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND));

        Optional<Artist> artistOptional = artistRepository.findByNameAndDeleted(dto.getName(), false);
        if (artistOptional.isPresent()) {
            if (!Objects.equals(artistOptional.get().getName(), artist.getName())) {
                throw new RestException(MessageService.getMessage("ARTIST_NAME_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
            }
        }

        artist.setName(dto.getName());
        artist.setDescription(dto.getDescription() != null ? dto.getDescription() : artist.getDescription());

        artistRepository.save(artist);
        return ApiResult.successResponse(artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto())));
    }

    @Override
    public ApiResult<?> deleteArtist(UUID id) {

        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND));

        deleteAttachment(artist.getPhoto());

        artist.setDeleted(true);
        artist.setName("DELETED " + artist.getName() + " [" + artist.getId() + "]");
        artistRepository.save(artist);

        return ApiResult.successResponse(MessageService.getMessage("ARTIST_DELETED"));
    }


    @Override
    public ApiResult<ArtistRespDto> setArtistPhoto(MultipartFile photo, UUID artistId) {

        if (!Objects.equals(photo.getContentType(), "image/jpeg") &&
                !Objects.equals(photo.getContentType(), "image/png"))
            throw new RestException(MessageService.getMessage("UNSUPPORTED_CONTENT_TYPE"), HttpStatus.BAD_REQUEST);

        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND));

        deleteAttachment(artist.getPhoto());

        artist.setPhoto(uploadFile(photo));

        artistRepository.save(artist);
        return ApiResult.successResponse(artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto())));
    }

    @Override
    public ApiResult<ArtistRespDto> removeArtistPhoto(UUID artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new RestException(MessageService.getMessage("ARTIST_NOT_FOUND"), HttpStatus.NOT_FOUND));

        deleteAttachment(artist.getPhoto());

        artist.setPhoto(null);

        artistRepository.save(artist);
        return ApiResult.successResponse(artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto())));
    }

    @Override
    public CustomPage<ArtistRespDto> makeCustomPage(Page<Artist> artists) {
        return new CustomPage<>(
                artists.getContent().stream().map(artist -> artistMapper.toArtistRespDto(artist, getFileId(artist.getPhoto()))).collect(Collectors.toList()),
                artists.getNumberOfElements(),
                artists.getNumber(),
                artists.getTotalElements(),
                artists.getTotalPages(),
                artists.getSize()
        );
    }

    @Override
    public UUID getFileId(Attachment photo) {
        if (photo != null) {
            if (!photo.isDeleted()) {
                if (photo.getName() != null) {
                    assert contentRepository != null;
                    Optional<AttachmentContent> photoContent = contentRepository.findByAttachmentId(photo.getId());
                    if (photoContent.isPresent()) {
                        if (!photoContent.get().isDeleted()) {
                            return photo.getId();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Transactional
    @Override
    public Attachment uploadFile(MultipartFile file) {
        Attachment attachment = Attachment.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType()).build();

        Attachment save = attachmentRepository.save(attachment);

        AttachmentContent content;
        try {
            content = AttachmentContent.builder()
                    .attachment(save)
                    .bytes(file.getBytes()).build();
        } catch (IOException e) {
            throw new RestException(MessageService.getMessage("FILE_NOT_SAVED"), HttpStatus.BAD_REQUEST);
        }

        contentRepository.save(content);

        return attachment;
    }

    @Override
    public Attachment deleteAttachment(Attachment attachment) {
        if (attachment != null) {

            Optional<AttachmentContent> content = contentRepository.findByAttachmentId(attachment.getId());
            if (content.isPresent()) {
                AttachmentContent photoContent = content.get();
                photoContent.setDeleted(true);
                contentRepository.save(photoContent);
            }
            attachment.setDeleted(true);
            attachmentRepository.save(attachment);
        }
        return attachment;
    }
}

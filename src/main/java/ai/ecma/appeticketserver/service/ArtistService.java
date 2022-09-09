package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Artist;
import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.ArtistCreateDto;
import ai.ecma.appeticketserver.payload.resp.ArtistRespDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ArtistService {
    ApiResult<ArtistRespDto> getArtist(UUID id);

    ApiResult<CustomPage<ArtistRespDto>> getAllArtists(Integer page, Integer size);

    ApiResult<ArtistRespDto> addArtist(ArtistCreateDto dto);

    ApiResult<ArtistRespDto> updateArtist(UUID id, ArtistCreateDto dto);

    ApiResult<?> deleteArtist(UUID id);

    CustomPage<ArtistRespDto> makeCustomPage(Page<Artist> artists);

    ApiResult<ArtistRespDto> setArtistPhoto(MultipartFile photo, UUID artistId);

    ApiResult<ArtistRespDto> removeArtistPhoto(UUID artistId);

    Attachment uploadFile(MultipartFile file) throws IOException;

    UUID getFileId(Attachment photo);

    Attachment deleteAttachment(Attachment attachment);

}

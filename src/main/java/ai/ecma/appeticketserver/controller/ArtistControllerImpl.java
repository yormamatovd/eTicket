package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.Artist;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.ArtistCreateDto;
import ai.ecma.appeticketserver.payload.resp.ArtistRespDto;
import ai.ecma.appeticketserver.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
public class ArtistControllerImpl implements ArtistController {

    private final ArtistService artistService;

    @Override
    public ApiResult<ArtistRespDto> getArtist(UUID id) {
        return artistService.getArtist(id);
    }

    @Override
    public ApiResult<CustomPage<ArtistRespDto>> getAllArtists(Integer page, Integer size) {
        return artistService.getAllArtists(page,size);
    }

    @Override
    public ApiResult<ArtistRespDto> addArtist(ArtistCreateDto dto) {
        return artistService.addArtist(dto);
    }

    @Override
    public ApiResult<ArtistRespDto> updateArtist(UUID id, ArtistCreateDto dto) {
        return artistService.updateArtist(id,dto);
    }

    @Override
    public ApiResult<?> deleteArtist(UUID id) {
        return artistService.deleteArtist(id);
    }

    @Override
    public ApiResult<ArtistRespDto> setArtistPhoto(MultipartFile photo, UUID artistId) {
        return artistService.setArtistPhoto(photo,artistId);
    }

    @Override
    public ApiResult<ArtistRespDto> removeArtistPhoto(UUID artistId) {
        return artistService.removeArtistPhoto(artistId);
    }
}

package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.payload.CustomPage;
import ai.ecma.appeticketserver.payload.req.ArtistCreateDto;
import ai.ecma.appeticketserver.payload.resp.ArtistRespDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static ai.ecma.appeticketserver.utils.AppConstant.*;

@RequestMapping(ArtistController.ARTIST_CONTROLLER)
public interface ArtistController {
    String ARTIST_CONTROLLER = BASE_PATH + "/artist";


    @GetMapping("{id}")
    ApiResult<ArtistRespDto> getArtist(@PathVariable(name = "id") UUID id);

    @GetMapping()
    ApiResult<CustomPage<ArtistRespDto>> getAllArtists(@RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer page,
                                                       @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer size);

    @PostMapping
    ApiResult<ArtistRespDto> addArtist(@RequestBody @Valid ArtistCreateDto dto);

    @PutMapping(value = "{id}")
    ApiResult<ArtistRespDto> updateArtist(@PathVariable UUID id,
                                          @RequestBody @Valid ArtistCreateDto dto);

    @DeleteMapping("{id}")
    ApiResult<?> deleteArtist(@PathVariable UUID id);

    @PostMapping("/photo/{artistId}")
    ApiResult<ArtistRespDto> setArtistPhoto(@RequestParam(name = "photo") MultipartFile photo, @PathVariable UUID artistId);

    @DeleteMapping("/photo/{artistId}")
    ApiResult<ArtistRespDto> removeArtistPhoto(@PathVariable UUID artistId);

}

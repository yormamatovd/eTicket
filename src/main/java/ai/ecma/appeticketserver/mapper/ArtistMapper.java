package ai.ecma.appeticketserver.mapper;

import ai.ecma.appeticketserver.entity.Artist;
import ai.ecma.appeticketserver.payload.resp.ArtistRespDto;
import ai.ecma.appeticketserver.repository.AttachmentContentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface ArtistMapper {

    @Autowired
    AttachmentContentRepository contentRepository = null;

    @Mapping(target = "profilePhoto", source = "photoId")
    ArtistRespDto toArtistRespDto(Artist artist, UUID photoId);


}

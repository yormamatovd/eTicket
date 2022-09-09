package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.payload.ApiResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AttachmentService {

    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    void download(UUID id, HttpServletResponse response);

    ApiResult<List<Attachment>> getAll();
}

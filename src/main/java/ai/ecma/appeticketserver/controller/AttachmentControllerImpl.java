package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController{

    private final AttachmentService service;

    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException {
        return service.upload(request);
    }

    @Override
    public void download(UUID id, HttpServletResponse response) {
        service.download(id, response);
    }

    @Override
    public ApiResult<List<Attachment>> getAll() {
        return service.getAll();
    }
}

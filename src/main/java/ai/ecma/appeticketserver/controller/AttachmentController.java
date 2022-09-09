package ai.ecma.appeticketserver.controller;

import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.utils.AppConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping(AttachmentController.ATTACHMENT_CONTROLLER)
public interface AttachmentController {

    String ATTACHMENT_CONTROLLER = AppConstant.BASE_PATH + "/attachment";

    @PostMapping("/upload")
    ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException;

    @GetMapping("/download/{id}")
    void download(@PathVariable(value = "id") UUID id, HttpServletResponse response);

    @GetMapping("/get")
    ApiResult<List<Attachment>> getAll();
}

package ai.ecma.appeticketserver.service;

import ai.ecma.appeticketserver.common.MessageService;
import ai.ecma.appeticketserver.entity.Attachment;
import ai.ecma.appeticketserver.entity.AttachmentContent;
import ai.ecma.appeticketserver.exception.RestException;
import ai.ecma.appeticketserver.payload.ApiResult;
import ai.ecma.appeticketserver.repository.AttachmentContentRepository;
import ai.ecma.appeticketserver.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository contentRepository;



    @Override
    public ApiResult<?> upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = Attachment.builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .contentType(file.getContentType()).build();
        Attachment save = attachmentRepository.save(attachment);

        AttachmentContent content = AttachmentContent.builder()
                .attachment(save)
                .bytes(file.getBytes()).build();
        contentRepository.save(content);
        return ApiResult.successResponse(MessageService.getMessage("FILE_SAVED"));
    }

    @Override
    public void download(UUID id, HttpServletResponse response) {
        Optional<Attachment> file = attachmentRepository.findById(id);
        if (file.isPresent()) {
            Attachment attachment = file.get();
            Optional<AttachmentContent> attachmentContent = contentRepository.findByAttachmentId(id);
            if (attachmentContent.isPresent()) {
                AttachmentContent content = attachmentContent.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                try {
                    FileCopyUtils.copy(content.getBytes(), response.getOutputStream());
                } catch (Exception e) {
                    throw RestException.badRequest();
                }
            }
        }
    }

    @Override
    public ApiResult<List<Attachment>> getAll() {
        List<Attachment> attachmentList = attachmentRepository.findAll();
        if (attachmentList.isEmpty())
            throw RestException.restThrow(MessageService.getMessage("NOT_FOUND"), HttpStatus.NOT_FOUND);
        return ApiResult.successResponse(attachmentList);
    }
}

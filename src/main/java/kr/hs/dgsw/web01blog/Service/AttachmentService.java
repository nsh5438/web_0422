package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Attachment;
import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.AttachmentPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AttachmentService {
    ResponseFormat Attachment(MultipartFile uploadFile);

    void Download(String type, Long id, HttpServletRequest request, HttpServletResponse response);

    ResponseFormat AddAttachment(Long id, Attachment attachment);
}

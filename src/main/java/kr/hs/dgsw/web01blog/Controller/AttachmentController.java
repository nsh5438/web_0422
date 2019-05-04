package kr.hs.dgsw.web01blog.Controller;

import kr.hs.dgsw.web01blog.Domain.Attachment;
import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Protocol.AttachmentPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/attachment")
    public ResponseFormat Attachment(@RequestPart MultipartFile uploadFile){ return this.attachmentService.Attachment(uploadFile); }

    @GetMapping("/download/{type}/{id}")
    public void Download(@PathVariable String type, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        this.attachmentService.Download(type,id,request,response);
    }

    @PostMapping("/addattachment/{id}")
    public ResponseFormat AddAttachment(@PathVariable Long id, @RequestBody Attachment attachment) {return this.attachmentService.AddAttachment(id,attachment);}
}

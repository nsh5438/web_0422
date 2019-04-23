package kr.hs.dgsw.web01blog.Service;

import kr.hs.dgsw.web01blog.Domain.Attachment;
import kr.hs.dgsw.web01blog.Domain.Post;
import kr.hs.dgsw.web01blog.Domain.User;
import kr.hs.dgsw.web01blog.Protocol.AttachmentPro;
import kr.hs.dgsw.web01blog.Protocol.PostUserPro;
import kr.hs.dgsw.web01blog.Protocol.ResponseFormat;
import kr.hs.dgsw.web01blog.Protocol.ResponseType;
import kr.hs.dgsw.web01blog.Repository.AttachmentRep;
import kr.hs.dgsw.web01blog.Repository.PostRep;
import kr.hs.dgsw.web01blog.Repository.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Optional;
import java.util.UUID;

@Service
public class Attachmentimp implements AttachmentService {

    @Autowired
    private PostRep postRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private AttachmentRep attachmentRep;

    @Override
    public ResponseFormat Attachment(MultipartFile uploadFile) {
        String destFilename = "D:/3102_남가영/IdeaProjects/web01blog/upload/" + UUID.randomUUID().toString() + " " + uploadFile.getOriginalFilename();
        try {
            File destFile = new File(destFilename);
            destFile.getParentFile().mkdirs();
            uploadFile.transferTo(destFile);
            return new ResponseFormat(ResponseType.ATTACHMENT_STORED, new AttachmentPro(destFilename, uploadFile.getOriginalFilename()));
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void Download(String type, Long id, HttpServletRequest request, HttpServletResponse response) {
        String filepath = null;
        String filename = null;

        if(type.equals("post")){
            Optional<Post> found = this.postRep.findById(id);
            Optional<Attachment> attach = this.attachmentRep.findByPostID(id);
            filepath = attach.get().getSavefilepath();
//            filename = found.get().getFilename();
        }else{
            Optional<User> found = this.userRep.findById(id);
            filepath = found.get().getProfilepath();
//            filename = found.get().getOrdinaryname();
        }

        try{
            File file =new File(filepath);
            if(file.exists() == false) return;
            String fileType = URLConnection.guessContentTypeFromName(file.getName());
            if(fileType == null) fileType = "application/octet-stream";
            response.setContentType(fileType);
            response.setHeader("Content-Disposition", "inline; filename=\'" + file.getName() + "\'");
            response.setContentLength((int)file.length());
            InputStream ip = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(ip,response.getOutputStream());
        }catch( Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ResponseFormat AddAttachment(Attachment attachment) {
        Attachment addattach = this.attachmentRep.save(attachment);
        this.postRep.findById(addattach.getId()).get().getPictures().add(addattach);
        return new ResponseFormat(ResponseType.ATTACHMENT_STORED, addattach);
    }
}

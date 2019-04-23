package kr.hs.dgsw.web01blog.Domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String savefilepath;

    private Long postID;

    public Attachment(Attachment attachment){
        this.id = attachment.getId();
        this.savefilepath = attachment.getSavefilepath();
        this.postID = attachment.getPostID();
    }

}

package kr.hs.dgsw.web01blog.Protocol;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachmentPro {

    private String filepath;

    private String filename;

    public AttachmentPro(String filepath, String filename) {
        this.filepath = filepath;
        this.filename = filename;
    }
}

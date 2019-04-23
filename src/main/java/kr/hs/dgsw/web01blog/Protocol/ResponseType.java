package kr.hs.dgsw.web01blog.Protocol;

public enum ResponseType {
    FAIL                    (0,"Failed to run"),

    USER_ADD                (101,"User account[%s] add"),
    USER_DELETE             (102,"User account[%s] delete"),
    USER_UPDATE             (103,"User account[%s] update"),
    USER_GET                (104,"User get"),

    POST_GET                (201,"Post get"),
    POST_DELETE             (202,"Post postID[%d] delete"),
    POST_ADD                (203,"Post postID[%d] add"),
    POST_UPDATE             (204,"Post postID[%d] update"),

    ATTACHMENT_STORED       (301,"Attachment sucess");

    final private int code;
    final private String desc;

    ResponseType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int code(){
        return this.code;
    }

    public String desc(){
        return this.desc;
    }
}

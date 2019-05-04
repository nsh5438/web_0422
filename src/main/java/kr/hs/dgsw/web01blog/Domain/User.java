package kr.hs.dgsw.web01blog.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Entity //Class -> MariaDB
@Data //Getter Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //primary key

    @Column(nullable = false, unique = true, length = 20)   // nullable : null값 안받아들임. / unique : 중복X / length : 길이
    private String account;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // 스프링이 내부적으로 사용은 하지만 외부적으로 사용할 수 없다.
    private String password;

    public void setPassword(String password) {  // password 암호화
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes(),0,password.getBytes().length);
            this.password = new BigInteger(1,md.digest()).toString(16);
        }catch (NoSuchAlgorithmException e){
            Logger logger = LoggerFactory.getLogger(User.class);
            logger.warn(e.getMessage());
            this.password = null;
        }
    }

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String profilepath;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)  // 값을 변경할 수 없다.
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")    // 이러한 포맷으로 전송
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updated;

    public User(String account, String password, String name, String email, String phone, String profilepath) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profilepath = profilepath;
    }
}

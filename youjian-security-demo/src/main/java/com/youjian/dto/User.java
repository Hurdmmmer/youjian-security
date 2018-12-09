package com.youjian.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.youjian.validator.MyValidator;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author shen youjian
 * @date 2018/12/6 22:06
 */
@Data
public class User {

    public interface UserSimpleView{}
    public interface UserDetailView extends UserSimpleView{}
    @JsonView(UserSimpleView.class)
    private String id;


    /** JsonView 指定输出视图, 只再 UserSimpleView 视图中输出*/
    @JsonView(UserSimpleView.class)
    @MyValidator(message = "则就是个测试注解")
    private String username;

    @NotBlank
    @JsonView(UserDetailView.class)
    private String password;
    @JsonView(UserSimpleView.class)
    private Date birthday;

}

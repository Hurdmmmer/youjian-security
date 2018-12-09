package com.youjian.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author shen youjian
 * @date 12/8/2018 10:35 AM
 */
@Getter
@Setter
public class UserExistException extends RuntimeException {
    private String id;

    public UserExistException(String id) {
        super("User is not exist");
        this.id = id;
    }
}

package com.youjian.security.browser.support;

import lombok.Data;

/**
 * @author shen youjian
 * @date 12/9/2018 10:39 AM
 */
@Data
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }
}

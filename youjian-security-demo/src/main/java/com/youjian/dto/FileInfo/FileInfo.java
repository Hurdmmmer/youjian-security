package com.youjian.dto.FileInfo;

import lombok.Data;

/**
 * @author shen youjian
 * @date 12/8/2018 5:33 PM
 */
@Data
public class FileInfo {

    private String path;

    public FileInfo(String path) {
        this.path = path;
    }
}

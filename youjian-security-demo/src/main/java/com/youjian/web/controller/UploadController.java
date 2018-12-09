package com.youjian.web.controller;

import com.youjian.dto.FileInfo.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * @author shen youjian
 * @date 12/8/2018 5:31 PM
 */
@RestController
@RequestMapping("/file")
public class UploadController {
    private static String folder = "E:\\IdeaWorkspace\\youjian-security\\youjian-security-demo\\src\\main\\java\\com\\youjian\\web\\controller";

    /**
     * @param file 文件上传的名称要和该参数名称一致, 参见 测试类中的上传测试
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        String name = file.getName();
        System.out.println("name = " + name);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        File localFile = new File(folder, new Date().getTime() + ".txt");
        file.transferTo(localFile);


        return new FileInfo(localFile.getPath());
    }

    /**
     * 文件下载
     */
    @GetMapping("/{id}")
    public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (
                InputStream in = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream out = response.getOutputStream();
        ) {
            // 设置返回响应状态参数, 下载
            response.setContentType("application/x-download");
            // 设置返回的文件描述, 文件名称
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(in, out);

        }

    }
}

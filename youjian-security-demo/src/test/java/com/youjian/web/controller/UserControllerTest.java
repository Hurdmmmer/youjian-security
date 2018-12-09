package com.youjian.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author shen youjian
 * @date 2018/12/6 20:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;
    // restful 风格的测试用例
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String json = mockMvc.perform(get("/user")
                // 添加请求参数
                .param("username", "jojo")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                // 期望服务端返回的数据,判断服务器返回的状态吗
                .andExpect(status().isOk())
                // 预期返回的是一个集合, 长度是 3  $.length() 取的是集合的长度表达式
                .andExpect(jsonPath("$.length()").value(3))
                // 获取服务器返回的 字符串数据
                .andReturn().getResponse().getContentAsString();
        System.out.println("json = " + json);
    }


    @Test
    public void whenGetUserSuccess() throws Exception {
        String json = mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tom"))

                .andReturn().getResponse().getContentAsString();

        System.out.println("json = " + json);

    }

    @Test
    public void whenCreateUser() throws Exception {
        Long birthday = new Date().getTime();
        String content = "{\"username\":\"tom\", \"password\":\"123\", \"birthday\":\""+birthday+"\"}";
        String json = mockMvc.perform(post("/user").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))

                .andReturn().getResponse().getContentAsString();

        System.out.println("json = " + json);
    }


    @Test
    public void whenUpdateUser() throws  Exception {
        String content = "{\"username\":\"tom\", \"password\":null, \"birthday\":\""+new Date().getTime()+"\"}";
        String result = mockMvc.perform(put("/user/1").content(content).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("result = " + result);
    }

    @Test
    public void whenDeleteUser() throws Exception{
        mockMvc.perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }


    @Test
    public void whenUploadSuccess() throws Exception {
        /**
         *  模拟一个上传文件请求,
         *  参数一: 请求头中的参数类型为 file, 要与 文件接受的方法中的参数名称一致 {@link UploadController}
         *  参数二: 文件的名称
         *  参数三: 文件的提交方式, "multipart/form-content" 为表单提交
         *  参数四: 文件的 byte 数据
         */
        String json = mockMvc.perform(fileUpload("/file").file(new MockMultipartFile("file", "test.txt", "multipart/form-content",
                "hello upload".getBytes(StandardCharsets.UTF_8))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("json = " + json);
    }


    @Test
    public void whenDownSuccess() {

    }
}

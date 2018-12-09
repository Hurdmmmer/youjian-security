package com.youjian.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.youjian.dto.User;
import com.youjian.exception.UserExistException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author shen youjian
 * @date 2018/12/6 22:02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     *   JsonView 可以控制不同的返回同一对象时, 指定不同的方法,返回不同的字段
     *   1. 创建 2 个不同的接口
     *   2. 使用 JsonView 注解, 再字段上指定输出的 {@link User}
     *   3. 再 controller 方法上指定使用哪一个视图输出数据 {@link UserController}
     */

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam("username") String username) {
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        return Arrays.asList(user, new User(), new User());
    }

    // /user/{id:\\d} 占位符url使用正则表达式
    @GetMapping("/{id:\\d}")
    @JsonView(User.UserDetailView.class)
    public User getUser(@PathVariable("id") String id) {
        System.out.println("id = " + id);

        User user = new User();
        user.setUsername("tom");
        user.setPassword("123");
        return user;
    }

    @PostMapping
    @JsonView(User.UserSimpleView.class)
    public User create(@RequestBody User user) {

        System.out.println("user.toString() = " + user.toString());
        user.setId("1");
        return user;
    }

    @PutMapping("/{id}")
    @JsonView(User.UserDetailView.class)
    public User update(@PathVariable("id") String id, @RequestBody @Valid User user, BindingResult error) {
        if (error.hasErrors()) {
            error.getAllErrors().forEach(e -> System.out.println("e = " + e.getDefaultMessage()));
        }
        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public User delete(@PathVariable("id") String id) {
        throw new RuntimeException("user not exist: " + id);
//        System.out.println("id = " + id);
//        return new User();
    }

}

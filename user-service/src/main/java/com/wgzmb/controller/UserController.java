package com.wgzmb.controller;

import com.wgzmb.component.BaseResponse;
import com.wgzmb.service.UserService;
import com.wgzmb._common.vo.UserBaseVO;
import com.wgzmb._common.vo.UserRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    BaseResponse login(@RequestBody @Validated UserBaseVO userBaseVO) {
        System.out.println(userBaseVO);
        return userService.login(userBaseVO.getUsername(), userBaseVO.getPassword());
    }

    @PostMapping("/register")
    BaseResponse register(@RequestBody UserRegisterVO userRegisterVO) {
        System.out.println(userRegisterVO);
        String username = userRegisterVO.getUsername();
        String password = userRegisterVO.getPassword();

        return userService.register(username, password);
    }

    @PostMapping("/info")
    BaseResponse info(@RequestHeader("Authorization") String token) {
        return userService.info(token);
    }

    @GetMapping("/info/{token}")
    BaseResponse info2(@PathVariable("token") String token) {
        return userService.info(token);
    }
}

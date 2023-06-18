package com.wgzmb.service;

import com.wgzmb.component.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "user-service")
public interface UserFeignInterface {
    @GetMapping("/user/info/{token}")
    public BaseResponse info(@PathVariable("token") String token);
}

package io.study.controller;

import io.study.reqeust.UserDto;
import io.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() { return "<h1>token</h1>"; }

    @PostMapping("/join")
    public String join(@RequestBody UserDto request) {
        userService.createUser(request);
        return "ok";
    }

    @GetMapping("api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("api/v1/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("api/v1/admin")
    public String admin() {
        return "admin";
    }
}

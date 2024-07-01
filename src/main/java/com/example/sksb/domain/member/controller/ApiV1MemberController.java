package com.example.sksb.domain.member.controller;

import com.example.sksb.domain.global.exceptions.GlobalException;
import com.example.sksb.domain.member.entity.Member;
import com.example.sksb.domain.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;


    @Getter
    public static class LoginResponseBody {
        private String username;
        public LoginResponseBody(String username) {
            this.username = username;
        }
    }

    @Getter
    @Setter
    public static class LoginRequestBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public LoginResponseBody login(@Valid @RequestBody LoginRequestBody body) {
        Member member = memberService.findByUsername(body.getUsername())
                .orElseThrow(() -> new GlobalException("400-1", "해당 유저가 존재하지 않음"));

        if(memberService.passwordMatches(member, body.getPassword())) {
            throw new GlobalException("400-2", "비밀번호 불일치");
        }
        return new LoginResponseBody(member.getUsername());
    }
}

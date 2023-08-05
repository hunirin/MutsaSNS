package com.example.project_2_leegwanghun.controller;

import com.example.project_2_leegwanghun.dto.ResponseDto;
import com.example.project_2_leegwanghun.dto.UserDto;
import com.example.project_2_leegwanghun.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    @PostMapping("/register")
    public ResponseDto registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            // UserEntity에 따로 파라미터 안만들고 인증하기 위해
            @RequestParam("passwordCheck") String passwordCheck,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
    ) {
        ResponseDto response = new ResponseDto();
        // 비밀번호와 비밀번호 체크가 일치할 시 유저 등록
        if (password.equals(passwordCheck)) {
            log.info("password match!");
            service.createUser(UserDto.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .phone(phone)
                    .email(email)
                    .profileImg("/static/profileDefault.jpg")
                    .build());

            response.setMessage("가입이 완료되었습니다.");
            return response;
        }
        log.warn("password does not match!");
        response.setMessage("입력한 정보가 틀렸습니다.");
        return response;
    }

    // 로그인
    @PostMapping("/login")
    public UserDto loginAndSaveJwt(
            @RequestBody UserDto userDto
    ) {
        return service.loginUser(userDto);
    }

    // 프로필 사진 업로드
    @PutMapping(
            value = "/profileImg",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseDto uploadImage(
            Long id,
            @RequestPart(value = "image")MultipartFile profileImg,
            Authentication authentication
    ) {
        service.uploadImage(id, profileImg, authentication);
        ResponseDto response = new ResponseDto();
        response.setMessage("프로필 이미지가 등록되었습니다.");
        return response;
    }
}

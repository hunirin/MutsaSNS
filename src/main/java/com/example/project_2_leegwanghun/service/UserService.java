package com.example.project_2_leegwanghun.service;

import com.example.project_2_leegwanghun.dto.UserDto;
import com.example.project_2_leegwanghun.entity.UserEntity;
import com.example.project_2_leegwanghun.jwt.JwtTokenUtils;
import com.example.project_2_leegwanghun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser
                = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        return UserDto.fromEntity(optionalUser.get());
    }

    // 유저를 생성하는 메소드
    public void createUser(UserDetails user) {
        if (this.userExists(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            UserDto userDto = (UserDto) user;
            UserEntity userEntity = userDto.newEntity();

            userRepository.save(userEntity);

        } catch (ClassCastException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 로그인 시 토큰 발급하는 메소드
    public UserDto loginUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDto.getUsername()));

        UserDetails userDetails = UserDto.fromEntity(userEntity);
        if (!passwordEncoder.matches(userDto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String token = jwtTokenUtils.generateToken(userDto);
        userEntity.setToken(token);
        userRepository.save(userEntity);
        return UserDto.fromEntity(userEntity);
    }

    // 유저의 프로필 이미지 등록
    public void uploadImage(Long id, MultipartFile profileImg, Authentication authentication) {

        UserEntity userEntity = getUserEntity(authentication);

        // 폴더명에 userId를 넣기 위해
        String userId = String.valueOf(userEntity.getId());

        // 업로드 위치 : media/{username}
        String profileImgDir = String.format("media/user_%s/", userId);
        try {
            Files.createDirectories(Path.of(profileImgDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 확장자를 포함한 이미지 이름
        String originFilename = profileImg.getOriginalFilename();
        String[] fileNameSplit = originFilename.split("\\.");
        String extension = fileNameSplit[fileNameSplit.length - 1];
        String profileImageFilename = "profile." + extension;
        log.info(profileImageFilename);

        // 폴더와 이미지 이름을 포함한 파일 경로
        String profileImagePath = profileImgDir + profileImageFilename;
        log.info(profileImagePath);

        // MultipartFile 저장
        try {
            profileImg.transferTo(Path.of(profileImagePath));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // UserEntity 업데이트
        log.info(String.format("/static/userId_%s/%s", userId, profileImageFilename));

        userEntity.setProfileImg(String.format("/static/userId_%s/%s", userId, profileImageFilename));
        userRepository.save(userEntity);
    }

    // 유저 정보 조회
    public UserDto readUser(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) return UserDto.fromEntity2(optionalUser.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }





    // 인증된 정보로 유저를 찾는 메소드
    private UserEntity getUserEntity(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean userExists(String username) {
        log.info("check if user: {} exists", username);
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }
}

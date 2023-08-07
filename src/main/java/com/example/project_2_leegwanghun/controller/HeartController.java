package com.example.project_2_leegwanghun.controller;

import com.example.project_2_leegwanghun.dto.HeartDto;
import com.example.project_2_leegwanghun.dto.ResponseDto;
import com.example.project_2_leegwanghun.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article/{articleId}/heart")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final ResponseDto response = new ResponseDto();

    @PostMapping
    public ResponseDto insert(
            @PathVariable Long articleId
    ) {
        heartService.insertHeart(articleId);
        response.setMessage("좋아요를 하였습니다.");
        return response;
    }

}

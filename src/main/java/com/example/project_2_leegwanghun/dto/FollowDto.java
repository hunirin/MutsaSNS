package com.example.project_2_leegwanghun.dto;

import com.example.project_2_leegwanghun.entity.UserEntity;
import lombok.Data;

@Data
public class FollowDto {
    private Long id;

    private Long toUserId;
    private UserEntity fromUser;

}

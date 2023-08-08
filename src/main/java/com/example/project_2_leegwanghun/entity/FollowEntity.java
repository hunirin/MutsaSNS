package com.example.project_2_leegwanghun.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "follow")
@RequiredArgsConstructor
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long followingId;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private UserEntity follower;

    public FollowEntity(Long followingId, UserEntity follower) {
        this.followingId = followingId;
        this.follower = follower;
    }
}

package com.example.project_2_leegwanghun.repository;

import com.example.project_2_leegwanghun.entity.FollowEntity;
import com.example.project_2_leegwanghun.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    FollowEntity findByFollowingIdAndFollower(Long followingId, UserEntity user);

    // FollowEntity에서 follow한 유저를 조회
    @Query("SELECT f.followingId FROM FollowEntity f WHERE f.follower = :follower")
    List<Long> findFollowingIdsByFollower(@Param("follower") UserEntity follower);
}

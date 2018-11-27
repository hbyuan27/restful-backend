package com.hbyuan.demo.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbyuan.demo.user.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}

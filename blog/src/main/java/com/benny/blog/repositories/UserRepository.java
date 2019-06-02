package com.benny.blog.repositories;

import com.benny.blog.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findUserByUserName(String name);

}
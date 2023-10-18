package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userRole.id = 2 and u.firstName =:firstname and u.lastName= :lastname ")
    Optional<List<User>> findByFirstnameAndLastname(String firstname, String lastname);

    @Query("SELECT u FROM User u WHERE u.userRole.id = 2")
    Optional<List<User>> findByRoleId(long roleId);

}

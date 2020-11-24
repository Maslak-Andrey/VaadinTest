package com.masluck.application.repositories;

import com.masluck.application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u " +
    "where concat(u.name , ' ', u.surname, ' ', u.email) " +
    " like concat('%', :name, '%') ")
    List<User> findByName(@Param("name") String name);
}

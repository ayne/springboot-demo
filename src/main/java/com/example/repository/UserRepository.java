package com.example.repository;

import com.example.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by charmanesantiago on 23/03/2017.
 */

//JpaRepository<T, ID> T is the object type which is User. ID is the class type of the Entity's identifier. In this demo, Entity User id is of type Integer
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEmail(String email);
    List<User> findAllByEmailAndName(String email, String name);
    User findOneByEmail(String email);
}

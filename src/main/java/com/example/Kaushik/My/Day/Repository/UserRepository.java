package com.example.Kaushik.My.Day.Repository;

import com.example.Kaushik.My.Day.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findById(int id);
    Users findByEmail(String mail);
    List<Users> findAll();

}


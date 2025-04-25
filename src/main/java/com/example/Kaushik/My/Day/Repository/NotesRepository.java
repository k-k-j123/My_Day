package com.example.Kaushik.My.Day.Repository;

import com.example.Kaushik.My.Day.Model.Notes;
import com.example.Kaushik.My.Day.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
    Notes findById(int Id);
    List<Notes> findByUser(Users user);
}

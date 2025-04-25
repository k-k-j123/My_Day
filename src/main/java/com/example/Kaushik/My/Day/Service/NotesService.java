package com.example.Kaushik.My.Day.Service;

import com.example.Kaushik.My.Day.Model.Notes;
import com.example.Kaushik.My.Day.Model.Users;
import com.example.Kaushik.My.Day.Repository.NotesRepository;
import com.example.Kaushik.My.Day.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;

    public void createNote(Notes note, HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Users user = userRepository.findById(userId);
        if (user != null) {
            note.setUser(user);
            notesRepository.save(note);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Notes getNoteById(int id, HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Notes tempNote = notesRepository.findById(id);
        if (tempNote != null && tempNote.getUser().getUserId() == userId) {
            return tempNote;
        }
        return null;
    }

    public List<Notes> getAllNotesByUser(HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Users user = userRepository.findById(userId);
        if (user != null) {
            return notesRepository.findByUser(user);
        }
        return List.of();
    }

    public Boolean updateNote(int id, Notes updatedNote, HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Notes tempNote = notesRepository.findById(id);
        if (tempNote != null) {
            if (tempNote.getUser().getUserId() == userId) {
                tempNote.setTitle(updatedNote.getTitle());
                tempNote.setContent(updatedNote.getContent());
                tempNote.setDate(updatedNote.getDate());
                notesRepository.save(tempNote);
                return true;
            } else {
                throw new RuntimeException("This note does not belong to the user.");
            }
        }
        return false;
    }

    public Boolean deleteNote(int id, HttpServletRequest request) {
        int userId = (int) request.getAttribute("userId");
        Notes tempNote = notesRepository.findById(id);
        if (tempNote != null) {
            if (tempNote.getUser().getUserId() == userId) {
                notesRepository.delete(tempNote);
                return true;
            } else {
                throw new RuntimeException("This note does not belong to the user.");
            }
        }
        return false;
    }
}

package com.example.Kaushik.My.Day.Controller;

import com.example.Kaushik.My.Day.Model.Notes;
import com.example.Kaushik.My.Day.Service.NotesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/new")
    public void createNote(@RequestBody Notes note, HttpServletRequest req){
        notesService.createNote(note,req);
    }

    @GetMapping("/getNote/{id}")
    public Notes getNote(@PathVariable int id, HttpServletRequest req) {
        return notesService.getNoteById(id, req);
    }

    @GetMapping("/getAllNotes")
    public List<Notes> getAllNote(HttpServletRequest req){
        return notesService.getAllNotesByUser(req);
    }

    @PutMapping("/updateNote/{id}")
    public boolean updateNote(@PathVariable int id, @RequestBody Notes note, HttpServletRequest req){
        return notesService.updateNote(id,note,req);
    }

    @DeleteMapping("/deleteNote/{id}")
    public boolean deleteNote(@PathVariable int id,HttpServletRequest req){
        return notesService.deleteNote(id,req);
    }


}

package com.cuccatti.inventory.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuccatti.inventory.exception.ResourceNotFoundException;
import com.cuccatti.inventory.model.Note;
import com.cuccatti.inventory.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	private static Logger logger = LogManager.getLogger(NoteController.class);

	@Autowired
	NoteRepository noteRepository;

	// Get All Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		logger.info("Accessing get all notes.");
		return noteRepository.findAll();
	}

	// Create a new Note
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		logger.info("Accessing create new note: title: {}, content: {}", note.getTitle(), note.getContent());
		return noteRepository.save(note);
	}

	// Get a Single Note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
		logger.info("Accessing get note by id for id = {}", noteId);
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}

	// Update a Note
	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
		logger.info("Accessing update note for id {}, setting title = {}, content = {}", noteId, noteDetails.getTitle(),
				noteDetails.getContent());
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());

		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	// Delete a Note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		noteRepository.delete(note);

		return ResponseEntity.ok().build();
	}
}

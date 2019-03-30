package com.cuccatti.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cuccatti.inventory.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
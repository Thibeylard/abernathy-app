package com.mediscreen.abernathyapp.note.repositories;

import com.mediscreen.abernathyapp.note.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "note", collectionResourceRel = "note")
public interface NoteRepository extends MongoRepository<Note, String> {
}
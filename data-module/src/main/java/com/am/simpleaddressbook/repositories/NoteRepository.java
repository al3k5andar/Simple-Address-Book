package com.am.simpleaddressbook.repositories;

import com.am.simpleaddressbook.domain.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
    
}

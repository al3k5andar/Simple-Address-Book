package com.am.simpleaddressbook.service.map;

import com.am.simpleaddressbook.domain.Note;
import com.am.simpleaddressbook.service.NoteService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class NoteServiceMapImpl extends AbstractContactMap<Long, Note> implements NoteService {
    @Override
    public Note save(Note note) {
        return super.save(note);
    }

    @Override
    public Set<Note> findAll() {
        return super.findAll();
    }

    @Override
    public Note findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Note note) {
        super.delete(note);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }
}

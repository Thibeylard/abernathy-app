package com.mediscreen.abernathyapp.note.validators;

import com.mediscreen.abernathyapp.note.models.Note;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class NoteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Note.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}

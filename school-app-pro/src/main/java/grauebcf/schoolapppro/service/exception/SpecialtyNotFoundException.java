package grauebcf.schoolapppro.service.exception;

import grauebcf.schoolapppro.model.Specialty;

public class SpecialtyNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public SpecialtyNotFoundException(String message) {super(message);}
}

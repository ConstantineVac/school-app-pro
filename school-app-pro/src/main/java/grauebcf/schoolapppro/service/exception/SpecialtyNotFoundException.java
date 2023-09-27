package grauebcf.schoolapppro.service.exception;

public class SpecialtyNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public SpecialtyNotFoundException(Long message) {super(String.valueOf(message));}
}

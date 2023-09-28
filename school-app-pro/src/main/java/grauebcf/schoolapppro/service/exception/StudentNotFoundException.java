package grauebcf.schoolapppro.service.exception;

public class StudentNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public StudentNotFoundException(Long message) {super(String.valueOf(message));}
}

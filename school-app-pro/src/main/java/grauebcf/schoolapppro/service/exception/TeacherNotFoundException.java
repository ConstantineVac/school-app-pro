package grauebcf.schoolapppro.service.exception;

public class TeacherNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public TeacherNotFoundException(Long message) {super(String.valueOf(message));}
}

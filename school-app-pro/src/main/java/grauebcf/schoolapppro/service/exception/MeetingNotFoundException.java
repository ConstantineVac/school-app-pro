package grauebcf.schoolapppro.service.exception;

public class MeetingNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public MeetingNotFoundException(Long message) {super(String.valueOf(message));}
}

package grauebcf.schoolapppro.service.exception;

public class CityNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(Long message) {super(String.valueOf(message));}

    public CityNotFoundException() {

    }
}

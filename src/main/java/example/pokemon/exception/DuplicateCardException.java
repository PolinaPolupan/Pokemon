package example.pokemon.exception;

public class DuplicateCardException extends RuntimeException {
    public DuplicateCardException(String message) {
        super(message);
    }
}
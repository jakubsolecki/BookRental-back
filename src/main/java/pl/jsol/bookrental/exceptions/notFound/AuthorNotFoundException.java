package pl.jsol.bookrental.exceptions.notFound;

public class AuthorNotFoundException extends NotFoundException {

    public AuthorNotFoundException(Long id) {
        super("Could not find author " + id);
    }
}

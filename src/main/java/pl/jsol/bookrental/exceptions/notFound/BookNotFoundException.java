package pl.jsol.bookrental.exceptions.notFound;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }
}

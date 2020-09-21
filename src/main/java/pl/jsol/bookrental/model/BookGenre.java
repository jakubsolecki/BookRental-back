package pl.jsol.bookrental.model;

public enum BookGenre {
    FANTASY, SCIENCE_FICTION, ADVENTURE, ROMANCE, HORROR, THRILLER, OTHER;

    public static BookGenre fromString(String genre) {
        try {
            return BookGenre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return OTHER;
        }
    }
}

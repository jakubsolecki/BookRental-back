package pl.jsol.bookrental.model;

public enum Genre {
    FANTASY, SCIENCE_FICTION, ADVENTURE, ROMANCE, HORROR, THRILLER, OTHER;

    public static Genre parseGenre(String genre) {
        try {
            return Genre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return OTHER;
        }
    }
}

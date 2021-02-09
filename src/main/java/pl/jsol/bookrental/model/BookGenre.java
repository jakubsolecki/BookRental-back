package pl.jsol.bookrental.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookGenre {
    FANTASY, SCIENCE_FICTION, ADVENTURE, ROMANCE, HORROR, THRILLER, OTHER;

    @JsonCreator
    public static BookGenre toEnum(String genre) {
        try {
            return BookGenre.valueOf(genre.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return OTHER;
        }
    }

    @JsonValue
    public String toJson() {
        return this.toString();
    }
}

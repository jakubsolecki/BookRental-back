package pl.jsol.bookrental.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import pl.jsol.bookrental.exceptions.EntityNotFoundException;
import pl.jsol.bookrental.model.entity.Author;
import pl.jsol.bookrental.model.entity.Book;
import pl.jsol.bookrental.service.AuthorService;

import java.io.IOException;

@RequiredArgsConstructor
public class BookDeserializer extends JsonDeserializer<Book> {

    private final AuthorService authorService;

    // TODO handling exceptions using my handlers for controllers
    @Override
    public Book deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, EntityNotFoundException, IllegalArgumentException {
        try {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            Long authorId = node.get("authorId").asLong();
            Author author = authorService.findAuthorById(authorId);

            return new Book(node.get("title").textValue(), author, node.get("genre").textValue());
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException("Json format does not match Book");
        }
    }
}

package pl.library.api.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.library.adapters.mysql.model.book.Book;
import pl.library.adapters.mysql.model.genre.Genre;
import pl.library.api.book.dto.BookRequest;
import pl.library.api.book.dto.CreateBookResponse;
import pl.library.api.book.dto.GetBookResponse;
import pl.library.domain.book.BookServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookServiceImpl bookService;

    @GetMapping("/search")
    public List<GetBookResponse> getAllBooksByPhrase(@RequestParam String phrase) {
        List<Book> gainedBooks = bookService.getAllByPhrase(phrase);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/search/random")
    public List<GetBookResponse> getNBooksByRandom(@RequestParam Byte number) {
        List<Book> gainedBooks = bookService.getNumberRandomBooks(number);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GetBookResponse getBookByIdAndTitle(@PathVariable Long id,
                                               @RequestParam String title) {
        Book gainedBook = bookService.getByIdAndTitle(id, title);
        return new GetBookResponse(gainedBook);
    }

    @GetMapping("/search/genre")
    public List<GetBookResponse> getAllBooksByGenres(@RequestBody Genre genre) {
        List<Book> gainedBooks = bookService.getAllByGenres(genre);
        return gainedBooks.stream().map(GetBookResponse::new).collect(Collectors.toList());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponse addSingleOrManyBooks(@Valid @RequestBody BookRequest bookRequest) {
        Book gainedBook = bookService.addBook(bookRequest.toBook());
        return new CreateBookResponse(gainedBook.getId());
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookResponse subtractBookAvailable(@PathVariable Long id,
                                                    @Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest.toBook());
        return new CreateBookResponse(updatedBook.getId());
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.bookDeletion(id);
    }
}

package pl.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.library.domain.model.book.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitle(String title);
    List<Book> findAllByAuthor(String author);
    List<Book> findAllByBookType(String bookType);
    Boolean existsByTitleAndAuthor(String title, String author);
}
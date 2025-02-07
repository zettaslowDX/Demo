package com.evaluacion.demo.services.books;

import com.evaluacion.demo.models.Libro;

import java.util.List;
import java.util.Map;

public interface BooksService {
    Libro getBookById(Long id);

    List<Libro> getAllBooks();

    Libro updateBook(Libro libro);

    void deleteBook(Long id);

    Libro createBook(Libro libro);

    Libro patchBook(long id, Map<String, Object> updates);
}

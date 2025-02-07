package com.evaluacion.demo.services.books;

import com.evaluacion.demo.exceptions.FieldNotFoundException;
import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Libro;
import com.evaluacion.demo.repositories.books.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class booksServiceImpl implements BooksService {
    private final Logger logger = LoggerFactory.getLogger(booksServiceImpl.class);
    private final BooksRepository booksRepository;

    @Autowired
    public booksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Libro getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return booksRepository.findById(id).orElseThrow(() -> {
            logger.error("Book with ID: {} not found", id);
            return new ItemNotFoundException("Book not found");
        });
    }

    @Override
    public List<Libro> getAllBooks() {
        logger.info("Fetching all books");
        return booksRepository.findAll();
    }

    @Override
    public Libro updateBook(Libro libro) {
        logger.info("Updating book with ID: {}", libro.getId());
        Libro existingBook = null;
            existingBook = booksRepository.findById(libro.getId()).orElseThrow(() -> {
                logger.error("Book with ID: {} not found cannot update", libro.getId());
                return new ItemNotFoundException("no hay libro");
            });
        existingBook.setAutor(libro.getAutor());
        existingBook.setIsbn(libro.getIsbn());
        existingBook.setFechaPublicacion(libro.getFechaPublicacion());
        existingBook.setTitulo(libro.getTitulo());
        logger.debug("Updated book details: {}", existingBook);
        return booksRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        booksRepository.deleteById(id);
    }


    @Override
    public Libro createBook(Libro libro) {
        logger.info("Creating new book: {}", libro);
        return booksRepository.save(libro);
    }

    @Override
    public Libro patchBook(long id, Map<String, Object> updates) {
        logger.info("Patching book with ID: {}", id);
        Libro existingBook = null;

        existingBook = booksRepository.findById(id).orElseThrow(() -> {
            logger.error("Book with ID: {} not found cannot patch", id);
            return new ItemNotFoundException("no hay libro");
        });
        Libro finalExistingBook = existingBook;
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Libro.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, finalExistingBook, value);
                logger.debug("Patched field: {} with value: {}", key, value);
            } else {
                logger.error("Field: {} not found in Libro class", key);

                throw new FieldNotFoundException("no existe el campo");
            }
        });
        return booksRepository.save(existingBook);
    }
}

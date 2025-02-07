package com.evaluacion.demo;

import com.evaluacion.demo.exceptions.FieldNotFoundException;
import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Libro;
import com.evaluacion.demo.repositories.books.BooksRepository;
import com.evaluacion.demo.services.books.booksServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BooksServiceImplTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private booksServiceImpl booksService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("Test Book");
        libro.setAutor("Test Author");
        libro.setIsbn("1234567890");
        libro.setFechaPublicacion(LocalDate.of(2025, 1, 1));
    }

    @Test
    void testGetBookById_BookNotFound() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            booksService.getBookById(1L);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBook_BookNotFound() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            booksService.updateBook(libro);
        });

        assertEquals("no hay libro", exception.getMessage());
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void testPatchBook_FieldNotFound() {
        when(booksRepository.findById(1L)).thenReturn(Optional.of(libro));

        Map<String, Object> updates = Map.of("nonExistentField", "value");

        FieldNotFoundException exception = assertThrows(FieldNotFoundException.class, () -> {
            booksService.patchBook(1L, updates);
        });

        assertEquals("no existe el campo", exception.getMessage());
        verify(booksRepository, times(1)).findById(1L);
    }

    @Test
    void testPatchBook_BookNotFound() {
        when(booksRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> updates = Map.of("titulo", "Nuevo Titulo");

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            booksService.patchBook(1L, updates);
        });

        assertEquals("no hay libro", exception.getMessage());
        verify(booksRepository, times(1)).findById(1L);
    }
}
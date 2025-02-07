package com.evaluacion.demo;

import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Libro;
import com.evaluacion.demo.models.Prestamo;
import com.evaluacion.demo.models.Usuario;
import com.evaluacion.demo.repositories.books.BooksRepository;
import com.evaluacion.demo.repositories.loans.LoansRepository;
import com.evaluacion.demo.repositories.users.UsersRepository;
import com.evaluacion.demo.services.loans.loansServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoansServiceImplTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private LoansRepository loansRepository;

    @InjectMocks
    private loansServiceImpl loansService;

    private Prestamo prestamo;
    private Libro libro;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("El Quijote");
        libro.setAutor("Miguel de Cervantes");
        libro.setIsbn("978-3-16-148410-0");
        libro.setFechaPublicacion(LocalDate.of(1605, 1, 16));

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john.doe@example.com");
        usuario.setTelefono("123456789");
        usuario.setFechaRegistro(LocalDate.now());

        prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(LocalDate.now().plusDays(14));
    }

    @Test
    void testGetLoanById_LoanExists() {
        when(loansRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo foundLoan = loansService.getLoanById(1L);

        assertNotNull(foundLoan);
        assertEquals("El Quijote", foundLoan.getLibro().getTitulo());
    }

    @Test
    void testGetLoanById_LoanDoesNotExist() {
        when(loansRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> loansService.getLoanById(1L));
    }

    @Test
    void testGetAllLoans() {
        List<Prestamo> prestamos = Arrays.asList(prestamo);
        when(loansRepository.findAll()).thenReturn(prestamos);

        List<Prestamo> allLoans = loansService.getAllLoans();

        assertNotNull(allLoans);
        assertEquals(1, allLoans.size());
    }

    @Test
    void testUpdateLoan_LoanExists() {
        when(loansRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(loansRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo updatedLoan = loansService.updateLoan(prestamo);

        assertNotNull(updatedLoan);
        assertEquals("El Quijote", updatedLoan.getLibro().getTitulo());
    }

    @Test
    void testUpdateLoan_LoanDoesNotExist() {
        when(loansRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> loansService.updateLoan(prestamo));
    }

    @Test
    void testDeleteLoan() {
        doNothing().when(loansRepository).deleteById(1L);

        loansService.deleteLoan(1L);

        verify(loansRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateLoan() {
        when(booksRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(usersRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(loansRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Prestamo createdLoan = loansService.createLoan(prestamo);

        assertNotNull(createdLoan);
        assertEquals("El Quijote", createdLoan.getLibro().getTitulo());
    }

    @Test
    void testPatchLoan_LoanExists() {
        when(loansRepository.findById(1L)).thenReturn(Optional.of(prestamo));
        when(loansRepository.save(any(Prestamo.class))).thenReturn(prestamo);

        Map<String, Object> updates = new HashMap<>();
        updates.put("fechaDevolucion", LocalDate.now().plusDays(21));

        Prestamo patchedLoan = loansService.patchLoan(1L, updates);

        assertNotNull(patchedLoan);
        assertEquals(LocalDate.now().plusDays(21), patchedLoan.getFechaDevolucion());
    }

    @Test
    void testPatchLoan_LoanDoesNotExist() {
        when(loansRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> updates = new HashMap<>();
        updates.put("fechaDevolucion", LocalDate.now().plusDays(21));

        assertThrows(NullPointerException.class, () -> loansService.patchLoan(1L, updates));
    }
}

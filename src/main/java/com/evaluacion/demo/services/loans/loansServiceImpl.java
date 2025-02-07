package com.evaluacion.demo.services.loans;

import com.evaluacion.demo.exceptions.FieldNotFoundException;
import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Libro;
import com.evaluacion.demo.models.Prestamo;
import com.evaluacion.demo.models.Usuario;
import com.evaluacion.demo.repositories.books.BooksRepository;
import com.evaluacion.demo.repositories.loans.LoansRepository;
import com.evaluacion.demo.repositories.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class loansServiceImpl implements LoansService {
    private final LoansRepository loansRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final Logger logger = LoggerFactory.getLogger(loansServiceImpl.class);

    @Autowired
    public loansServiceImpl(LoansRepository loanRepository, UsersRepository usersRepository, BooksRepository booksRepository) {
        this.loansRepository = loanRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    @Override
    public Prestamo getLoanById(Long id) {
        logger.info("Fetching loan with ID: {}", id);
        return loansRepository.findById(id).orElseThrow(() -> {
            logger.error("Loan with ID: {} not found", id);
            return new ItemNotFoundException("El prestamo no existe");
        });
    }

    @Override
    public List<Prestamo> getAllLoans() {
        logger.info("Fetching all loans");
        return loansRepository.findAll();
    }

    @Override
    public Prestamo updateLoan(Prestamo prestamo) {
        logger.info("Updating loan with ID: {}", prestamo.getId());
        Prestamo existingLoan = null;
        existingLoan = loansRepository.findById(prestamo.getId()).orElseThrow(() -> {
            logger.error("Loan with ID: {} not found, cannot Update", prestamo.getId());
            return new ItemNotFoundException("no hay usuario");
        });
        existingLoan.setLibro(prestamo.getLibro());
        existingLoan.setUsuario(prestamo.getUsuario());
        existingLoan.setFechaPrestamo(prestamo.getFechaPrestamo());
        existingLoan.setFechaDevolucion(prestamo.getFechaDevolucion());
        logger.debug("Updated loan details: {}", existingLoan);
        return loansRepository.save(prestamo);
    }

    @Override
    public void deleteLoan(Long id) {
        logger.info("Deleting loan with ID: {}", id);
        loansRepository.deleteById(id);
    }

    @Override
    public Prestamo createLoan(Prestamo prestamo) {
        logger.info("Creating new loan: {}", prestamo);
        Libro libro = null;
        libro = booksRepository.findById(prestamo.getLibro().getId())
                .orElseThrow(() -> {
                    logger.error("Book with ID: {} not found", prestamo.getLibro().getId());
                    return new ItemNotFoundException("Libro no encontrado");
                });
        Usuario usuario = null;
        usuario = usersRepository.findById(prestamo.getUsuario().getId())
                .orElseThrow(() -> {
                    logger.error("User with ID: {} not found", prestamo.getUsuario().getId());
                    return new ItemNotFoundException("Usuario no encontrado");
                });
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setLibro(libro);
        nuevoPrestamo.setUsuario(usuario);
        nuevoPrestamo.setFechaPrestamo(prestamo.getFechaPrestamo());
        nuevoPrestamo.setFechaDevolucion(prestamo.getFechaDevolucion());

        Prestamo savedPrestamo = loansRepository.save(nuevoPrestamo);
        logger.debug("Updated Loan with value: {}", prestamo);
        return savedPrestamo;
    }

    @Override
    public Prestamo patchLoan(long id, Map<String, Object> updates) {
        logger.info("Patching loan with ID: {}", id);
        Prestamo existingLoan = loansRepository.findById(id).orElseThrow(() -> {
            logger.error("Loan with ID: {} not found, cannot patch", id);
            return new NullPointerException("no hay prestamo");
        });
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Prestamo.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingLoan, value);
                logger.debug("Patched field: {} with value: {}", key, value);
            } else {
                logger.error("Field: {} not found in Prestamo class", key);
                throw new FieldNotFoundException("Field does not exist");
            }
        });
        return loansRepository.save(existingLoan);
    }
}

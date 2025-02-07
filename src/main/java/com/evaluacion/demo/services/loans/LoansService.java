package com.evaluacion.demo.services.loans;

import com.evaluacion.demo.models.Prestamo;

import java.util.List;
import java.util.Map;

public interface LoansService {
    Prestamo getLoanById(Long id);

    List<Prestamo> getAllLoans();

    Prestamo updateLoan(Prestamo prestamo);

    void deleteLoan(Long id);

    Prestamo createLoan(Prestamo prestamo);

    Prestamo patchLoan(long id, Map<String, Object> updates);
}

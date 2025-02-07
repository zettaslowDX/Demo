package com.evaluacion.demo.repositories.loans;

import com.evaluacion.demo.models.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<Prestamo, Long> {
}

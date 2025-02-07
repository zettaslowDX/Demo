package com.evaluacion.demo.controllers;

import com.evaluacion.demo.models.Prestamo;
import com.evaluacion.demo.services.loans.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prestamos")
public class loanController {

    LoansService loansService;
    @Autowired
    public loanController(LoansService loansService) {
        this.loansService = loansService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getLoanById(@PathVariable long id){
        Prestamo prestamo = loansService.getLoanById(id);
        return ResponseEntity.ok(prestamo);
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>>getAllLoans(){
        List<Prestamo> prestamos=loansService.getAllLoans();
        return ResponseEntity.ok(prestamos);
    }

    @PostMapping
    public ResponseEntity<Prestamo> createLoan(@RequestBody Prestamo prestamo){
        Prestamo createdLoan = loansService.createLoan(prestamo);
        return ResponseEntity.ok(prestamo);
    }

    @PutMapping()
    public ResponseEntity<Prestamo> updateLoan(@RequestBody Prestamo prestamo){
        Prestamo updatedPrestamo=loansService.updateLoan(prestamo);
        return ResponseEntity.ok(prestamo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Prestamo> patchLoan(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        Prestamo patchedLoan = loansService.patchLoan(id,updates);
        return ResponseEntity.ok(patchedLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteLoan(@PathVariable Long id){
        loansService.deleteLoan(id);
        return ResponseEntity.ok(id);
    }

}

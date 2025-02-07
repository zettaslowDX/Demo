package com.evaluacion.demo.controllers;

import com.evaluacion.demo.models.Prestamo;
import com.evaluacion.demo.models.Usuario;
import com.evaluacion.demo.services.loans.LoansService;
import com.evaluacion.demo.services.users.UsersService;
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
@RequestMapping("/usuarios")
public class userController {
    UsersService usersService;
    @Autowired
    public userController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable long id){
        Usuario usuario = usersService.getUserById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>>getAllUsers(){
        List<Usuario> usuarios=usersService.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario){
        Usuario createdUser = usersService.createUser(usuario);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping()
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuario){
        Usuario updatedUser=usersService.updateUser(usuario);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates){
        Usuario patchedUser = usersService.patchUser(id,updates);
        return ResponseEntity.ok(patchedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
        return ResponseEntity.ok(id);
    }
}

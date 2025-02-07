package com.evaluacion.demo.repositories.users;

import com.evaluacion.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Usuario, Long> {

}

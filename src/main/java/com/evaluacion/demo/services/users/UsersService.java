package com.evaluacion.demo.services.users;

import com.evaluacion.demo.models.Usuario;

import java.util.List;
import java.util.Map;

public interface UsersService {
    Usuario getUserById(Long id);

    List<Usuario> getAllUsers();

    Usuario updateUser(Usuario usuario);

    void deleteUser(Long id);

    Usuario createUser(Usuario usuario);

    Usuario patchUser(long id, Map<String, Object> updates);
}

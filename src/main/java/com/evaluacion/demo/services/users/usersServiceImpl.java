package com.evaluacion.demo.services.users;

import com.evaluacion.demo.exceptions.FieldNotFoundException;
import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Usuario;
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
public class usersServiceImpl implements UsersService {
    private final Logger logger = LoggerFactory.getLogger(usersServiceImpl.class);
    private final UsersRepository usersRepository;

    @Autowired
    public usersServiceImpl(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    @Override
    public Usuario getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return usersRepository.findById(id).orElseThrow(() -> {
            logger.error("User with ID: {} not found", id);
            return new ItemNotFoundException("El usuario no existe");
        });

    }

    @Override
    public List<Usuario> getAllUsers() {
        logger.info("Fetching all users");
        return usersRepository.findAll();
    }

    @Override
    public Usuario updateUser(Usuario usuario) {
        logger.info("Updating user with ID: {}", usuario.getId());
        Usuario existingUser = null;

        existingUser = usersRepository.findById(usuario.getId()).orElseThrow(() -> {
            logger.error("User with ID: {} not found, cannot update", usuario.getId());
            return new ItemNotFoundException("no hay usuario");
        });

        existingUser.setNombre(usuario.getNombre());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setTelefono(usuario.getTelefono());
        existingUser.setFechaRegistro(usuario.getFechaRegistro());
        logger.debug("Updated user details: {}", existingUser);
        return usersRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        usersRepository.deleteById(id);
    }

    public Usuario createUser(Usuario usuario) {
        logger.info("Creating new user: {}", usuario);
        return usersRepository.save(usuario);
    }


    public Usuario patchUser(long id, Map<String, Object> updates) {
        logger.info("Patching user with ID: {}", id);
        Usuario existingUser = null;
        existingUser = usersRepository.findById(id).orElseThrow(() -> {
            logger.error("User with ID: {} not found cannot patch", id);
            return new ItemNotFoundException("no hay usuario");
        });

        Usuario finalExistingUser = existingUser;
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Usuario.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, finalExistingUser, value);
                logger.debug("Patched field: {} with value: {}", key, value);
            } else {
                logger.error("Field: {} not found in Usuario class", key);
                throw new FieldNotFoundException("no existe el campo");
            }
        });
        return usersRepository.save(existingUser);
    }

}

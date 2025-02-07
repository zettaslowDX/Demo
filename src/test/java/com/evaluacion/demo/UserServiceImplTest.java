package com.evaluacion.demo;

import com.evaluacion.demo.exceptions.ItemNotFoundException;
import com.evaluacion.demo.models.Usuario;
import com.evaluacion.demo.repositories.users.UsersRepository;
import com.evaluacion.demo.services.users.usersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private usersServiceImpl usersService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("John Doe");
        usuario.setEmail("john.doe@example.com");
        usuario.setTelefono("123456789");
        usuario.setFechaRegistro(LocalDate.now());
    }

    @Test
    void testGetUserById_UserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario foundUser = usersService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getNombre());
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> usersService.getUserById(1L));
    }

    @Test
    void testGetAllUsers() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usersRepository.findAll()).thenReturn(usuarios);

        List<Usuario> allUsers = usersService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
    }

    @Test
    void testUpdateUser_UserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usersRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario updatedUser = usersService.updateUser(usuario);

        assertNotNull(updatedUser);
        assertEquals("John Doe", updatedUser.getNombre());
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> usersService.updateUser(usuario));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(usersRepository).deleteById(1L);

        usersService.deleteUser(1L);

        verify(usersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCreateUser() {
        when(usersRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario createdUser = usersService.createUser(usuario);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getNombre());
    }

    @Test
    void testPatchUser_UserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usersRepository.save(any(Usuario.class))).thenReturn(usuario);

        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", "Jane Doe");

        Usuario patchedUser = usersService.patchUser(1L, updates);

        assertNotNull(patchedUser);
        assertEquals("Jane Doe", patchedUser.getNombre());
    }

    @Test
    void testPatchUser_UserDoesNotExist() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", "Jane Doe");

        assertThrows(ItemNotFoundException.class, () -> usersService.patchUser(1L, updates));
    }
}
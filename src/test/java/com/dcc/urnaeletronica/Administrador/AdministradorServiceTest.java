package com.dcc.urnaeletronica.Administrador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.dcc.urnaeletronica.dao.DaoAdministrador;
import com.dcc.urnaeletronica.exceptions.AdministradorServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.service.AdministradorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class AdministradorServiceTest {

    @Autowired
    private AdministradorService administradorService;

    @MockBean
    private DaoAdministrador daoAdministrador;

    private String username;

    private String senha;

    @BeforeEach
    void setUp() {
        username = "user";
        senha = "123";
    }

    @Test
    void testAutenticar() throws AdministradorServiceException {

        Administrador administrador = new Administrador(username, senha);

        when(this.daoAdministrador.findByUsernameAndSenha(anyString(), anyString())).thenReturn(Optional.of(administrador));

        final Administrador administradorResponse = this.administradorService.autenticar(username, senha);
        
        assertNotNull(administradorResponse);

        verify(this.daoAdministrador, times(1)).findByUsernameAndSenha(anyString(), anyString());
    }

    @Test
    void testAutenticarNãoEncontraAdministrador() throws AdministradorServiceException {
        Exception exception = assertThrows(AdministradorServiceException.class, () -> {
            this.administradorService.autenticar(anyString(), anyString());
        });
        
        assertEquals("Administrador não encontrado.", exception.getMessage());
    }
}
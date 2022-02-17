package com.dcc.urnaeletronica.Eleitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.dcc.urnaeletronica.dao.DaoEleitor;
import com.dcc.urnaeletronica.exceptions.EleitorServiceException;
import com.dcc.urnaeletronica.model.Eleitor;
import com.dcc.urnaeletronica.service.EleitorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EleitorServiceTest {
    
    @Autowired
    private EleitorService eleitorService;

    @MockBean
    private DaoEleitor daoEleitor;

    private Eleitor eleitor;

    @BeforeEach
    void setUp() {
        eleitor = new Eleitor("0000000001", 222, 001, true);
    }

    @Test
    void testAutenticar() throws EleitorServiceException {

        when(this.daoEleitor.findByTituloEleitor(anyString())).thenReturn(Optional.of(eleitor));

        final Eleitor eleitorResponse = this.eleitorService.autenticar("0000000001");
        
        assertNotNull(eleitorResponse);

        verify(this.daoEleitor, times(1)).findByTituloEleitor(anyString());
    }

    @Test
    void testAutenticarEleitorNaoEncontrado() {
        Exception exception = assertThrows(EleitorServiceException.class, () -> {
            this.eleitorService.autenticar(anyString());
        });
        
        assertEquals("Eleitor não encontrado.", exception.getMessage());
    }

    @Test
    void testMarcaQueVotou() throws EleitorServiceException {

        when(this.daoEleitor.findByTituloEleitor(anyString())).thenReturn(Optional.of(eleitor));
        when(this.daoEleitor.save(any(Eleitor.class))).thenReturn(eleitor);

        this.eleitorService.marcaQueVotou(anyString());

        verify(this.daoEleitor, times(1)).findByTituloEleitor(anyString());
        verify(this.daoEleitor, times(1)).save(any(Eleitor.class));
    }

    @Test
    void testVerificaSeEleitorVotou() throws EleitorServiceException {
        when(this.daoEleitor.findByTituloEleitor(anyString())).thenReturn(Optional.of(eleitor));

        boolean votou = this.eleitorService.verificaSeEleitorVotou("0000000001");
        assertTrue(votou);

        verify(this.daoEleitor, times(1)).findByTituloEleitor(anyString());
    }
}

package com.dcc.urnaeletronica.Eleicao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dcc.urnaeletronica.dao.DaoEleicao;
import com.dcc.urnaeletronica.exceptions.EleicaoServiceException;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.service.EleicaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class EleicaoServiceTest {
    
    @Autowired
    private EleicaoService eleicaoService;

    @MockBean
    private DaoEleicao daoEleicao;

    private Eleicao eleicao;

    private Administrador administrador;

    @BeforeEach
    void setUp() {
        administrador = new Administrador("user", "123");
        eleicao = new Eleicao(2022, true, administrador);
    }

    @Test
    void testBuscarTodasAsEleicoes() {

        when(this.daoEleicao.findAll()).thenReturn(List.of(eleicao));

        final List<Eleicao> eleicoes = this.eleicaoService.buscarTodos();

        assertNotNull(eleicoes);
        assertFalse(eleicoes.isEmpty());

        verify(this.daoEleicao, times(1)).findAll();
    }

    @Test
    void testSalvarEleicao() {

        when(this.daoEleicao.save(any(Eleicao.class))).thenReturn(eleicao);

        final Eleicao eleicaoResponse = this.eleicaoService.salvar(eleicao);
        assertNotNull(eleicaoResponse);

        verify(this.daoEleicao, times(1)).save(any(Eleicao.class));
    }

    @Test
    void testRemover() {

        doNothing().when(this.daoEleicao).deleteById(anyLong());

        this.eleicaoService.remover(1L);

        verify(this.daoEleicao, times(1)).deleteById(anyLong());
    }

    @Test
    void testBuscarPorId() throws EleicaoServiceException {
        
        when(this.daoEleicao.findById(anyLong())).thenReturn(Optional.of(eleicao));

        final Eleicao eleicaoResponse = this.eleicaoService.buscarPeloId(1L);
        assertNotNull(eleicaoResponse);

        verify(this.daoEleicao, times(1)).findById(anyLong());
    }

    @Test
    void testBuscarNãoEncontrado() throws EleicaoServiceException {
        Exception exception = assertThrows(EleicaoServiceException.class, () -> {
            this.eleicaoService.buscarPeloId(anyLong());
        });
        
        assertEquals("Eleição não encontrada.", exception.getMessage());
    }

    @Test
    void testBuscaEleicaoAtivaQuandoExiste() throws EleicaoServiceException {
        when(this.daoEleicao.buscaEleicaoAtiva()).thenReturn(Optional.of(eleicao));

        final Eleicao eleicaoResponse = this.eleicaoService.retornaEleicaoAtiva();
        assertNotNull(eleicaoResponse);

        verify(this.daoEleicao, times(1)).buscaEleicaoAtiva();
    }

    @Test
    void testBuscaEleicaoAtivaQuandoNãoExiste() throws EleicaoServiceException {
        Exception exception = assertThrows(EleicaoServiceException.class, () -> {
            this.eleicaoService.retornaEleicaoAtiva();
        });
        
        assertEquals("Não há eleições ativas.", exception.getMessage());
    }

    @Test
    void testTemEleicaoAtiva() {
        List<Eleicao> eleicoes = new ArrayList<>();
        Eleicao eleicao2 = new Eleicao(2021, false, administrador);
        eleicoes.add(eleicao);
        eleicoes.add(eleicao2);

        when(this.daoEleicao.findAll()).thenReturn(eleicoes);

        Boolean temEleicaoAtiva = this.eleicaoService.temEleicaoAtiva();
        assertTrue(temEleicaoAtiva);

        verify(this.daoEleicao, times(1)).findAll();
    }

    @Test
    void testNãoTemEleicaoAtiva() {
        List<Eleicao> eleicoes = new ArrayList<>();
        Eleicao eleicao2 = new Eleicao(2021, false, administrador);
        Eleicao eleicao3 = new Eleicao(2022, false, administrador);
        eleicoes.add(eleicao2);
        eleicoes.add(eleicao3);

        when(this.daoEleicao.findAll()).thenReturn(eleicoes);

        Boolean temEleicaoAtiva = this.eleicaoService.temEleicaoAtiva();
        assertFalse(temEleicaoAtiva);

        verify(this.daoEleicao, times(1)).findAll();
    }
}

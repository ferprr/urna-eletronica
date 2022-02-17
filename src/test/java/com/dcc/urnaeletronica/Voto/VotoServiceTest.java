package com.dcc.urnaeletronica.Voto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import com.dcc.urnaeletronica.dao.DaoCandidato;
import com.dcc.urnaeletronica.dao.DaoVoto;
import com.dcc.urnaeletronica.model.Administrador;
import com.dcc.urnaeletronica.model.Candidato;
import com.dcc.urnaeletronica.model.Cargo;
import com.dcc.urnaeletronica.model.Eleicao;
import com.dcc.urnaeletronica.model.Partido;
import com.dcc.urnaeletronica.model.TipoVoto;
import com.dcc.urnaeletronica.model.Voto;
import com.dcc.urnaeletronica.service.VotoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class VotoServiceTest {

    @Autowired
    private VotoService votoService;

    @MockBean 
    private DaoVoto daoVoto;

    @MockBean
    private DaoCandidato daoCandidato;

    private Cargo cargo1;

    private Cargo cargo2;

    private Partido partido1;

    private Partido partido2;

    private Partido partido3;

    private Candidato candidato1;

    private Candidato candidato2;

    private Candidato candidato3;

    private Eleicao eleicao;

    private Administrador administrador;

    @BeforeEach
    void setUp() {
        administrador = new Administrador("user", "123");
        eleicao = new Eleicao(2022, true, administrador);
        cargo1 = Cargo.PRESIDENTE;
        cargo2 = Cargo.SENADOR;
        partido1 = new Partido("XPTO", 4422);
        partido2 = new Partido("C3P0", 4411);
        partido3 = new Partido("ABC", 4400);
        candidato1 = new Candidato(44, cargo1, partido1);
        candidato2 = new Candidato(44, cargo2, partido2);
        candidato3 = new Candidato(44, cargo2, partido3);
    }

    @Test
    void testAtribuiVotosTodosBranco() {
        Voto primeiroVoto = new Voto();
        primeiroVoto.setCandidato(null);
        primeiroVoto.setTipoVoto(TipoVoto.BRANCO);

        primeiroVoto.setEleicao(eleicao);

        Voto segundoVoto = new Voto();
        segundoVoto.setCandidato(null);
        segundoVoto.setTipoVoto(TipoVoto.BRANCO);

        segundoVoto.setEleicao(eleicao);

        Voto terceiroVoto = new Voto();
        terceiroVoto.setCandidato(null);
        terceiroVoto.setTipoVoto(TipoVoto.BRANCO);

        terceiroVoto.setEleicao(eleicao);

        when(daoVoto.saveAll(Arrays.asList(any(Voto.class)))).thenReturn(Arrays.asList(primeiroVoto, segundoVoto, terceiroVoto));

        this.votoService.atribuiVotos(eleicao, candidato2, candidato3, candidato1, true, true, true);

        verify(this.daoVoto, times(1)).saveAll(anyList());
    }

    @Test
    void testAtribuiVotosTodosValidos() {
        Voto primeiroVoto = new Voto();
        primeiroVoto.setCandidato(candidato2);
        primeiroVoto.setTipoVoto(TipoVoto.VALIDO);

        primeiroVoto.setEleicao(eleicao);

        Voto segundoVoto = new Voto();
        segundoVoto.setCandidato(candidato3);
        segundoVoto.setTipoVoto(TipoVoto.VALIDO);

        segundoVoto.setEleicao(eleicao);

        Voto terceiroVoto = new Voto();
        terceiroVoto.setCandidato(candidato1);
        terceiroVoto.setTipoVoto(TipoVoto.VALIDO);

        terceiroVoto.setEleicao(eleicao);

        when(this.daoCandidato.findByNumero(anyInt())).thenReturn(candidato2);
        when(daoVoto.saveAll(Arrays.asList(any(Voto.class)))).thenReturn(Arrays.asList(primeiroVoto, segundoVoto, terceiroVoto));

        this.votoService.atribuiVotos(eleicao, candidato2, candidato3, candidato1, false, false, false);

        verify(this.daoVoto, times(1)).saveAll(anyList());
        verify(this.daoCandidato, times(3)).findByNumero(anyInt());
    }
    
}

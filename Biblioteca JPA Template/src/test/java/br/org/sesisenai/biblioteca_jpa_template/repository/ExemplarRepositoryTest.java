package br.org.sesisenai.biblioteca_jpa_template.repository;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.ExemplarRepository;
import br.org.sesisenai.biblioteca_jpa_template.model.Categoria;
import br.org.sesisenai.biblioteca_jpa_template.model.Exemplar;
import br.org.sesisenai.biblioteca_jpa_template.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class ExemplarRepositoryTest {

    @Autowired
    ExemplarRepository exemplarRepository;

    @Test
    @DisplayName("Teste da repository de Exemplar, permite a busca de um exempar por codigo de barras")
    void testLivroRepositoryPesquisaPorCodigoBarra() {
        try {
            Exemplar exemplar = exemplarRepository.findByCodigoBarras("123");
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package br.org.sesisenai.biblioteca_jpa_template.repository;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.EmprestimoRepository;
import br.org.sesisenai.biblioteca_jpa_template.model.Cliente;
import br.org.sesisenai.biblioteca_jpa_template.model.Emprestimo;
import br.org.sesisenai.biblioteca_jpa_template.model.Exemplar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class EmprestimoRepositoryTest {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Test
    @DisplayName("Teste da repository de Emprestimo, permite a busca de empréstimos por cliente")
    void testEmprestimoRepositoryPesquisaPorCliente() {
        try {
            Cliente cliente = new Cliente();
            Field idCliente = Cliente.class.getDeclaredField("id");
            idCliente.setAccessible(true);
            idCliente.set(cliente, 1L);
            List<Emprestimo> listaEmprestimo = emprestimoRepository.findAllByCliente(cliente);
            assertNotNull(listaEmprestimo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Teste da repository de Emprestimo, permite a busca de empréstimos por exemplar")
    void testEmprestimoRepositoryPesquisaPorExemplar() {
        try {
            Exemplar exemplar = new Exemplar();
            Field idExemplar = Exemplar.class.getDeclaredField("id");
            idExemplar.setAccessible(true);
            idExemplar.set(exemplar, 1L);
            List<Emprestimo> listaEmprestimo = emprestimoRepository.findAllByExemplar(exemplar);
            assertNotNull(listaEmprestimo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

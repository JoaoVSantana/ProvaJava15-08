package br.org.sesisenai.biblioteca_jpa_template.repository;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.*;
import br.org.sesisenai.biblioteca_jpa_template.model.Editora;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class RepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    BibliotecarioRepository bibliotecarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EditoraRepository editoraRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    ExemplarRepository exemplarRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    void testAutorRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(autorRepository);
    }

    @Test
    void testBibliotecarioRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(bibliotecarioRepository);
    }

    @Test
    void testCategoriaRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(categoriaRepository);
    }

    @Test
    void testClienteRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(clienteRepository);
    }

    @Test
    void testEditoraRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(editoraRepository);
    }

    @Test
    void testEmprestimoRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(emprestimoRepository);
    }

    @Test
    void testExemplarRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(exemplarRepository);
    }

    @Test
    void testLivroRepositoryNaoPodeSerNuloDeveSerinjetavel() {
        assertNotNull(livroRepository);
    }


}

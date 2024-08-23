package br.org.sesisenai.biblioteca_jpa_template.repository;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.AutorRepository;
import br.org.sesisenai.biblioteca_jpa_template.Repositories.EmprestimoRepository;
import br.org.sesisenai.biblioteca_jpa_template.Repositories.LivroRepository;
import br.org.sesisenai.biblioteca_jpa_template.model.*;
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
public class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Test
    @DisplayName("Teste da repository de Livro, permite a busca de livros por autor")
    void testLivroRepositoryPesquisaPorAutor() {
        try {
            Autor autor = new Autor();
            Field idAutor = Autor.class.getDeclaredField("id");
            idAutor.setAccessible(true);
            idAutor.set(autor, 1L);
            List<Livro> listaLivros = livroRepository.findAllByAutor(autor);
            assertNotNull(listaLivros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Teste da repository de Livro, permite a busca de livros por categoria")
    void testLivroRepositoryPesquisaPorCategoria() {
        try {
            Categoria categoria = new Categoria();
            Field idCategoria = Categoria.class.getDeclaredField("id");
            idCategoria.setAccessible(true);
            idCategoria.set(categoria, 1L);
            List<Livro> listaLivros = livroRepository.findAllByCategoria(categoria);
            assertNotNull(listaLivros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

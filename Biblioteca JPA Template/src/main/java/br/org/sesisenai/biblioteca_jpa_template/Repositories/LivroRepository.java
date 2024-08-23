package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Autor;
import br.org.sesisenai.biblioteca_jpa_template.model.Categoria;
import br.org.sesisenai.biblioteca_jpa_template.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findAllByAutor(Autor autor);
    List<Livro> findAllByCategoria(Categoria categoria);
}

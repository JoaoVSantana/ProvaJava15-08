package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Bibliotecario;
import br.org.sesisenai.biblioteca_jpa_template.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}

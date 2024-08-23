package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
}

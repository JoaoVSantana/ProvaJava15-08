package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

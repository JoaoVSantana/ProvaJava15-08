package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Exemplar findByCodigoBarras(String codigo);
}

package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

package br.org.sesisenai.biblioteca_jpa_template.Repositories;

import br.org.sesisenai.biblioteca_jpa_template.model.Categoria;
import br.org.sesisenai.biblioteca_jpa_template.model.Cliente;
import br.org.sesisenai.biblioteca_jpa_template.model.Emprestimo;
import br.org.sesisenai.biblioteca_jpa_template.model.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAllByCliente(Cliente cliente);
    List<Emprestimo> findAllByExemplar(Exemplar exemplar);
}

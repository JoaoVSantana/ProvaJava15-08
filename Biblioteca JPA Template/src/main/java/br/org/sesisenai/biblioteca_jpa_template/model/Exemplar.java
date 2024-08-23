package br.org.sesisenai.biblioteca_jpa_template.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String codigoBarras;
    private boolean disponivel;
    @ManyToOne
    private Livro livro;
    @OneToMany(mappedBy = "exemplar")
    private List<Emprestimo> emprestimos;
}

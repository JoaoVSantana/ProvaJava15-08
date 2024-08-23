package br.org.sesisenai.biblioteca_jpa_template.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate dataEmprestimo;
    @Column(nullable = false)
    private LocalDate dataDevolucao;
    @Column(length = 20)
    private String status;
    @ManyToOne
    private Exemplar exemplar;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Bibliotecario bibliotecario;

}

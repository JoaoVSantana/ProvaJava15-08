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
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;
    private Integer anoPublicacao;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editora editora;
    @OneToMany(mappedBy = "livro")
    private List<Exemplar> exemplares;
}

package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "resultado", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Autores> autor;
    @OneToMany(mappedBy = "resultado", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Idioma> idioma;
    private Double numeroDeDescargas;

    public Resultado(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autores> getAutor() {
        return autor;
    }

    public void setAutor(List<Autores> autor) {
        this.autor = autor;
    }

    public List<Idioma> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<Idioma> idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Resultado(String titulo, List<Autores> autor, List<Idioma> idioma, Double numeroDeDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        String nombreDeLosAutores = autor.stream()
                .map(a ->a.getNombre())
                .collect(Collectors.joining(", "));

        String idiomaUnido = idioma.stream()
                .map(i ->i.getIdioma())
                .collect(Collectors.joining(", "));

        return """
                *****LIBRO*****
                titulo: %s
                autor: %s
                idioma: %s
                numeroDeDescargas: %s
                ***************
                """.formatted(titulo, nombreDeLosAutores, idiomaUnido, numeroDeDescargas);
    }
}

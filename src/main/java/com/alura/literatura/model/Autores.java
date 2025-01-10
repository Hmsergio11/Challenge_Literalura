package com.alura.literatura.model;

import jakarta.persistence.*;


@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer añoDeNacimiento;
    private Integer añoDeMuerte;
    @ManyToOne
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;

    public Autores() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(Integer añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public Integer getAñoDeMuerte() {
        return añoDeMuerte;
    }

    public void setAñoDeMuerte(Integer añoDeMuerte) {
        this.añoDeMuerte = añoDeMuerte;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
}
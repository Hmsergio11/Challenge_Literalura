package com.alura.literatura.model;

import java.util.List;

public class Busqueda {
    private int count;
    private List<Resultado> resultado;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Resultado> getResultado() {
        return resultado;
    }

    public void setResultado(List<Resultado> resultado) {
        this.resultado = resultado;
    }
}

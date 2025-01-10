package com.alura.literatura.repository;

import com.alura.literatura.model.Autores;
import com.alura.literatura.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ResultadoRepository extends JpaRepository<Resultado,Long> {
    @Query("SELECT DISTINCT a FROM Autores a JOIN FETCH a.resultado")
    List<Autores> findAutoresConLIbros();

    Resultado findByTitulo(String titulo);

    @Query("SELECT a FROM Autores a WHERE a.añoDeNacimiento <= :year AND (a.añoDeMuerte IS NULL OR a.añoDeMuerte >= :year)")
    List<Autores> findAutoresVivosEnAño(@Param("year") int year);

    @Query("SELECT r FROM Resultado r JOIN r.idioma i WHERE i.idioma = :idioma")
    List<Resultado> findLibrosPorIdioma(@Param("idioma") String idioma);

}

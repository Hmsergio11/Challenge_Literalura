package com.alura.literatura.principal;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.ResultadoRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Principal {


   private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private final String URL_SEARCH = "/?search=";
    private ResultadoRepository repositorio;
    public Principal(ResultadoRepository repository) {
        this.repositorio = repository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 6) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Lista de libros registrados
                    3 - Lista de autores registrados
                    4 - Lista de autores vivos en un determinado año
                    5 - Lista de libros por idioma
                    6 - Salir
                    
                    """;
            System.out.println(menu);
                try {
                    opcion = teclado.nextInt();
                    teclado.nextLine();

                    switch (opcion) {
                        case 1:
                            buscarLibroPorTitulo();
                            break;
                        case 2:
                            listaDeLibrosRegistrados();
                            break;
                        case 3:
                            listaDeAutoresRegistrados();
                            break;
                        case 4:
                            listaDeAutoresVivosEnUnDeterminadoAño();
                            break;
                        case 5:
                            listaDeLibrosPorIdioma();
                            break;
                        case 6:
                            System.out.println("Cerrando la aplicación");
                            break;
                        default:
                            System.out.println("Opcion invalida");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, ingrese un número válido.");
                    teclado.nextLine();
                }
            }
        }

        private void buscarLibroPorTitulo () {
            System.out.println("Escribe el nombre del libro que deseas buscar");
            var nombreDelLibro = teclado.nextLine();
            var json = consumoAPI.obtenerDatos(URL_BASE + URL_SEARCH + nombreDelLibro.replace(" ", "%20"));
//        System.out.println(json);
            ConvierteDatos conversor = new ConvierteDatos();
            List<DatosBusqueda> busqueda = new ArrayList<>();
            var datos = conversor.obtenerDatos(json, DatosBusqueda.class);
            busqueda.add(datos);

            List<Resultado> resultado = busqueda.stream()
                    .flatMap(b -> b.resultados().stream())
                    .map(r -> {

                        List<Autores> autores = r.autores().stream()
                                .map(a -> {
                                    Autores nuevoAutor = new Autores();
                                    nuevoAutor.setNombre(a.nombre());
                                    nuevoAutor.setAñoDeNacimiento(a.añoDeNacimiento());
                                    nuevoAutor.setAñoDeMuerte(a.añoDeMuerte());
                                    return nuevoAutor;
                                })
                                .collect(Collectors.toList());

                        List<Idioma> idiomas = r.idioma().stream()
                                .map(i -> {
                                    Idioma idiomaLimpio = new Idioma();
                                    idiomaLimpio.setIdioma(i);
                                    return idiomaLimpio;
                                })
                                .collect(Collectors.toList());

                        Resultado libro = new Resultado(r.titulo(), autores, idiomas, r.numeroDeDescargas());
                        autores.forEach(autor -> autor.setResultado(libro));
                        idiomas.forEach(idioma -> idioma.setResultado(libro));
                        return libro;

                    })

                    .limit(1)
                    .collect(Collectors.toList());

            resultado.forEach(r -> {
                try {
                    Resultado guardado = repositorio.save(r);
                    System.out.println(guardado);
                } catch (DataIntegrityViolationException e) {
                    System.out.println("El libro ya esta registrado en la base de datos.");
                    Resultado libroExistente = repositorio.findByTitulo(r.getTitulo());
                    if (libroExistente != null) {
                        System.out.println(libroExistente);
                    } else {
                        System.out.println("No se puede obtener la informacion del libro existente");
                    }
                }

            });

        }

        private void listaDeLibrosRegistrados () {
            List<Resultado> resultados = repositorio.findAll();
            resultados.stream()
                    .forEach(System.out::println);
        }

        private void listaDeAutoresRegistrados () {
            List<Autores> autores = repositorio.findAutoresConLIbros();
            autores.stream().distinct().collect(Collectors.toList());

            autores.forEach(autor -> {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getAñoDeNacimiento());
                System.out.println("Fecha de fallecimineto: " + autor.getAñoDeMuerte());

                System.out.println("Libros:");
                System.out.println(" - " + autor.getResultado().getTitulo());
                System.out.println();

            });

        }

        private void listaDeAutoresVivosEnUnDeterminadoAño () {
            System.out.println("Escribe el año para buscar autores vivos:");
            try {
                int año = teclado.nextInt();
                List<Autores> autoresVivos = repositorio.findAutoresVivosEnAño(año);

                if (autoresVivos.isEmpty()) {
                    System.out.println("No se encontraron autores vivos en ese año");
                } else {
                    autoresVivos.forEach(autores -> {
                        System.out.println("Autor: " + autores.getNombre());
                        System.out.println("Fecha de nacimiento: " + autores.getAñoDeNacimiento());
                        System.out.println("Fecha de fallecimineto: " + autores.getAñoDeMuerte());
                        System.out.println();
                    });
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un año válido.");
                teclado.nextLine();
                }
        }

        private void listaDeLibrosPorIdioma () {
            System.out.println("Escribe el idioma para buscar los libros:");
            System.out.println("""
                    es - español
                    en - ingles
                    fr - frances
                    pt - portuges
                    """);
            String idioma = teclado.nextLine().trim();
            List<Resultado> libros = repositorio.findLibrosPorIdioma(idioma);

            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros en ese idioma.");
            } else {
                libros.forEach(libro -> {
                    System.out.println("Titulo: " + libro.getTitulo());
                    System.out.println("Idioma: " + libro.getIdioma().stream()
                            .map(i -> i.getIdioma())
                            .collect(Collectors.joining(", ")));
                    System.out.println("Numero de descargas: " + libro.getNumeroDeDescargas());
                    System.out.println("Autores: ");
                    libro.getAutor().forEach(autores -> System.out.println(" - " + autores.getNombre()));
                    System.out.println();
                });
            }
        }
}

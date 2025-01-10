# Challenge_Literalura

Este repositorio contiene un sistema de gestión de literatura que permite interactuar con una API de libros, almacenar resultados en una base de datos y realizar consultas avanzadas sobre libros y autores.

## Características

El programa ofrece las siguientes funcionalidades:

1. **Buscar libro por título**: Realiza una búsqueda en la API de Gutenberg y registra el libro en la base de datos si no está previamente almacenado.
2. **Lista de libros registrados**: Muestra todos los libros almacenados en la base de datos.
3. **Lista de autores registrados**: Muestra los autores asociados con libros registrados, incluyendo su fecha de nacimiento y, si aplica, fecha de fallecimiento.
4. **Lista de autores vivos en un año determinado**: Consulta a los autores que estuvieron vivos en un año especificado.
5. **Lista de libros por idioma**: Filtra libros registrados según su idioma.

## Estructura del Proyecto

- **`com.alura.literatura.model`**:
  - `Resultado`: Representa un libro con sus atributos (título, autores, idioma, descargas).
  - `Autores`: Representa un autor, incluyendo su nombre y datos biográficos.
  - `Idioma`: Representa el idioma de un libro.
- **`com.alura.literatura.service`**:
  - `ConsumoAPI`: Clase para realizar las consultas a la API externa.
  - `ConvierteDatos`: Clase para transformar datos de JSON a objetos del modelo.
- **`com.alura.literatura.repository`**:
  - `ResultadoRepository`: Repositorio para interactuar con la base de datos.
- **`com.alura.literatura.principal`**:
  - `Principal`: Contiene el menú principal y la lógica de interacción con el usuario.

## Uso
- Al iniciar, se presenta un menú con las opciones disponibles.
- Ingresa el número de la opción deseada y sigue las instrucciones en pantalla.
- La información obtenida de la API se almacena automáticamente en la base de datos para futuras consultas.

## Ejemplo de Consulta
Buscar libro por título
- Ingresa 1 en el menú y proporciona el título del libro.
- Si el libro está disponible en la API, se registrará en la base de datos con sus autores e idiomas.

Lista de autores vivos en un año
- Ingresa 4 en el menú y proporciona un año.
- El sistema mostrará los autores que estuvieron vivos durante ese año.



 

## Dependencias

Este proyecto utiliza:

- **Spring Boot**: Para la gestión de repositorios y conexión a la base de datos.
- **Jakarta Persistence API (JPA)**: Para el mapeo objeto-relacional.
- **Hibernate**: Implementación de JPA.
- **API Gutenberg**: Fuente externa de datos sobre libros.


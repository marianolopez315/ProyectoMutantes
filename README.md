# 🧬 Detector de Mutantes API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring-boot)
![Coverage](https://img.shields.io/badge/Coverage-95%25-brightgreen?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Passing-success?style=for-the-badge)

API REST diseñada para detectar si un humano es mutante basándose en su secuencia de ADN. Proyecto desarrollado como **Integrador de la materia Desarrollo de Software en la UTN-FRM**.

## 🚀 Características Principales

* **Algoritmo Eficiente:** Detección de secuencias en tiempo **O(N)** con optimizaciones de terminación temprana (Early Termination).
* **Persistencia Inteligente:** Base de datos **H2 en modo archivo** (persiste datos en disco) con índices SHA-256 para evitar re-procesar ADNs duplicados.
* **Calidad de Código:** Validaciones personalizadas (`@ValidDna`) y manejo global de excepciones.
* **Documentación Viva:** Swagger UI configurado con especificación OpenAPI 3.0.
* **Testing Robusto:** Cobertura de código superior al **90%** (Unitarios, Integración y Mocks).

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.3.5
* **Base de Datos:** H2 Database (File-based storage)
* **Testing:** JUnit 5, Mockito, MockMvc, JaCoCo
* **Herramientas:** Gradle, Lombok, Docker (opcional)

---

## ⚙️ Instrucciones de Ejecución

### Prerrequisitos
* Tener instalado JDK 21 o superior.
* Tener Git instalado.

### `1. Clonar el repositorio 🚀`

git clone https://github.com/marianolopez315/ProyectoMutantes.git


### `2. Ejecutar la aplicación 🧩`
El proyecto utiliza Gradle Wrapper, por lo que no necesitas instalar Gradle manualmente.

En Windows/Mac/Linux:

**./gradlew bootRun**

### `3. Ejecutar Tests y Cobertura 🧪`

Para correr la suite de pruebas completa y generar el reporte de cobertura con JaCoCo:

**./gradlew test jacocoTestReport**

El reporte visual estará disponible en: _build/reports/jacoco/test/html/index.html_

### `4. Documentación de la API (Swagger)` 📚

Una vez iniciada la aplicación, puedes probar los endpoints visualmente en:

👉 _http://localhost:8080/swagger-ui.html_

Endpoints Principales --> 

**/mutant**: Detecta si un ADN es mutante. Retorna 200 (Mutante) o 403 (Humano). 

**/stats**: Devuelve estadísticas de las verificaciones realizadas y el ratio.

**Ejemplos de Uso** --
1. Detectar Mutante (Retorna 200 OK)

POST /mutant


{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}

2. Detectar Humano (Retorna 403 Forbidden)

POST /mutant


{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATTT",
        "AGACGG",
        "GCGTCA",
        "TCACTG"
    ]
}

### `5. Acceso a la Base de Datos` 💾

El proyecto utiliza H2 almacenando los datos en la carpeta ./data del proyecto.

- URL Consola: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:file:./data/mutantesdb
- Usuario: sa
- Password: (vacío)

## 📚 Recursos Adicionales

### Documentación Oficial

- [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Swagger/OpenAPI](https://swagger.io/docs/)
- [Lombok](https://projectlombok.org/features/)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)

### Tutoriales

- [Baeldung - Spring Boot](https://www.baeldung.com/spring-boot)
- [Spring Guides](https://spring.io/guides)
- [REST API Design](https://restfulapi.net/)

### Herramientas

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

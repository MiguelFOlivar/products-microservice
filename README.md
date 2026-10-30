# 🛍️ Products Microservice

![Banner del proyecto](./assets/presentation-banner.svg)

[![Java](https://img.shields.io/badge/Java-25-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.2-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JPA](https://img.shields.io/badge/Persistence-JPA-59666C)](https://jakarta.ee/specifications/persistence/)
[![Estado](https://img.shields.io/badge/Estado-En%20progreso-orange)](#-roadmap)

Microservicio backend para gestión de productos, desarrollado como **portafolio profesional** con arquitectura escalable, buenas prácticas y documentación clara para facilitar su adopción.

---

## 🎯 Objetivo del proyecto

Construir una base sólida para un ecosistema de microservicios enfocado en e-commerce, iniciando con el dominio de productos.

### Enfoque técnico
- Java 25 + Spring Boot
- Persistencia con Spring Data JPA y MySQL
- Validaciones de entrada con Jakarta Validation
- Preparación para seguridad JWT y crecimiento por módulos

---

## 📌 Estado actual

Actualmente este repositorio incluye:

- Entidades de dominio: `Product`, `Category`, `CategoryType`
- DTOs para categorías y productos
- Repositorios JPA para productos y categorías
- Seeder de categorías iniciales en arranque (`DataLoaderCategory`)
- Pruebas unitarias para DTOs, entidad de categorías y seeding

> ⚠️ La capa de API REST y la seguridad JWT completa siguen en construcción.

---

## 🧱 Stack principal

- **Java 25**
- **Spring Boot 4.1.2**
- **Spring Data JPA**
- **Jakarta Validation**
- **MySQL**
- **Lombok**
- **Maven Wrapper (`./mvnw`)**

---

## 🚀 Ejecución local (rápida y amigable)

### 1) Requisitos

- JDK 25
- MySQL 8+
- Git

### 2) Clonar el repositorio

```bash
git clone https://github.com/MiguelFOlivar/products-microservice.git
cd products-microservice
```

### 3) Configurar base de datos

Crea una base de datos en MySQL (por defecto: `db_products`) y define variables de entorno:

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=db_products
export DB_USER=root
export DB_PASSWORD=tu_password
```

> Si no defines `DB_USER` o `DB_PASSWORD` en desarrollo, el proyecto usa valores por defecto desde `application.properties`.

### 4) Ejecutar el proyecto

```bash
./mvnw spring-boot:run
```

Perfil activo por defecto: `dev`.

---

## ⚙️ Perfiles de configuración

- `application.properties`: configuración base y variables dinámicas de conexión.
- `application-dev.properties`: ajustes de desarrollo (puerto `8081`, SQL visible, `ddl-auto=update`).
- `application-prod.properties`: ajustes estrictos para producción (puerto `8080`, `ddl-auto=none`, sin SQL en consola).

---

## 🧪 Pruebas

Ejecuta las pruebas con:

```bash
./mvnw test
```

Cobertura actual orientada a:
- Validaciones de DTOs de categoría
- Mapeo `Category -> CategoryResponse`
- Persistencia de enum como `STRING` en `Category`
- Lógica de seeding de categorías faltantes

---

## 📁 Estructura del proyecto

```text
src/
├─ main/
│  ├─ java/org/dev/app/products/
│  │  ├─ config/
│  │  ├─ dto/
│  │  ├─ entity/
│  │  └─ repository/
│  └─ resources/
└─ test/
   └─ java/org/dev/app/products/
```

---

## 🗂️ Roadmap

- [x] Definir visión y base del proyecto
- [x] Modelar entidades y categorías iniciales
- [x] Añadir DTOs y pruebas unitarias base
- [ ] Implementar API REST de productos/categorías
- [ ] Integrar seguridad JWT end-to-end
- [ ] Añadir capa de servicio y manejo centralizado de errores
- [ ] Documentar endpoints (OpenAPI/Swagger)

---

## 🤝 Enfoque de portafolio

Este repositorio está diseñado para demostrar:

- Diseño backend mantenible
- Evolución incremental con buenas prácticas
- Documentación clara para reclutadores, colaboradores y usuarios finales

---

## 📝 Nota de mantenimiento

Este README se irá actualizando en cada avance importante para mantener una guía útil y alineada al estado real del proyecto.

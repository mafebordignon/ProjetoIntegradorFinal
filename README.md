# keepInventory

Projeto Integrador do Grupo 6 de 2024 - Biopark Educação

## Descrição

Este projeto é uma aplicação Java baseada em Spring Boot, que utiliza Thymeleaf para renderização de páginas, JPA para acesso a dados e Apache POI para manipulação de arquivos do Microsoft Excel. O objetivo é manter um inventário de produtos.

## Pré-requisitos

Antes de executar o projeto, certifique-se de que você tem os seguintes requisitos instalados:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) (se não usar Docker)
- [Maven](https://maven.apache.org/download.cgi) (se não usar Docker)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/) (incluso no Docker Desktop)

### Com Docker

O Docker Compose pode ser usado para configurar tanto o banco de dados MySQL quanto a aplicação. 


Clone o repositório para a sua máquina local:

```bash
git clone https://github.com/seu_usuario/keepInventory.git
cd keepInventory

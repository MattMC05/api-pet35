# api-pet35
Projeto_Final_API_2026.01

* ===================================================================
 * Projeto: [Projeto Final API - Desenvolvimento de uma API para gerir uma Oficina Mecânica]
 * Autor: [Leandro Coutinho, Marcelo Oliveira, Matheus Mendonça, Rodolpho Almeida]
 * Data: 22/05/2026
 * Projeto desenvolvido para avaliação final da disciplina de Desenvolvimento de APIs.
 * Professor: Roni Schanuel
 * ===================================================================

# 🛠️ API Oficina Mecânica - Projeto Final

## 📖 Sobre o Projeto
Este projeto consiste numa API RESTful desenvolvida em **Java com Spring Boot** para informatizar e gerir os processos de uma oficina mecânica. O sistema permite a gestão de clientes, veículos, serviços e ordens de serviço (OS), aplicando boas práticas de arquitetura em camadas e segurança.

## 🚀 Tecnologias Utilizadas
* **Java 17+**
* **Spring Boot** (Web, Data JPA, Security, Validation, Mail)
* **Base de Dados:** H2 Database (configurável no `application.properties`)
* **Autenticação:** JWT (JSON Web Token) com BCrypt
* **Integração Externa:** API ViaCEP
* **Documentação:** Swagger (SpringDoc OpenAPI)
* **Arquitetura:** Padrão em camadas (Entity, Service, Repository, Controller, DTO)

## ⚙️ Funcionalidades Principais

### 👥 Clientes
* CRUD completo (Criar, Ler, Atualizar, Eliminar).
* **Validações:** Nome, telefone, e-mail e CPF obrigatórios e formatados.
* **Integração ViaCEP:** Preenchimento automático da morada/endereço através da consulta do CEP na API externa ViaCEP.
* **Notificações:** Envio automático de e-mail ao inserir ou alterar os dados de um cliente.

### 🚗 Veículos
* CRUD completo de veículos (matrícula/placa, marca, modelo, ano, cor).
* Vínculo obrigatório a um Cliente (proprietário).
* Ao listar os veículos, a API exibe os dados do respetivo proprietário.

### 🔧 Serviços
* Gestão do catálogo de serviços oferecidos pela oficina (ex: troca de óleo, alinhamento, revisão).
* Definição de descrição, valor e tempo estimado para cada serviço.

### 📋 Ordens de Serviço (OS)
* Criação e gestão de ordens de serviço vinculadas a um Cliente e a um Veículo.
* **Controlo de Status:** Pendente, Em Andamento, Concluída, Cancelada.
* Cálculo automático do valor total da OS, baseado na soma dos serviços realizados.

### 🔒 Segurança e Autenticação
* Proteção de rotas utilizando **Spring Security**.
* Autenticação via token **JWT** (necessário passar no cabeçalho `Authorization: Bearer <token>`).
* Criptografia de palavras-passe com **BCryptPasswordEncoder**.

### ⚠️ Tratamento de Exceções
Tratamento global e centralizado através de `@ControllerAdvice` para devolução de respostas amigáveis nos seguintes cenários:
* Registo não encontrado (404 Not Found)
* Dados inválidos / Erro de validação (400 Bad Request)
* Erro de autenticação ou token inválido (401 Unauthorized / 403 Forbidden)
* Erro de base de dados (500 Internal Server Error ou 409 Conflict)
* Enum inválido ou CEP com formato inválido (400 Bad Request)

## 👥 Colaboradores

Agradecemos às seguintes pessoas que contribuíram para este projeto:

| Foto | Usuário | Função (Exemplo) |
| :---: | :--- | :--- |
| <img src="https://github.com/leandrotcdev.png" width="60px;"/> | [**Leandro**](https://github.com/leandrotcdev) | Fullstack Developer |
| <img src="https://github.com/MarceloMdx.png" width="60px;"/> | [**Marcelo**](https://github.com/MarceloMdx) | Fullstack Developer |
| <img src="https://github.com/MattMC05.png" width="60px;"/> | [**Matheus**](https://github.com/MattMC05) | Fullstack Developerr |
| <img src="https://github.com/RodolphoAlmeida-FS.png" width="60px;"/> | [**Rodolpho**](https://github.com/RodolphoAlmeida-FS) | Fullstack Developer |

---


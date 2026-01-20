# 🏗️ Desafio Fullstack Integrado
🚨 Instrução Importante (LEIA ANTES DE COMEÇAR)
❌ NÃO faça fork deste repositório.

Este repositório é fornecido como modelo/base. Para realizar o desafio, você deve:
✅ Opção correta (obrigatória)
  Clique em “Use this template” (se este repositório estiver marcado como Template)
OU
  Clone este repositório e crie um NOVO repositório público em sua conta GitHub.
📌 O resultado deve ser um repositório próprio, independente deste.

## 🎯 Objetivo
Criar solução completa em camadas (DB, EJB, Backend, Frontend), corrigindo bug em EJB e entregando aplicação funcional.

## 📦 Estrutura
- db/: scripts schema e seed
- ejb-module/: serviço EJB com bug a ser corrigido
- backend-module/: backend Spring Boot
- frontend/: app Angular
- docs/: instruções e critérios
- .github/workflows/: CI

## ✅ Tarefas do candidato
1. Executar db/schema.sql e db/seed.sql
2. Corrigir bug no BeneficioEjbService
3. Implementar backend CRUD + integração com EJB
4. Desenvolver frontend Angular consumindo backend
5. Implementar testes
6. Documentar (Swagger, README)
7. Submeter via fork + PR

## 🐞 Bug no EJB
- Transferência não verifica saldo, não usa locking, pode gerar inconsistência
- Espera-se correção com validações, rollback, locking/optimistic locking

## 📊 Critérios de avaliação
- Arquitetura em camadas (20%)
- Correção EJB (20%)
- CRUD + Transferência (15%)
- Qualidade de código (10%)
- Testes (15%)
- Documentação (10%)
- Frontend (10%)


# API de Gestão de Benefícios

Este projeto consiste em um ecossistema completo para gestão e transferência de benefícios, utilizando uma arquitetura distribuída que integra tecnologias corporativas e frameworks modernos para garantir escalabilidade e integridade de dados.

---

## 1. Tecnologias Utilizadas

* **Core Business:** Java 17, Jakarta EE (EJB 3.x), JPA/Hibernate.
* **API / BFF:** Spring Boot 3.x, SpringDoc OpenAPI (Swagger).
* **Frontend:** Angular 16+.
* **Servidor de Aplicação:** WildFly 27+.
* **Banco de Dados:** H2 Database (em memória para desenvolvimento).
* **Testes:** JUnit 5, Mockito.

---

## 2. Arquitetura do Sistema

A solução adota um modelo de **Arquitetura Híbrida**, separando a lógica de negócio central da camada de exposição para o usuário final:



* **Módulo EJB (Core):** Hospedado no servidor WildFly, é responsável pelo controle transacional e persistência. Utiliza **Locking Otimista (@Version)** para prevenir conflitos de concorrência em atualizações de saldo.
* **Módulo Spring Boot (BFF):** Atua como um *Backend For Frontend*, realizando chamadas remotas para o EJB e expondo uma interface REST documentada.
* **Módulo Angular:** Interface SPA (Single Page Application) que permite a visualização em tempo real dos benefícios e a execução de operações financeiras.

---

## 3. Decisões Técnicas e Diferenciais

* **Desacoplamento:** A separação física e lógica entre o motor de regras (EJB) e a API (Spring) permite manutenções isoladas e maior robustez.
* **Documentação Interativa:** Implementação do **Swagger UI**, fornecendo uma interface visual para exploração e teste imediato dos endpoints.
* **Segurança de Negócio:** O processo de transferência executa validações triplas (existência de conta, status ativo e saldo disponível) antes de efetivar o commit no banco de dados.
* **Interoperabilidade:** Configuração estratégica de CORS no Spring Boot para garantir a comunicação segura entre o ambiente Angular e a API REST.



---

## 4. Como Executar (Quick Start)

Para inicializar o projeto localmente, siga a ordem de dependência abaixo:

### 1. Módulo EJB (WildFly)
* Inicie o servidor WildFly.
* Realize o deploy do artefato `.ear`.
* O banco de dados será automaticamente populado pelo script `import.sql` (configurado com IDs e versões iniciais).

### 2. Módulo API (Spring Boot)
* Acesse o diretório do módulo backend.
* Execute o comando: `mvn spring-boot:run`.
* A API estará disponível na porta `8081`.
* **URL Swagger:** `http://localhost:8081/swagger-ui/index.html`

### 3. Módulo Web (Angular)
* Acesse o diretório do frontend.
* Execute `npm install` e, em seguida, `ng serve`.
* Acesse o sistema via browser em: `http://localhost:4200`.
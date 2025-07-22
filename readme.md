# LumiCash: Seu Gerenciador Financeiro Pessoal

O **LumiCash** é uma aplicação web intuitiva e eficiente, desenvolvida para auxiliar usuários no gerenciamento de suas finanças pessoais. Nosso objetivo é proporcionar **clareza e controle** sobre receitas, despesas e investimentos, facilitando a tomada de decisões financeiras.

---

## 🚀 Funcionalidades da Primeira Entrega

Esta primeira versão do LumiCash foca nas funcionalidades essenciais de **administração de usuários e gerenciamento básico de contas e transações**.

### Casos de Uso Implementados

- **UC21 – Administrador cadastra correntista:** permite ao administrador criar novos perfis de correntistas.
- **UC20 – Administrador acessa listagem de correntistas:** fornece ao administrador uma visão geral de todos os correntistas cadastrados.
- **UC01 – Correntista cadastra conta:** correntista cria e organiza suas contas financeiras (ex: conta corrente, cartão de crédito).
- **UC02 – Correntista acessa contas:** permite visualizar todas as contas cadastradas.
- **UC03 – Correntista cria transação para conta:** registra novas transações (débitos e créditos).
- **UC04 – Correntista edita transação existente:** possibilita modificar transações previamente registradas.
- **UC05 – Correntista adiciona comentário à transação:** permite adicionar notas ou detalhes às transações.
- **UC06 – Correntista edita/exclui comentário:** oferece controle sobre comentários anexados às transações.
- **RNF 07 – Padrão P-R-G (Post-Redirect-Get):** implementado para evitar reenvio de formulários ao atualizar a página.

---

## 💻 Tecnologias Utilizadas

- **Java (JDK):** 17
- **Spring Boot:** 3.4.4
  - spring-boot-starter-data-jpa
  - spring-boot-starter-thymeleaf
  - spring-boot-starter-validation
  - spring-boot-starter-web
  - spring-boot-devtools
  - spring-boot-starter-test
- **PostgreSQL Driver:** 42.7.3
- **Lombok:** 1.18.26
- **Thymeleaf Extras Java 8 Time:** 3.0.4.RELEASE

---

## 🧩 Entidades do Projeto

O modelo de dados do LumiCash é composto pelas seguintes entidades:

- **Categoria:** classifica transações (ex: "Salário", "Saúde", "Aporte Investimento"), com nome, natureza (Entrada, Saída, Investimento) e ordem.
- **Comentario:** permite adicionar notas às transações.
- **Conta:** representa contas financeiras do correntista, com número, descrição e tipo.
- **Correntista:** representa o usuário, com nome, email, senha, status ativo e isAdmin.
- **Transacao:** registra movimentações financeiras, com valor, data, descrição e movimento (Débito/Crédito).

Cada entidade possui seu respectivo *Repository* (ex: `CategoriaRepository`, `CorrentistaRepository`), utilizando **Spring Data JPA** para persistência.

---

## ▶️ Como Executar o Projeto

### 1️⃣ Pré-requisitos

- JDK 17 instalado
- PostgreSQL rodando localmente

---

### 2️⃣ Configuração do Banco de Dados

#### Criação do Banco:

Execute no terminal ou pgAdmin:

```sql
CREATE DATABASE lumicash;
```

#### Configuração do `application.properties`:

No arquivo:

```
src/main/resources/application.properties
```

adicione ou confira, na linha 15:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lumicash
spring.datasource.username=postgres
spring.datasource.password=ifpb
```

---

### 3️⃣ Inserção de Categorias e Administrador Inicial

**Após** a **primeira execução** (onde acontece de fato a criação das tabelas do banco), garanta que o arquivo `application.properties` contenha:

```properties
spring.sql.init.mode=always
```

Ou seja, **descomente essa propriedade para que o arquivo data.sql seja executado**.

---

### 4️⃣ Execução do Projeto

#### Via IDE:

- Execute o método `main` da classe `LumiCashApplication`.

#### Via Terminal:

Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

### 5️⃣ Acesso à Aplicação

Após iniciar, acesse no navegador:

```
http://localhost:8080/lumicash
```

---

## Contribuições

Este projeto está sendo desenvolvido pela equipe **LumiCash**.

 - Cássia Gomes
 - Danillo Coelho
 - Pedro Henrique
 - Jackson Douglas

## Licença

Este projeto está licenciado sob os termos da licença acadêmica do IFPB.

> **LumiCash está pronto para organizar suas finanças pessoais de forma clara, segura e escalável. Luz ao seu dinheiro. Luz às suas finanças. LumiCash.**


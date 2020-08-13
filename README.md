# Projeto Final do Curso de Rest APIs com Spring Boot e Java

## Projeto CRdb - Classificações e Reviews de Disciplinas

Esta API se trata de uma mini aplicação de backend feita com Java e Spring Boot.
A aplicação simula uma pequena rede social, onde pode-se cadastrar usuários e estes podem
se autenticar para assim usar as funcionalidades e interagir e manter informações de disciplinas já cadastradas.
Usuários já autenticados podem dar likes, notas e adicionar comentários, assim alimentando esta pequena rede social.

- obs: Professora e Professor, não pude fazer todas as funcionalidades do projeto, pois durante a semana não tive muito tempo para me dedicar a atividade. Agradeço pelo mini curso, foi de muita ajuda nos meus estudos. A professora tem uma excelente didatica e passou o conteúdo muito bem.

## Tecnologias utilizadas

- Linguagem de Programação Java
- Framework Spring Boot
- JPA e Hibernet
- Banco de Dados em mémoria H2
- Lombok
- Model Mapper
- JWT - Json Web Token para autenticação e autorização

## Funcionalidades do projeto

- Caso de uso 1: cadastrar/autenticar usuários (✔️)
- Caso de uso 2: pesquisar disciplinas a partir de uma (sub)string (✔️)
- Caso de uso 3: Adicionar comentários de uma disciplina (✔️)
- Caso de uso 4: Apagar comentários de uma disciplina (✔️) Função para manter comentários apagados ainda não foi implementado
- Caso de uso 5: Dar/retirar like em uma disciplina (✔️)
- Caso de uso 6: Deve ser possível recuperar o perfil de uma disciplina a partir do seu código numérico (✔️)
- Caso de uso 7: mostrar ranking das disciplinas (em desenvolvimento)

## Endpoints da aplicação

- Usuários
  - post("/usuarios") -> Cadastra um novo usuário no sistema
  - get("/usuarios") -> Lista usuários cadastrados

- Auth
  - post("/auth/usuarios") -> Faz o login de um usuário e lhe gera um token de autenticação

- Disciplinas
  - get("/disciplinas") -> Recebe uma substring como parâmetro e assim lista disciplinas que atendem a essa substring
  - get("/disciplinas/{id}") -> Mostra uma disciplina pelo seu id
  - post("/disciplinas/{id}/comentario") -> Adiciona um comentário a umas disciplina
  - delete("/disciplinas/{id}/comentario/{id}") -> Deleta um comentário feito em uma dada disciplina
  - post("/disciplinas/{id}/like") -> Dar e Retira like de uma disciplina
  - post("/disciplinas/{id}/nota") -> Dar uma nota a uma disciplina

`Rosivaldo Lucas, estudante de Engenharia de Computação - UFPB`

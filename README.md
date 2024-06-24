# ProjetoIntegrador

### Este é um projeto feito pelo GRUPO-6 para o projeto integrador durante o 3º semestre 

version - 0.1.0

- Adicionado pasta inicial do projeto com spring boot.

version 0.2.0

- Adicionado arquivos de usuario
- Adicionado paginas de login e logon
- Agora é possível cadastrar um usuario no sistema

version 0.3.0

- adicionado arquivos da package model
    - classe Acao
    - classe TipoAcao
    - classe Localizacao
    - classe Anexo
    - classe Disponibilidade
    - classe Categoria
    - classe Item
    - classe Estado
- modificado classe Usuario
    Adicionado conexão com classe Acao

version 0.4.0

- adicionado arquivos da package repository
    - interface Acao
    - interface TipoAcao
    - interface Localizacao
    - interface Anexo
    - interface Disponibilidade
    - interface Categoria
    - interface Item
    - interface Estado

    
version 0.5.0

- adicionados arquivos da package controller, agora rest functions funcionam com o mysql, arquivos:
    -  Acaocontroller
    -  Tipoacaocontroller
    -  Localizacaocontroller
    -  Anexocontroller
    -  Disponibilidadecontroller
    -  Categoriacontroller
    -  Itemcontroller
    -  Estadocontroller
- modificado arquivos da package repository, agora atendem aos requisitos do controller
        - interface Acao
    - interface TipoAcao
    - interface Localizacao
    - interface Anexo
    - interface Disponibilidade
    - interface Categoria
    - interface Item
    - interface Estado

version 0.6.0
- modificado arquivos de repository e controller:
    - Classes extendem Page and Sorting Repository
        - TipoAcaoRepository
        - LocalizacaoRepository
        - DisponibilidadeRepository
        - CategoriaRepository
        - ItemRepository
        - EstadoRepository
    - Adicionado métodos e querys personalizadas
        - UsuarioRepository
            Adicionado pesquisa de usuario por email
        - ItemRepository
            Adicionado pesquisa por filtros
            1. Descrição (nome, string)
            2. Categoria (id, long)
            3. Disponibilidade (id, long)
            4. Estado (id, long)
            5. Localização (id, long)

- adicionado pagina de cadastro de campos ativos
    - Select para campos 
        1. Categoria
        2. Disponibilidade
        3. Estado
        4. TipoAcao
    - Adicionado função para cadastro, exclusão e leitura dos campos com paginação

version 0.7.0

- adicionado pagina de login, logon e alterar usuario
    - table usuario:
        1. Nome
        2. Sobrenome
        3. Email
        4. Senha
        5. Numero de Cadastro
    - agora é possível cadastrar usuario e alterar seus campos
    - senha é criptografada
    - campo Email eh considerado informação unica de cadastro
    - agora é possível verificar credenciais para logar (fake login, nao há bloqueio de paginas nem token de verificacao) 

version 0.8.0

- adicionado página de empréstimo
    - puxa informações sobre o item no banco de dados:
        1. Nome
        2. Disponibilidade
        3. Categoria
        4. Origem
    - campos para submeter:
        1. Local do item
        2. Responsável pelo empréstimo do item
        3. Informação extra sobre o item caso necessário
# DayCare

Sistema desktop para gerenciamento de clientes e animais de uma creche, desenvolvido em Java como projeto acadêmico.

## Sobre o projeto

O **DayCare** tem como objetivo facilitar o cadastro e o gerenciamento de clientes e seus respectivos animais, permitindo organizar essas informações por meio de uma interface gráfica simples e intuitiva.

O sistema utiliza Java Swing para a interface, SQLite para armazenamento dos dados e JDBC para comunicação com o banco de dados.

## Funcionalidades

### Gerenciamento de clientes
- Cadastro de clientes.
- Consulta e listagem de clientes.
- Atualização dos dados cadastrados.
- Exclusão de clientes, desde que não possuam animais vinculados.
- Validação de CPF único.

### Gerenciamento de animais
- Cadastro de animais.
- Consulta e listagem de animais.
- Atualização dos dados cadastrados.
- Exclusão de animais.
- Associação de cada animal a um cliente responsável.

## Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Java Swing** — desenvolvimento da interface gráfica.
- **SQLite** — banco de dados relacional.
- **JDBC** — conexão e comunicação com o banco de dados.
- **Apache NetBeans** — ambiente de desenvolvimento.

## Estrutura do projeto

O projeto utiliza uma organização em camadas para separar as responsabilidades:

```text
DayCare/
├── lib/
├── nbproject/
├── src/
│   └── daycare/
│       ├── dao/
│       │   ├── AnimalDAO.java
│       │   ├── ClienteDAO.java
│       │   └── Conexao.java
│       ├── model/
│       │   ├── Animal.java
│       │   └── Cliente.java
│       ├── service/
│       │   ├── AnimalService.java
│       │   ├── BusinessException.java
│       │   └── ClienteService.java
│       ├── util/
│       │   └── CampoUtils.java
│       ├── view/
│       │   ├── TelaAnimal.java
│       │   ├── TelaCliente.java
│       │   └── TelaPrincipal.java
│       └── Daycare.java
├── build.xml
├── manifest.mf
└── .gitignore
```

### Organização das camadas

| Camada | Responsabilidade |
|---|---|
| `model` | Representação das entidades do sistema. |
| `dao` | Acesso ao banco de dados e operações SQL. |
| `service` | Regras de negócio e validações. |
| `view` | Interfaces gráficas e interação com o usuário. |
| `util` | Métodos utilitários. |

## Banco de dados

O sistema utiliza SQLite e possui duas tabelas principais:

### cliente

- `id`: identificador do cliente.
- `cpf`: CPF do cliente, obrigatório e único.
- `nome`: nome do cliente.
- `telefone`: telefone de contato.
- `email`: endereço de e-mail.
- `endereco`: endereço do cliente.

### animal

- `id`: identificador do animal.
- `nome`: nome do animal.
- `especie`: espécie do animal.
- `raca`: raça do animal.
- `data_nascimento`: data de nascimento.
- `cliente_id`: identificador do cliente responsável.

Um cliente pode estar associado a vários animais, enquanto cada animal possui apenas um cliente responsável.

## Como executar

1. Instale o **Java JDK** e o **Apache NetBeans**.
2. Clone este repositório:

```bash
git clone https://github.com/GabrielSenziani/DayCare.git
```

3. Abra o Apache NetBeans.
4. Selecione **File → Open Project**.
5. Localize a pasta `DayCare` clonada e abra o projeto.
6. Execute o projeto a partir da classe principal `Daycare.java`.

As bibliotecas necessárias estão disponibilizadas na pasta `lib/`.


Projeto acadêmico desenvolvido para fins de aprendizado e aplicação prática de conceitos de programação orientada a objetos, persistência de dados, arquitetura em camadas e desenvolvimento de interfaces gráficas.

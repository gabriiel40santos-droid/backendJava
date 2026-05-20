# Diagrama Entidade-Relacionamento (DER)

```mermaid
erDiagram
    PACIENTE ||--o{ AGENDAMENTO : realiza
    UNIDADE_SAUDE ||--o{ AGENDAMENTO : recebe

    PACIENTE {
        Long id PK
        String nome
        String cpf
        String telefone
    }

    UNIDADE_SAUDE {
        Long id PK
        String nome
        String cnpj
        String endereco
    }

    AGENDAMENTO {
        Long id PK
        Long paciente_id FK
        Long unidade_saude_id FK
        LocalDateTime data_hora
        String status
    }
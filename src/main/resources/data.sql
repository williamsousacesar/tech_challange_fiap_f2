CREATE TABLE IF NOT EXISTS usuario (
    id_usuario UUID PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL);
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'carlos_dono', 'Carlos Oliveira', 'carlos.dono@restaurante.com', 'senha123', 'DONO_RESTAURANTE');
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'marina_silva', 'Mariana Silva', 'mariana.cliente@email.com', 'marina123', 'CLIENTE');
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440002', 'herson_marinho', 'Herson Noronha Marinho', 'herson_marinho@email.com', 'herson123', 'CLIENTE');
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440003', 'cleber_miranda', 'Cleber Miranda', 'cleber_miranda@email.com', 'cleber123', 'CLIENTE');
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440004', 'henrique_arraes', 'Henrique Arraes', 'henrique_arraes@email.com', 'henrique123', 'CLIENTE');
--
INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario)
VALUES ('550e8400-e29b-41d4-a716-446655440005', 'william_cesar', 'William Cesar', 'william_cesar@email.com', 'william123', 'CLIENTE');
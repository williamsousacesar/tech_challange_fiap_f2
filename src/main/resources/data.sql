CREATE TABLE IF NOT EXISTS usuario (
    id_usuario UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50));
--
-- Inserção de usuarios
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('1234'
       ,'carlos_dono'
       ,'Carlos Oliveira'
       ,'carlos.dono@restaurante.com'
       ,'senha123'
       ,'DONO_RESTAURANTE');
--
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('5678'
       ,'marina_silva'
       ,'Mariana Silva'
       ,'mariana.cliente@email.com'
       ,'marina123');
--
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('4567'
       ,'herson_marinho'
       ,'Herson Noronha Marinho'
       ,'herson_marinho@email.com'
       ,'herson123');
--
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('1765'
       ,'cleber_miranda'
       ,'Cleber Miranda'
       ,'cleber_miranda@email.com'
       ,'cleber123');
--
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('8976'
       ,'henrique_arraes'
       ,'Henrique Arraes'
       ,'henrique_arraes@email.com'
       ,'henrique123');
--
INSERT INTO usuarios (id_usuario
                     ,login
                     ,nome
                     ,email
                     ,senha
                     ,tipo_usuario)
VALUES ('2398'
       ,'william_cesar'
       ,'William Cesar'
       ,'william_cesar@email.com'
       ,'william123');
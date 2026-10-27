INSERT INTO tipo_usuario (tp_usuario, descricao_tipo_usuario)
SELECT 'DONO_RESTAURANTE', 'Dono de Restaurante' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM tipo_usuario WHERE tp_usuario = 'DONO_RESTAURANTE');

INSERT INTO tipo_usuario (tp_usuario, descricao_tipo_usuario)
SELECT 'CLIENTE', 'Cliente' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM tipo_usuario WHERE tp_usuario = 'CLIENTE');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440000', 'carlos_dono', 'Carlos Oliveira', 'carlos.dono@restaurante.com', 'senha123', id
FROM tipo_usuario WHERE tp_usuario = 'DONO_RESTAURANTE' 
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440000');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440001', 'marina_silva', 'Mariana Silva', 'mariana.cliente@email.com', 'marina123', id
FROM tipo_usuario WHERE tp_usuario = 'CLIENTE'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440001');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440002', 'herson_marinho', 'Herson Noronha Marinho', 'herson_marinho@email.com', 'herson123', id
FROM tipo_usuario WHERE tp_usuario = 'CLIENTE'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440002');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440003', 'cleber_miranda', 'Cleber Miranda', 'cleber_miranda@email.com', 'cleber123', id
FROM tipo_usuario WHERE tp_usuario = 'CLIENTE'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440003');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440004', 'henrique_arraes', 'Henrique Arraes', 'henrique_arraes@email.com', 'henrique123', id
FROM tipo_usuario WHERE tp_usuario = 'CLIENTE'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440004');

INSERT INTO usuario (id_usuario, login, nome, email, senha, tipo_usuario_id)
SELECT '550e8400-e29b-41d4-a716-446655440005', 'william_cesar', 'William Cesar', 'william_cesar@email.com', 'william123', id
FROM tipo_usuario WHERE tp_usuario = 'CLIENTE'
AND NOT EXISTS (SELECT 1 FROM usuario WHERE id_usuario = '550e8400-e29b-41d4-a716-446655440005');

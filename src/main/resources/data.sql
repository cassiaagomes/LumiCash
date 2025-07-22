

ALTER SEQUENCE categoria_id_seq RESTART WITH 30;
ALTER SEQUENCE correntista_id_seq RESTART WITH 2;


-- Categorias de ENTRADA (E)
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (1, TRUE, 'ENTRADA', 'Salario', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (2, TRUE, 'ENTRADA', 'Cashback', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (3, TRUE, 'ENTRADA', 'Resgate Investimento', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (4, TRUE, 'ENTRADA', 'Outras Entradas', 4) ON CONFLICT (id) DO NOTHING;

-- Categorias de SAÍDA (S)
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (5, TRUE, 'SAIDA', 'Saude e Remedios', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (6, TRUE, 'SAIDA', 'Academia e Personal', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (7, TRUE, 'SAIDA', 'Carros e Uber', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (8, TRUE, 'SAIDA', 'Educacao e Cursos', 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (9, TRUE, 'SAIDA', 'Lazer e Turismo', 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (10, TRUE, 'SAIDA', 'Condominio', 6) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (11, TRUE, 'SAIDA', 'Energia', 7) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (12, TRUE, 'SAIDA', 'Celular', 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (13, TRUE, 'SAIDA', 'Internet', 9) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (14, TRUE, 'SAIDA', 'Itens Pessoais', 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (15, TRUE, 'SAIDA', 'Feira', 11) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (16, TRUE, 'SAIDA', 'Casa', 12) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (17, TRUE, 'SAIDA', 'Impostos', 13) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (18, TRUE, 'SAIDA', 'Outros gastos', 14) ON CONFLICT (id) DO NOTHING;

-- Categorias de INVESTIMENTO (I)
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (19, TRUE, 'INVESTIMENTO', 'Aporte Renda Fixa', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (20, TRUE, 'INVESTIMENTO', 'Aporte Renda Variavel', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (21, TRUE, 'INVESTIMENTO', 'Aporte Reserva Emergencia', 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO categoria (id, ativo, natureza, nome, ordem) VALUES (22, TRUE, 'INVESTIMENTO', 'Aporte Previdencia', 4) ON CONFLICT (id) DO NOTHING;

insert into correntista values (1,true,'adm@gmail.com',true,'Chefao','12345678') ON CONFLICT (id) DO NOTHING;
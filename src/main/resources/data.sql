INSERT INTO ASSOCIACAO (ATIVO, NUMERO, ID, CIDADE, ENDERECO, ESTADO, NOME) 
VALUES 
(1, 1, NEXTVAL('ASSOCIACAO_SEQ'), 'Cidade1', 'Endereco1', 'Estado1', 'Nome1'),
(1, 2, NEXTVAL('ASSOCIACAO_SEQ'), 'Cidade2', 'Endereco2', 'Estado2', 'Nome2'),
(1, 3, NEXTVAL('ASSOCIACAO_SEQ'), 'Cidade3', 'Endereco3', 'Estado3', 'Nome3');


INSERT INTO ASSOCIADO (DATA_ASSOCIACAO, DATA_REMISSAO, NASCIMENTO, NUMERO, REMIDO, ASSOCIACAO_ID, ID, CIDADE, CPF, EMAIL, ENDERECO, ESTADO, NOME, TELEFONE, WHATSAPP) 
VALUES 
('2022-01-01', NULL, '1980-01-01', 1, 0, 1, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade1', 'CPF1', 'email1@example.com', 'Endereco1', 'Estado1', 'Nome1', 'Telefone1', 'Whatsapp1'),
('2022-01-02', NULL, '1981-01-01', 2, 0, 1, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade2', 'CPF2', 'email2@example.com', 'Endereco2', 'Estado2', 'Nome2', 'Telefone2', 'Whatsapp2'),
('2022-01-03', NULL, '1982-01-01', 3, 0, 2, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade3', 'CPF3', 'email3@example.com', 'Endereco3', 'Estado3', 'Nome3', 'Telefone3', 'Whatsapp3'),
('2022-01-04', NULL, '1983-01-01', 4, 0, 2, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade4', 'CPF4', 'email4@example.com', 'Endereco4', 'Estado4', 'Nome4', 'Telefone4', 'Whatsapp4'),
('2022-01-05', NULL, '1984-01-01', 5, 0, 3, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade5', 'CPF5', 'email5@example.com', 'Endereco5', 'Estado5', 'Nome5', 'Telefone5', 'Whatsapp5'),
('2022-01-06', NULL, '1985-01-01', 6, 0, 3, NEXTVAL('ASSOCIADO_SEQ'), 'Cidade6', 'CPF6', 'email6@example.com', 'Endereco6', 'Estado6', 'Nome6', 'Telefone6', 'Whatsapp6');


INSERT INTO Taxa (CODIGO, PARCELAS, TIPO_TAXA, VALOR_ANO, VIGENCIA, ASSOCIACAO_ID, ID, NOME) 
VALUES 
(1, 12, 0, 100.0, 2024, 1, NEXTVAL('TAXA_SEQ'), 'Anuidade'),
(2, 12, 0, 200.0, 2024, 2, NEXTVAL('TAXA_SEQ'), 'Anuidade'),
(3, 12, 0, 300.0, 2024, 3, NEXTVAL('TAXA_SEQ'), 'Anuidade');

INSERT INTO reuniao (DATA, ASSOCIACAO_ID, ID, PAUTA) 
VALUES 
('2022-01-01', 1, NEXTVAL('REUNIAO_SEQ'), 'Pauta1'),
('2022-01-02', 2, NEXTVAL('REUNIAO_SEQ'), 'Pauta2'),
('2022-01-03', 3, NEXTVAL('REUNIAO_SEQ'), 'Pauta3'),
('2022-01-01', 1, NEXTVAL('REUNIAO_SEQ'), '2 reuniao Pauta');

INSERT INTO ASSOCIADO_REUNIAO (ASSOCIADO_ID, REUNIAO_ID) 
VALUES 
(1, 1),
(2, 1),
(1, 4),
(3, 2);

INSERT INTO PAGAMENTO (DATA, VALOR, ASSOCIACAO_ID, ASSOCIADO_ID, ID, TAXA_ID) 
VALUES 
('2022-01-01', 100.00, 1, 1, NEXTVAL('PAGAMENTO_SEQ'), 1),
('2022-01-02', 200.00, 2, 2, NEXTVAL('PAGAMENTO_SEQ'), 2),
('2022-01-03', 300.00, 3, 3, NEXTVAL('PAGAMENTO_SEQ'), 3);
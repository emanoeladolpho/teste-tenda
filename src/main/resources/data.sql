INSERT INTO categoria (COD_CATEGORIA, ATIVO, DESCRICAO)
VALUES
    (1, true, 'Eletrônicos'),
    (2, true, 'Roupas'),
    (3, true, 'Acessórios'),
    (4, true, 'Livros'),
    (5, true, 'Casa e Decoração');

INSERT INTO marca (COD_MARCA, ATIVO, DESCRICAO)
VALUES
    (1, true, 'Samsung'),
    (2, true, 'Nike'),
    (3, true, 'Sony'),
    (4, true, 'LG'),
    (5, true, 'Adidas');

INSERT INTO produto (COD_PRODUTO, ALTURA_CM, ATIVO, DESCRICAO, DETALHE, LARGURA_CM, PESO_GRAMA, PRECO, PROFUNDIDADE_CM, SKU, FK_COD_CATEGORIA, FK_COD_MARCA)
VALUES
    (1, 15, true, 'Smartphone Galaxy', 'Novo modelo com câmera aprimorada', 7, 150, 899.99, 1.5, 'GALAXY123', 1, 1),
    (2, 25, true, 'Tênis Running', 'Design moderno para corridas', 10, 300, 129.99, 5, 'NIKE456', 2, 2),
    (3, 30, true, 'TV LED 4K', 'Resolução incrível e tecnologia avançada', 40, 5000, 1499.99, 8, 'SONY789', 1, 3),
    (4, 20, true, 'Livro de Ficção', 'Autor renomado, best-seller', 15, 800, 29.99, 2, 'NOVEL2022', 4, 4),
    (5, 40, true, 'Sofá Moderno', 'Conforto e estilo combinados', 60, 10000, 799.99, 12, 'MODERN123', 5, 5);

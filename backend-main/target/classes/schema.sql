CREATE DATABASE IF NOT EXISTS sanea;
USE sanea;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    cpf VARCHAR(14) NOT NULL UNIQUE,
    senha_hash VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tipos_problema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    icon VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS regioes_santos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_regiao VARCHAR(100) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    populacao INT,
    area FLOAT,
    zona VARCHAR(50),
    taxa FLOAT,
    densidade_populacional FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS relatos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo_problema VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    data_ocorrido DATE NOT NULL,
    cep VARCHAR(9) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    status ENUM('pendente', 'em_andamento', 'resolvido') DEFAULT 'pendente',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reclamacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo_problema_id INT NOT NULL,
    regiao_id INT NOT NULL,
    endereco TEXT NOT NULL,
    descricao TEXT NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    status ENUM('pendente', 'em_andamento', 'resolvido') DEFAULT 'pendente',
    anexo_patch VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (tipo_problema_id) REFERENCES tipos_problema(id) ON DELETE CASCADE,
    FOREIGN KEY (regiao_id) REFERENCES regioes_santos(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS acompanhamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reclamacao_id INT NOT NULL,
    responsavel VARCHAR(100) NOT NULL,
    acao TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reclamacao_id) REFERENCES reclamacoes(id) ON DELETE CASCADE
);

-- Inserir tipos de problema padrão
INSERT INTO tipos_problema (nome, descricao, icon) VALUES
('Esgoto a céu aberto', 'Vazamento ou acúmulo de esgoto em vias públicas', 'fa-tint'),
('Falta de água', 'Ausência prolongada de abastecimento de água', 'fa-faucet'),
('Água contaminada', 'Água com cor, odor ou sabor estranho', 'fa-flask'),
('Entupimento', 'Bueiros ou redes de esgoto entupidas', 'fa-water'),
('Alagamento', 'Pontos de alagamento recorrente', 'fa-umbrella'),
('Vazamento', 'Vazamentos de água em vias públicas', 'fa-tint'),
('Outros', 'Outros problemas relacionados a saneamento', 'fa-question-circle'); 
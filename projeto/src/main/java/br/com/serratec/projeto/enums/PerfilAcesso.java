package br.com.serratec.projeto.enums;

public enum PerfilAcesso {
    
    ROLE_ADMIN,        // Acesso total (Gerente/Dono)
    ROLE_MECANICO,     // Acesso operacional (Criar OS, atualizar status)
    ROLE_RECEPCIONISTA // Acesso de atendimento (Cadastrar clientes/veículos)
}
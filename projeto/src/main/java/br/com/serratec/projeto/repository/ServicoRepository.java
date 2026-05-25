package br.com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    
}
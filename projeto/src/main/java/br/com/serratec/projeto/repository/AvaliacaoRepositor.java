package br.com.serratec.projeto.repository;

import br.com.serratec.projeto.model.Avaliacao; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepositor extends JpaRepository<Avaliacao, Long> {
}
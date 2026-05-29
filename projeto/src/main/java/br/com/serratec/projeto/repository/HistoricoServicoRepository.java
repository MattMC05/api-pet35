package br.com.serratec.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.serratec.projeto.model.HistoricoServico;

@Repository
public interface HistoricoServicoRepository extends JpaRepository<HistoricoServico, Long> {
    List<HistoricoServico> findByServicoId(Long servicoId);
}

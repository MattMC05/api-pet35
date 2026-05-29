package br.com.serratec.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.ModeloNotificacao;

public interface ModeloNotificacaoRepository extends JpaRepository<ModeloNotificacao, Long> {

    Optional<ModeloNotificacao> findByStatusOs(String statusOs);
}
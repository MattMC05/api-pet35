package br.com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.OrdemDeServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemDeServico, Long> {
    
    OrdemDeServico findByClienteId(Long clienteId);

    OrdemDeServico findByVeiculoId(Long veiculoId);
}

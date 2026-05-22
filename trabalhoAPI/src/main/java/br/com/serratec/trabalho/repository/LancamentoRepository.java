package br.com.serratec.trabalho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.trabalho.model.LancamentoVendas;

public interface LancamentoRepository extends JpaRepository<LancamentoVendas, Long>{

}

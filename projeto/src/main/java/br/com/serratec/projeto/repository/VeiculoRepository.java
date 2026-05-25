package br.com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Veiculo findByPlaca(String placa);

}
package br.com.serratec.exercicio1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.exercicio1.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta,Long>{

}

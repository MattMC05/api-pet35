package br.com.serratec.exercicio1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.exercicio1.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente,Long>{

}

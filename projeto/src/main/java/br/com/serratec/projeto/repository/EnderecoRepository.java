package br.com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco,String>{
    Endereco findByCep(String cep);
}
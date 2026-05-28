package br.com.serratec.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.projeto.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);

    Cliente findByEmail(String email);
}
package br.com.serratec.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.model.Veiculo;
import br.com.serratec.projeto.repository.VeiculoRepository;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository repository;

    public Veiculo inserir(Veiculo veiculo){
        return repository.save(veiculo);
    }

    public Optional<Veiculo> buscar(Long id){
        return repository.findById(id);
    }
}

package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.VeiculoResponseDTO;
import br.com.serratec.projeto.model.Veiculo;
import br.com.serratec.projeto.repository.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository repository;

    /*public Veiculo inserir(Veiculo veiculo){
        return repository.save(veiculo);
    }*/

    @Transactional
    public VeiculoResponseDTO inserir(Veiculo veiculo){
        repository.save(veiculo);
        return new VeiculoResponseDTO(veiculo);
    }

    public List<VeiculoResponseDTO> listarTodos(){
        return repository.findAll().stream()
        .map(veiculo -> new VeiculoResponseDTO(veiculo))
        .collect(Collectors.toList());
    }
    
    public Veiculo alterar(Long id, Veiculo veiculo){
        if (repository.existsById(id)) {
            veiculo.setId(id);
            return repository.save(veiculo);
        }
        return null;
    }

    public Page<Veiculo> listarPorPagina(Pageable pageable) {
        return repository.findAll(pageable);
    }

}

package br.com.serratec.projeto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.dto.OrdemServicoResponseDTO;
import br.com.serratec.projeto.model.OrdemDeServico;
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

    public List<OrdemServicoResponseDTO> listarTodos(){
    return repository.findAll().stream()
    .map(ordemServico -> new OrdemServicoResponseDTO(new OrdemDeServico()))
    .collect(Collectors.toList());
    }
}

package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.projeto.dto.EnderecoResponseDTO;
import br.com.serratec.projeto.exceptions.ClienteEmailException;
import br.com.serratec.projeto.exceptions.EnderecoException;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enRepository;

    public Endereco salvar(Endereco endereco){
        return enRepository.save(buscarCep(endereco.getCep()));
    }

    public List<EnderecoResponseDTO> listarTodos(){
        return enRepository.findAll().stream()
        .map(endereco -> new EnderecoResponseDTO(endereco))
        .collect(Collectors.toList());
    }

    public Page<Endereco> listarPorBairro(Pageable pageable, String bairro){
        return enRepository.findByBairro(pageable, bairro);
    }

    public Endereco alterar(String cep, Endereco endereco){
        if (enRepository.existsById(cep)) {
            Endereco enderecoExistente = enRepository.findById(cep).get();
            endereco.setCep(cep);
            if (endereco.getBairro() == null) {
                endereco.setBairro(enderecoExistente.getBairro());
            }
            if (endereco.getLocalidade() == null) {
                endereco.setLocalidade(enderecoExistente.getLocalidade());
            }
            if (endereco.getLogradouro() == null) {
                endereco.setLogradouro(enderecoExistente.getLogradouro());
            }
            if (endereco.getUf() == null) {
                endereco.setUf(enderecoExistente.getUf());
            }
            return enRepository.save(endereco);
        }
        throw new ResourceNotFoundException("Endereco não encontrado");
    }

    public void apagar(String cep){
        if (enRepository.existsById(cep)) {
            enRepository.deleteById(cep);
        }else{
            throw new ResourceNotFoundException("Endereco não encontrado");
        }
    }





    private Endereco buscarCep(String cep){
        Endereco enderecoBanco = enRepository.findByCep(cep);
        if (enderecoBanco != null) {
            throw new ClienteEmailException("Este Cep já está cadastrado");
        }else{
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://viacep.com.br/ws/"+cep+"/json/";
            Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
            if (enderecoViaCep != null) {
                enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));
                return enderecoViaCep;
            }
        }
        throw new EnderecoException("Cep não encontrado!");
    }

}

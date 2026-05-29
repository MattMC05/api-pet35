package br.com.serratec.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.projeto.configuration.MailConfig;
import br.com.serratec.projeto.dto.ClienteResponseDTO;
import br.com.serratec.projeto.dto.EnderecoResponseDTO;
import br.com.serratec.projeto.exceptions.ClienteEmailException;
import br.com.serratec.projeto.exceptions.EnderecoException;
import br.com.serratec.projeto.exceptions.ResourceNotFoundException;
import br.com.serratec.projeto.model.Cliente;
import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.repository.ClienteRepository;
import br.com.serratec.projeto.repository.EnderecoRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enRepository;

    @Autowired
    private MailConfig mailConfig;

    @Transactional
    public ClienteResponseDTO inserir(Cliente cliente){
        Cliente clienteBanco = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteBanco != null) {
            throw new ClienteEmailException("Email já cadastrado");
        }
        
        clienteRepository.save(cliente);
        buscarCep(cliente.getEndereco().getCep());
        mailConfig.sendMail(cliente.getEmail(), "Cadastro de cliente", cliente.toString());
        return new ClienteResponseDTO(cliente);
    }



    private EnderecoResponseDTO buscarCep(String cep){
        /*Endereco enderecoBanco = enRepository.findByCep(cep);
        if (enderecoBanco != null) {
            return new EnderecoResponseDTO(enderecoBanco);
        }else{*/
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/"+cep+"/json/";
        Endereco enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
        if (enderecoViaCep != null) {
            enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));
            return inserir(enderecoViaCep);
        }
        //}
        throw new EnderecoException("Cep não encontrado!");
    }

    private EnderecoResponseDTO inserir(Endereco enderecoViaCep) {
        return new EnderecoResponseDTO(enRepository.save(enderecoViaCep));
    }

    public List<ClienteResponseDTO> listarTodos(){
        return clienteRepository.findAll().stream()
        .map(cliente -> new ClienteResponseDTO(cliente))
        .collect(Collectors.toList());
    }

    public ClienteResponseDTO alterar(Long id, Cliente cliente){
        if (clienteRepository.existsById(id)) {
            cliente.setId(id);
            clienteRepository.save(cliente);
            mailConfig.sendMail(cliente.getEmail(), "Atualização de dados do cliente", cliente.toString());
            return new ClienteResponseDTO(cliente);
        }
        return null;
    }

    public void apagar(Long id){
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

}

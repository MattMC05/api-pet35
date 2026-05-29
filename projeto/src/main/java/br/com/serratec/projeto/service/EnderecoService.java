package br.com.serratec.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.projeto.model.Endereco;
import br.com.serratec.projeto.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enRepository;

    public Page<Endereco> listarPorBairro(Pageable pageable, String bairro){
        return enRepository.findByBairro(pageable, bairro);
    }

}

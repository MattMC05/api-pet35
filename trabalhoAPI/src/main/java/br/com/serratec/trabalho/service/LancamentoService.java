package br.com.serratec.trabalho.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.trabalho.DTO.LancamentoVendasResponseDTO;
import br.com.serratec.trabalho.exceptions.LancamentoException;
import br.com.serratec.trabalho.model.LancamentoVendas;
import br.com.serratec.trabalho.repository.LancamentoRepository;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    public LancamentoVendasResponseDTO buscarPorId(Long id) {

        Optional<LancamentoVendas> lancamentoEncontrado = repository.findById(id);

        if (lancamentoEncontrado.isEmpty()) {
            throw new LancamentoException("Lançamento não encontrado com o ID: " + id);
        }

        LancamentoVendas lancamento = lancamentoEncontrado.get();
        return new LancamentoVendasResponseDTO(lancamento.getData(), lancamento.getValor(), lancamento.getVendedor().getNome());
    }
    public LancamentoVendas inserirLancamento(LancamentoVendas lancamentoVendas){
        return repository.save(lancamentoVendas);
    }
}   


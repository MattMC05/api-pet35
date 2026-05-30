package br.com.serratec.projeto.service;

import br.com.serratec.projeto.model.Avaliacao; 
import br.com.serratec.projeto.repository.AvaliacaoRepositor; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepositor repository;

    public List<Avaliacao> listarTodas() {
        return repository.findAll();
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5.");
        }
        return repository.save(avaliacao);
    }

    // Atualizar existente
    public Avaliacao atualizar(Long id, Avaliacao novosDados) {
        Optional<Avaliacao> antiga = repository.findById(id);
        if (antiga.isPresent()) {
            Avaliacao avaliacao = antiga.get();
            if (novosDados.getNota() != null) {
                if (novosDados.getNota() < 1 || novosDados.getNota() > 5) {
                    throw new IllegalArgumentException("Nota inválida.");
                }
                avaliacao.setNota(novosDados.getNota());
            }
            if (novosDados.getComentario() != null) {
                avaliacao.setComentario(novosDados.getComentario());
            }
            return repository.save(avaliacao);
        }
        return null;
    }

    // Deletar por id
    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
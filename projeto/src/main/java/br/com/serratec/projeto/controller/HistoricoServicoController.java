package br.com.serratec.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.projeto.dto.HistoricoServicoResponseDTO;
import br.com.serratec.projeto.service.HistoricoServicoService;

@RestController
@RequestMapping("/historico-servicos")
public class HistoricoServicoController {

    @Autowired
    private HistoricoServicoService service;

    @GetMapping
    public ResponseEntity<List<HistoricoServicoResponseDTO>> listarTodoHistorico() {
        return ResponseEntity.ok(service.listarTodoHistorico());
    }

    @GetMapping("/servico/{servicoId}")
    public ResponseEntity<List<HistoricoServicoResponseDTO>> listarHistoricoPorServico(@PathVariable Long servicoId) {
        return ResponseEntity.ok(service.listarHistoricoPorServico(servicoId));
    }

    @DeleteMapping("/servico/{servicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarHistoricoServico(@PathVariable Long servicoId) {
        service.deletarHistoricoServico(servicoId);
    }

    @DeleteMapping("/{historicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRegistroHistorico(@PathVariable Long historicoId) {
        service.deletarRegistroHistorico(historicoId);
    }
}

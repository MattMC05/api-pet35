package br.com.serratec.exercicio1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.exercicio1.model.Paciente;
import br.com.serratec.exercicio1.repository.PacienteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paciente inserir(@Valid @RequestBody Paciente paciente){
        return repository.save(paciente);
    }

    @PutMapping("{id}")
    public ResponseEntity<Paciente> atualizar(@Valid @RequestBody Paciente paciente, @PathVariable Long id){
        if (repository.existsById(id)) {
            paciente.setId(id);
            return ResponseEntity.ok(repository.save(paciente));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Paciente> buscar(@PathVariable Long id){
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Paciente> listar(){
        return repository.findAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id){
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

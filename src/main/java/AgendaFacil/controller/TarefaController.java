package AgendaFacil.controller;

import AgendaFacil.dto.RecurringTarefaDTO;
import AgendaFacil.service.TarefaService;
import AgendaFacil.tarefa.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<List<Tarefa>> getAllTarefasForUser(Principal principal) {
        List<Tarefa> tarefas = tarefaService.getAllTarefasForUser(principal.getName());
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping
    public ResponseEntity<Tarefa> createTarefa(@RequestBody Tarefa tarefa, Principal principal) {
        Tarefa novaTarefa = tarefaService.createTarefaForUser(tarefa, principal.getName());
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @PostMapping("/recorrente")
    public ResponseEntity<List<Tarefa>> createRecurringTarefas(@RequestBody RecurringTarefaDTO dto, Principal principal) {
        List<Tarefa> tarefasCriadas = tarefaService.createRecurringTarefasForUser(dto, principal.getName());
        return new ResponseEntity<>(tarefasCriadas, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetails, Principal principal) {
        Tarefa tarefaAtualizada = tarefaService.updateTarefaForUser(id, tarefaDetails, principal.getName());
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id, Principal principal) {
        tarefaService.deleteTarefaForUser(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
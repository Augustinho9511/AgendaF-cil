package AgendaFacil.controller;

import AgendaFacil.dto.RecurringTarefaDTO;
import AgendaFacil.service.TarefaService;
import AgendaFacil.tarefa.Tarefa;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/Listar")
    public ResponseEntity<List<Tarefa>> getAllTarefa() {
        List<Tarefa> tarefas = tarefaService.getAllTarefa();
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @PostMapping("/CreateRecurring")
    public ResponseEntity<List<Tarefa>> createRecurringAposta(@RequestBody RecurringTarefaDTO dto) {
        List<Tarefa> tarefasCriadas = tarefaService.createRecurringTarefas(dto);
        return new ResponseEntity<>(tarefasCriadas, HttpStatus.CREATED);
    }

    @PostMapping("/Create")
    public ResponseEntity<Tarefa> createAposta(@RequestBody Tarefa tarefa) {
        Tarefa CreateTarefa = tarefaService.CreateTarefa(tarefa);
        return new ResponseEntity<>(CreateTarefa, HttpStatus.OK);
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        try {
            Tarefa updateTarefa = tarefaService.UpdateTarefa(id, tarefa);
            return new ResponseEntity<>(updateTarefa, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        tarefaService.deleteTarefa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

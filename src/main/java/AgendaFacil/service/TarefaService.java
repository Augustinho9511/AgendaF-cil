package AgendaFacil.service;

import AgendaFacil.repository.TarefaRepository;
import AgendaFacil.tarefa.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> getAllTarefa() {
        return tarefaRepository.findAll();
    }

    public Tarefa CreateTarefa(Tarefa tarefa) {
        tarefa.setNome(tarefa.getNome());
        tarefa.setDescricao(tarefa.getDescricao());
        tarefa.setStatus(tarefa.getStatus());
        tarefa.setHorarioData(tarefa.getHorarioData());

        return tarefaRepository.save(tarefa);
    }

    public Tarefa UpdateTarefa(Long id, Tarefa tarefa) {
        return tarefaRepository.findById(id)
                .map(Tarefa -> {
                    tarefa.setStatus(tarefa.getStatus());
                    tarefa.setDescricao(tarefa.getDescricao());
                    tarefa.setHorarioData(tarefa.getHorarioData());
                    tarefa.setNome(tarefa.getNome());

                    return tarefaRepository.save(tarefa);
                })
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID " + id));
    }

    public void deleteTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}

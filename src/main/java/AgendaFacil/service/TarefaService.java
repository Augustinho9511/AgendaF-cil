package AgendaFacil.service;

import AgendaFacil.dto.RecurringTarefaDTO;
import AgendaFacil.repository.TarefaRepository;
import AgendaFacil.tarefa.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    public List<Tarefa> createRecurringTarefas(RecurringTarefaDTO dto) {
        List<Tarefa> novasTarefas = new ArrayList<>();
        // Correção 1: 'hoje' deve ser do tipo LocalDate
        LocalDate hoje = LocalDate.now();

        // Cria as tarefas para as próximas 4 semanas
        for (int i = 0; i < 28; i++) {
            LocalDate diaAtual = hoje.plusDays(i);
            DayOfWeek diaDaSemana = diaAtual.getDayOfWeek();

            // Correção 2: Acessa os dias da semana a partir do DTO
            if (dto.getDiasDaSemana().contains(diaDaSemana.toString())) {
                Tarefa novaTarefa = new Tarefa();
                novaTarefa.setNome(dto.getNome());
                novaTarefa.setDescricao(dto.getDescricao());
                novaTarefa.setStatus(dto.getStatus() != null ? dto.getStatus() : "Pendente");

                LocalDateTime horarioDataCompleta;
                // Correção 3: Acessa o horário a partir do DTO
                if (dto.getHorarioInicio() != null && dto.getHorarioFim() != null) {
                    novaTarefa.setHorarioInicio(diaAtual.atTime(dto.getHorarioInicio()));
                    novaTarefa.setHorarioFim(diaAtual.atTime(dto.getHorarioFim()));
                } else {
                    novaTarefa.setHorarioInicio(diaAtual.atStartOfDay());
                    novaTarefa.setHorarioFim(diaAtual.atStartOfDay().plusHours(1));
                }

                novasTarefas.add(novaTarefa);
            }
        }
        return tarefaRepository.saveAll(novasTarefas);
    }

    public Tarefa CreateTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa UpdateTarefa(Long id, Tarefa tarefa) {
        return tarefaRepository.findById(id)
                .map(Tarefa -> {
                    tarefa.setStatus(tarefa.getStatus());
                    tarefa.setDescricao(tarefa.getDescricao());
                    tarefa.setHorarioInicio(tarefa.getHorarioInicio());
                    tarefa.setHorarioFim(tarefa.getHorarioFim());
                    tarefa.setNome(tarefa.getNome());

                    return tarefaRepository.save(tarefa);
                })
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID " + id));
    }

    public void deleteTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}

package AgendaFacil.service;

import AgendaFacil.dto.RecurringTarefaDTO;
import AgendaFacil.repository.TarefaRepository;
import AgendaFacil.repository.UsuarioRepository;
import AgendaFacil.tarefa.Tarefa;
import AgendaFacil.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Tarefa> getAllTarefasForUser(String username) {
        return tarefaRepository.findByUsuario_Username(username);
    }

    public Tarefa createTarefaForUser(Tarefa tarefa, String username) {
        Usuario usuario = findUsuarioByUsername(username);
        tarefa.setUsuario(usuario);
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> createRecurringTarefasForUser(RecurringTarefaDTO dto, String username) {
        Usuario usuario = findUsuarioByUsername(username);
        List<Tarefa> novasTarefas = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < 28; i++) {
            LocalDate diaAtual = hoje.plusDays(i);
            DayOfWeek diaDaSemana = diaAtual.getDayOfWeek();

            if (dto.getDiasDaSemana().contains(diaDaSemana.toString())) {
                Tarefa novaTarefa = new Tarefa();
                novaTarefa.setNome(dto.getNome());
                novaTarefa.setDescricao(dto.getDescricao());
                novaTarefa.setStatus(dto.getStatus() != null ? dto.getStatus() : "Pendente");
                novaTarefa.setUsuario(usuario);

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


    public Tarefa updateTarefaForUser(Long id, Tarefa tarefaDetails, String username) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID " + id));

        if (!Objects.equals(tarefa.getUsuario().getUsername(), username)) {
            throw new AccessDeniedException("Usuário não autorizado a modificar esta tarefa");
        }

        tarefa.setNome(tarefaDetails.getNome());
        tarefa.setDescricao(tarefaDetails.getDescricao());
        tarefa.setStatus(tarefaDetails.getStatus());
        tarefa.setHorarioInicio(tarefaDetails.getHorarioInicio());
        tarefa.setHorarioFim(tarefaDetails.getHorarioFim());

        return tarefaRepository.save(tarefa);
    }

    public void deleteTarefaForUser(Long id, String username) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID " + id));

        if (!Objects.equals(tarefa.getUsuario().getUsername(), username)) {
            throw new AccessDeniedException("Usuário não autorizado a excluir esta tarefa");
        }
        tarefaRepository.deleteById(id);
    }

    private Usuario findUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário '" + username + "' não encontrado no sistema."));
    }
}
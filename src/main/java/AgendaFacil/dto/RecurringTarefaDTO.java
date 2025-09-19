package AgendaFacil.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class RecurringTarefaDTO {

    private String nome;
    private String descricao;
    private String status;
    private List<String> diasDaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    public RecurringTarefaDTO (String nome,  String descricao, String status, List<String> diasDaSemana,LocalTime horarioInicio, LocalTime horarioFim) {
        this.descricao = descricao;
        this.nome = nome;
        this.status = status;
        this.diasDaSemana = diasDaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }
}

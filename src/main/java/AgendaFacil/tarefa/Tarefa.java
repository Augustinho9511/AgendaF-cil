package AgendaFacil.tarefa;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "Tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "horario_inicio") // É uma boa prática usar snake_case em nomes de colunas
    private LocalDateTime horarioInicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "horario_fim")
    private LocalDateTime horarioFim;

    public Tarefa() {
    }

    public Tarefa (String nome, String descricao, String status, LocalDateTime horarioInicio, LocalDateTime horarioFim) {
        this.descricao = descricao;
        this.nome = nome;
        this.status = status;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }
}

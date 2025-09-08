package AgendaFacil.tarefa;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @Column(name = "horarioData")
    private LocalDateTime horarioData;

    public Tarefa() {
    }

    public Tarefa (String nome, String descricao, String status, LocalDateTime horarioData) {
        this.descricao = descricao;
        this.nome = nome;
        this.status = status;
        this.horarioData = horarioData;
    }
}

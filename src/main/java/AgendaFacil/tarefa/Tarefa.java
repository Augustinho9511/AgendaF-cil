package AgendaFacil.tarefa;

import AgendaFacil.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "horario_inicio")
    private LocalDateTime horarioInicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "horario_fim")
    private LocalDateTime horarioFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

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

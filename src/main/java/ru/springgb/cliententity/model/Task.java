package ru.springgb.cliententity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {

    public enum Status {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "description_task", nullable = false)
    private String description;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @UpdateTimestamp
    private LocalDateTime completionTime;


    @ManyToMany
    @JoinTable(
            name = "execute_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "executor_id"))
    private List<Executor> executors = new ArrayList<>();



    public void addExecutor(Executor executor) {
        this.executors.add(executor);

    }

    public void removeExecutor(long executorId) {
        Executor executor = this.executors.stream().filter(t -> t.getId() == executorId)
                .findFirst().orElse(null);
        if (executor != null) {
            this.executors.remove(executor);
            executor.getTasks().remove(this);
        }
    }


}

package ru.springgb.cliententity.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "executors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ToString
public class Executor {//implements InitializingBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "executor_name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "task_executers",
            joinColumns = @JoinColumn(name = "executor_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> tasks = new ArrayList<>();


    public void addTask(Task task) {
        this.tasks.add(task);

    }

    public void removeTask(long taskId) {
        Task task = this.tasks.stream().filter(t -> t.getId() == taskId)
                .findFirst().orElse(null);
        if (task != null) {
            this.tasks.remove(task);

        }
    }



}

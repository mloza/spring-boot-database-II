package pl.mloza.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {
    @GeneratedValue
    @Id
    private int id;

    @Column
    private String name;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @OneToOne(mappedBy = "project")
    private ProjectDetails projectDetails;

    @ManyToMany(mappedBy = "projects")
    private List<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}";

    }
}
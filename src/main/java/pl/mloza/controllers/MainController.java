package pl.mloza.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mloza.entity.Project;
import pl.mloza.entity.Tag;
import pl.mloza.entity.Task;
import pl.mloza.repository.ProjectRepository;
import pl.mloza.repository.TagRepository;
import pl.mloza.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Controller
public class MainController {
    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    public TagRepository tagRepository;

    @RequestMapping("/db")
    @ResponseBody
    public String testMethod() {
        StringBuilder response = new StringBuilder();

        Task task = new Task()
                .withName("Task 1")
                .withDescription("Test task")
                .withBudget(123.55)
                .withDone(true);
        taskRepository.save(task);

        for (Task i : taskRepository.findAll()) {
            response.append(i).append("<br>");
        }

        return response.toString();
    }

    @RequestMapping("/db2")
    @ResponseBody
    public String testMethod2() {
        StringBuilder response = new StringBuilder();

        response.append("Tasks with done set to true:<br>");
        for (Task i : taskRepository.findByDone(true)) {
            response.append(i).append("<br>");
        }

        response.append("Tasks with done set to false:<br>");
        for (Task i : taskRepository.findByDone(false)) {
            response.append(i).append("<br>");
        }

        response.append("Tasks with \"Do\" in description:<br>");
        for (Task i : taskRepository.getByDescriptionLike("Do")) {
            response.append(i).append("<br>");
        }

        return response.toString();
    }


    @RequestMapping("/project-tasks")
    @ResponseBody
    public String projectsAndTasks() {
        StringBuilder response = new StringBuilder();

        for (Project project : projectRepository.findAll()) {
            response.append(project).append("<br>");
            for (Task task : project.getTasks()) {
                response.append("- ").append(task).append("<br>");
            }
        }

        return response.toString();
    }

    @RequestMapping("/add-task")
    @ResponseBody
    public String addTask() {
        Project project = projectRepository.findOne(1);

        Task task = new Task()
                .withBudget(12.00)
                .withDescription("New task")
                .withName("New task")
                .withProject(project);

        project.getTasks().add(task);

        taskRepository.save(task);

        return projectsAndTasks();
    }

    @RequestMapping("/delete-task")
    @ResponseBody
    public String deleteTask() {
        Task task = taskRepository.findOne(1L);
        Project project = task.getProject();

        project.getTasks().remove(task);
        taskRepository.delete(task);

        return projectsAndTasks();
    }

    @RequestMapping("/tagging")
    @ResponseBody
    public String tagsAndProjects() {
        Tag tag1 = new Tag();
        tag1.setName("Tag 1");

        Tag tag2 = new Tag();
        tag2.setName("Tag 2");

        Project project1 = new Project();
        project1.setName("Project 1");

        Project project2 = new Project();
        project2.setName("Project 2");
        projectRepository.save(project1);
        projectRepository.save(project2);

        project1.setTags(new ArrayList<>());
        project1.getTags().add(tag1);

        project2.setTags(new ArrayList<>());
        project2.getTags().add(tag1);
        project2.getTags().add(tag2);

        tag1.setProjects(new ArrayList<>());
        tag1.getProjects().add(project1);
        tag1.getProjects().add(project2);

        tag2.setProjects(new ArrayList<>());
        tag2.getProjects().add(project2);

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        return "OK";
    }

    @RequestMapping("/by-tag")
    @ResponseBody
    public String projectsByTag() {
        StringBuilder out = new StringBuilder();
        tagRepository.findAll().forEach(tag -> {
            out.append("tag: ").append(tag.getName()).append("<br>");
            tag.getProjects().forEach(project -> {
                out.append("- ").append(project.getName()).append("<br>");
            });
        });
        return out.toString();
    }

    @RequestMapping("/by-project")
    @ResponseBody
    public String tagsByProject() {
        StringBuilder out = new StringBuilder();
        projectRepository.findAll().forEach(project -> {
            out.append("project: ").append(project.getName()).append("<br>");
            project.getTags().forEach(tag -> {
                out.append("- ").append(tag.getName()).append("<br>");
            });
        });
        return out.toString();
    }

    @RequestMapping("/remove-tag")
    @ResponseBody
    public String removeTag() {
        Optional<Project> project =
                StreamSupport
                        .stream(projectRepository
                                .findAll()
                                .spliterator(), false)
                        .filter(p -> p.getName().equals("Project 2"))
                        .findAny();

        project.ifPresent(p -> {
            Optional<Tag> tag = p.getTags().stream()
                    .filter(t -> t.getName().equals("Tag 2"))
                    .findAny();

            tag.ifPresent(t -> {
                t.getProjects().remove(p);
                p.getTags().remove(t);

                tagRepository.save(t);
                projectRepository.save(p);
            });
        });

        return "OK";
    }


}

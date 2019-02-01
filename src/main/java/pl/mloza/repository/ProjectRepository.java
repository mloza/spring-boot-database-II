package pl.mloza.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mloza.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer> { }
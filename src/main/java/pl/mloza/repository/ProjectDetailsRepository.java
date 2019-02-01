package pl.mloza.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mloza.entity.Project;
import pl.mloza.entity.ProjectDetails;

public interface ProjectDetailsRepository extends CrudRepository<ProjectDetails, Integer> { }
package pl.mloza.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mloza.entity.Project;
import pl.mloza.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> { }
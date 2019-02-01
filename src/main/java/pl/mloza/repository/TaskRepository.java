package pl.mloza.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mloza.entity.Task;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    public List<Task> findByDone(Boolean done);

    @Query("select t from Task t where t.description like %?1%")
    public List<Task> getByDescriptionLike(String search);
}

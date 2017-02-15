package is.hi.verkvitinn.persistence.repositories;

import is.hi.verkvitinn.persistence.entities.Project;

import java.util.List;

public interface ProjectRepository {
	Project save(Project newProject);

	Project findOne(Long id);

	List<Project> findByAdmin(String admin);

	List<Project> findAll();

	void delete(Project project);
}

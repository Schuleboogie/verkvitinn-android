package is.hi.verkvitinn.persistence.repositories;

import is.hi.verkvitinn.persistence.entities.Milestone;

import java.util.List;

public interface MilestoneRepository {
	Milestone save(Milestone newMilestone);

	List<Milestone> findByProjectId(Long projectId);

	Milestone findOne(Long id);

	void delete(Milestone Milestone);
}

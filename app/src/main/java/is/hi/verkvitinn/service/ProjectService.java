package is.hi.verkvitinn.service;

import android.content.Context;

import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.entities.Milestone;
import is.hi.verkvitinn.persistence.repositories.ProjectRepository;
import is.hi.verkvitinn.persistence.repositories.MilestoneRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ProjectService {
	// Project repository
	static ProjectRepository projects;
	// Milestone repository
	MilestoneRepository milestones;

	// Dependency Injection
	public ProjectService(ProjectRepository projects, MilestoneRepository milestones) {
		this.projects = projects;
		this.milestones = milestones;
	}

	// Create project
	public static Project create(Project newProject, Context context) {
		// Save project
		return projects.save(newProject, context);
	}

	// Find projects by admin
	public List<Project> findByAdmin(String admin, Context context) {
		return projects.findByAdmin(admin, context);
	}

	// Find project by id
	public Project findOne(Long id, Context context) {
		return projects.findOne(id, context);
	}

	// Delete project by id
	public void delete(Project project) {
		projects.delete(project);
	}

	// Find projects that worker is assigned to
	public List<Project> findByWorker(String worker) {
		List<Project> allProjects = projects.findAll();
		List<Project> workerProjects = new ArrayList<Project>();

		for (Project project : allProjects) {
			// Check if worker is assigned to project
			String[] workers = project.getWorkers();
			if (workers == null)
				continue;
			for (int i = 0; i < workers.length; i++) {
				if (workers[i].equals(worker))
					workerProjects.add(project);
			}
		}
		return workerProjects;
	}

	/* Milestones */
	// Set milestone
	public Milestone setMilestone(Milestone newMilestone, Context context) {
		return milestones.save(newMilestone, context);
	}
	// Find milestones associated with project with projectId
	public List<Milestone> findMilestones(Long projectId, Context context) {
		List<Milestone> foundMilestones = milestones.findByProjectId(projectId, context);
		// Sort milestones by timestamp
		if (foundMilestones != null) {
			Collections.sort(foundMilestones, new Comparator<Milestone>() {
				public int compare(Milestone m1, Milestone m2) {
					if (m1.getTimestamp() == null || m2.getTimestamp() == null)
						return 0;
					return m2.getTimestamp().compareTo(m1.getTimestamp());
				}
			});
		}
		return foundMilestones;
	}

	// Start and finish project with id projectId
	public boolean startProject(Long projectId, Context context) {
		Project project = projects.findOne(projectId, context);
		if (project != null) {
			project.setStatus("In progress");
			// Set start time
			project.setStartTime(new Date());
			Project updatedProject = projects.save(project, context);
			if (updatedProject != null)
				return true;
			else return false;
		}
		else return false;
	}
	public boolean finishProject(Long projectId, Context context) {
		Project project = projects.findOne(projectId, context);
		if (project != null) {
			project.setStatus("Finished");
			// Set finish time
			project.setFinishTime(new Date());
			Project updatedProject = projects.save(project, context);
			if (updatedProject != null)
				return true;
			else return false;
		}
		else return false;
	}
}

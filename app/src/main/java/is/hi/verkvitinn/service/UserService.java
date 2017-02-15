package is.hi.verkvitinn.service;

import is.hi.verkvitinn.persistence.entities.User;
import is.hi.verkvitinn.persistence.entities.Project;
import is.hi.verkvitinn.persistence.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;

public class UserService {
	// User repository
	UserRepository users;

	// Dependency Injection
	public UserService(UserRepository users) {
		this.users = users;
	}

	// Authenticate user
	public boolean auth(String username, String password) {
		/*String fetchedPassword = users.getPasswordByUsername(username);
		if (fetchedPassword != null) {
			byte[] givenPasswordBytes = MessageDigest.digest(password.getBytes());
			byte[] fetchedPasswordBytes = fetchedPassword.getBytes();
			return MessageDigest.isEqual(givenPasswordBytes, fetchedPasswordBytes);
		}
		else return false;*/
		return false;
	}

	// Register user
	public boolean register(String username, String password, String role, String name) {
		/*
		// Encode password
		MessageDigest.digest(password.getBytes());
		String encodedPassword = MessageDigest.toString();

		User newUser = new User(username, encodedPassword, role, name, false);
		// Save user
		if (users.save(newUser) != null)
			return true;
		else return false;
		return false;*/
        return false;
	}

	// Find user by username
	public User findByUsername(String username) {
		return users.findByUsername(username);
	}

	// Find users by role
	public List<User> findByRole(String role) {
		return users.findByRole(role);
	}

	// Find workers not in project
	public List<User> findWorkersNotInProject(Project project) {
		String[] projectWorkers = project.getWorkers();
		List<User> allWorkers = this.findByRole("worker");
		// Return all workers if no workers on project
		if (projectWorkers == null)
			return allWorkers;

		List<User> availableWorkers = new ArrayList<User>();
		for (User worker: allWorkers) {
			// Check if worker is in project
			boolean inProject = false;
			for (int i = 0; i < projectWorkers.length; i++) {
				if (worker.getUsername().equals(projectWorkers[i]))
					inProject = true;
			}
			// Add worker to project if worker not in project
			if (!inProject)
				availableWorkers.add(worker);
		}
		return availableWorkers;
	}
}

package is.hi.verkvitinn.service;

import android.content.Context;

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
	MessageDigest encoder;

	// Dependency Injection
	public UserService(UserRepository users) {
		this.users = users;
		//this.encoder = new MessageDigest("md5");
	}

	// Authenticate user
	public boolean auth(String username, String password, Context context) {
		String fetchedPassword = users.getPasswordByUsername(username, context);
		if (fetchedPassword != null) {
			/*
			byte[] givenPasswordBytes = encoder.digest(password.getBytes());
			byte[] fetchedPasswordBytes = fetchedPassword.getBytes();
			*/
			return fetchedPassword.equals(password);
			//return encoder.isEqual(givenPasswordBytes, fetchedPasswordBytes);
		}
		else return false;
	}

	// Register user
	public boolean register(String username, String password, String role, String name, Context context) {
		// Encode password
		/*
		encoder.digest(password.getBytes());
		String encodedPassword = encoder.toString();*/
		String encodedPassword = password;

		User newUser = new User(username, encodedPassword, role, name, false);
		// Save user
		if (users.save(newUser, context) != null)
			return true;
		else return false;
	}

	// Find user by username
	public User findByUsername(String username, Context context) {
		return users.findByUsername(username, context);
	}

	// Find users by role
	public List<User> findByRole(String role, Context context) {
		return users.findByRole(role, context);
	}

	// Find workers not in project
	public List<User> findWorkersNotInProject(Project project, Context context) {
		String[] projectWorkers = project.getWorkers();
		List<User> allWorkers = this.findByRole("worker", context);
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

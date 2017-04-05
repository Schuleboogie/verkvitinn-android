package is.hi.verkvitinn.service;

import android.content.Context;

import is.hi.verkvitinn.persistence.entities.Message;
import is.hi.verkvitinn.persistence.repositories.MessageRepository;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class MessageService {
	// Message repository
	MessageRepository messages;

	// Dependency Injection
	public MessageService(MessageRepository messages) {
		this.messages = messages;
	}

	// Create message
	public Message create(Message newMessage, Context context) {
		// Save message
		return messages.save(newMessage, context);
	}

	// Find messages by author
	public List<Message> findByProjectId(Long projectId, Context context) {
		List<Message> foundMessages = messages.findByProjectId(projectId, context);
		// Sort messages by timestamp
		if (foundMessages != null) {
			Collections.sort(foundMessages, new Comparator<Message>() {
				public int compare(Message m1, Message m2) {
					if (m1.getTimestamp() == null || m2.getTimestamp() == null)
						return 0;
					return m2.getTimestamp().compareTo(m1.getTimestamp());
				}
			});
		}
		return foundMessages;
	}
}

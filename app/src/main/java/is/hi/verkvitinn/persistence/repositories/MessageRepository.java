package is.hi.verkvitinn.persistence.repositories;

import is.hi.verkvitinn.persistence.entities.Message;

import java.util.List;


public interface MessageRepository  {
	Message save(Message newMessage);

	List<Message> findByProjectId(Long projectId);

	Message findOne(Long messageId);

	void delete(Message message);
}

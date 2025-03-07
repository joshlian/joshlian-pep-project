package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) 
    {
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) 
        {
            return null; 
        }
        
        if (!messageDAO.userExists(message.posted_by)) 
        {
            return null; 
        }
    
        return messageDAO.creatMessage(message);
    }

    public List<Message> getAllMessage()
    {
        return messageDAO.getAllMessage();
    }
    
    public Message getMessageById(int id)
    {
        return messageDAO.getMessageById(id);
    }

    public List<Message> getMessageByUser(int id)
    {
        return messageDAO.getMessageByUser(id);
    }

    public Message deleteMessage(int id) 
    {
        return messageDAO.deleteMessage(id);
    }
    
    public Message updateMessage(int messageId, String newText) 
    {
        if (newText.isBlank() || newText.length() > 255) 
        {
            return null;
        }
        return messageDAO.updateMessage(messageId, newText);
    }
}

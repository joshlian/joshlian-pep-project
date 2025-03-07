package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    public SocialMediaController()
    {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /*
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);

        app.post("/messages", this::createNewMessage);
        app.get("/messages", this::getAllTheMessage);
        app.get("/messages/{message_id}", this::getMessageByUsingID);
        app.get("/accounts/{account_id}/messages", this::getALLMessageByUserID);
        app.delete("/messages/{message_id}", this::deleteTheMessage);
        app.patch("/messages/{message_id}", this::updateTheMessage);

        return app;
    }

    /*
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */

    /* registering users */
    private void registerUser(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);

        Account createdAccount = accountService.register(account);

        if (createdAccount == null) 
        {
            ctx.status(400);
        } else 
        {
            ctx.json(createdAccount);
        }
    }

    /* login users */
    private void loginUser(Context ctx) {
        Account loginRequest = ctx.bodyAsClass(Account.class);
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (account == null) 
        {
            ctx.status(401);
        } else 
        {
            ctx.json(account);
        }
    }

    /* create new message */
    private void createNewMessage(Context ctx)
    {
        Message messages = ctx.bodyAsClass(Message.class);
        Message message = messageService.createMessage(messages);

        if (message == null)
        {
            ctx.status(400);
        }
        else
        {
            ctx.json(message);
        }
    }

    /* get all message */
    private void getAllTheMessage(Context ctx)
    {
        List<Message> message = messageService.getAllMessage();

        if (message == null)
        {
            ctx.status(400);
        }
        else
        {
            ctx.json(message);
        }
    }

     /* get message by ID */
     private void getMessageByUsingID(Context ctx)
     {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
    
        Message message = messageService.getMessageById(messageId);
        if(message == null)
        {
            ctx.status(200);
        }
        else
        {
            ctx.json(message);
        }
    }

    /* get ALL message by User ID */
    private void getALLMessageByUserID(Context ctx)
    {
       int accountId = Integer.parseInt(ctx.pathParam("account_id"));
   
       List<Message> message = messageService.getMessageByUser(accountId);
       if(message == null)
       {
           ctx.status(200);
       }
       else
       {
           ctx.json(message);
       }
    }

    /* Delete the message */
    private void deleteTheMessage(Context ctx)
    {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(messageId);
    
        if (message == null) 
        {
            ctx.status(200);
        } 
        else 
        {
            ctx.json(message);
        }
    }    

    /* Update the message */
    private void updateTheMessage(Context ctx)
    {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
  
        String newText = ctx.bodyAsClass(Map.class).get("message_text").toString();

        if (newText.isBlank() || newText.length() > 255) 
        {
            ctx.status(400); 
            return;
        }
        
        Message updatedMessage = messageService.updateMessage(messageId, newText);

        if (updatedMessage == null) 
        {
            ctx.status(400);
        } else {
            ctx.json(updatedMessage); 
        }
    }    
}
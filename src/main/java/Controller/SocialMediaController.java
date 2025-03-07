package Controller;

import Model.Account;
import Service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    public SocialMediaController()
    {
        accountService = new AccountService();
    }

    /*
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);

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
}
package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    /*register account*/
    public Account register (Account account)
    {
        if(account.getUsername().isBlank() || account.getPassword().length() < 4)
        {
            return null;
        }
        if(accountDAO.getAccountByUsername(account.getUsername()) != null)
        {
            return null;
        }
        
        return accountDAO.createAccount(account);
    }

    /*User login*/
    public Account login (String username, String password)
    {
        return accountDAO.getAccountByUsernameAndPassword(username,password);
    }
    
}

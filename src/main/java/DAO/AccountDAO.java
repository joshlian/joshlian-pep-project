package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
public class AccountDAO {
    
    /*Creat new account*/
    public Account createAccount (Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) 
            {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) 
                {
                    int generatedId = rs.getInt(1);
                    return new Account(generatedId, account.getUsername(), account.getPassword());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*Get User by Username*/
    public Account getAccountByUsername (String username)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) 
            {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /*Get username and password*/
    public Account getAccountByUsernameAndPassword (String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

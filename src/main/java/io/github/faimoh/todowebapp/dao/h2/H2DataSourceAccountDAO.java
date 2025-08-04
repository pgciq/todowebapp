/* 
 * Copyright (c) 2020, Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.faimoh.todowebapp.dao.h2;

import io.github.faimoh.todowebapp.model.Account;
import io.github.faimoh.todowebapp.dao.AccountDAO;
import io.github.faimoh.todowebapp.dao.DAOFactory;
import io.github.faimoh.todowebapp.dao.h2.H2DataSourceDAOFactory;
import java.sql.*;
import java.util.*;

/**
 * H2 implementation of AccountDAO for development environment
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
public class H2DataSourceAccountDAO implements AccountDAO {
    
    @Override
    public Account findAccount(int id) {
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accounts WHERE account_id = ?";
        try {
            System.out.println("connection.isClosed():" + connection.isClosed());
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Account account = null;
            if (rs.next()) {
                account = new Account();
                account.setAccountID(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setFirstName(rs.getString("first_name"));
                account.setLastName(rs.getString("last_name"));
                account.setPassword(rs.getString("password"));
                account.setCreatedAt(rs.getTimestamp("created_at"));                
                account.setStatusID(rs.getInt("status_id"));
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }
    
    @Override
    public Account findAccount(String username) {
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accounts WHERE username = ?";
        try {
            System.out.println("connection.isClosed():" + connection.isClosed());
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            Account account = null;
            if (rs.next()) {
                account = new Account();
                account.setAccountID(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setFirstName(rs.getString("first_name"));
                account.setLastName(rs.getString("last_name"));
                account.setPassword(rs.getString("password"));
                account.setCreatedAt(rs.getTimestamp("created_at"));                
                account.setStatusID(rs.getInt("status_id"));
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }

    @Override
    public ArrayList<Account> getAllAccounts() {
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM accounts";
        try {
            System.out.println("connection.isClosed():" + connection.isClosed());
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Account> accountsList = new ArrayList<Account>();
            Account account = null;
            while (rs.next()) {
                account = new Account();
                account.setAccountID(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setFirstName(rs.getString("first_name"));
                account.setLastName(rs.getString("last_name"));
                account.setPassword(rs.getString("password"));
                account.setCreatedAt(rs.getTimestamp("created_at"));                
                account.setStatusID(rs.getInt("status_id"));
                accountsList.add(account);
            }
            return accountsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }

    @Override
    public Boolean insertAccount(Account account) {
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "INSERT INTO accounts (username, first_name, last_name) VALUES (?, ?, ?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getFirstName());
            ps.setString(3, account.getLastName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }

    @Override
    public Boolean resetPassword(Account account) {
        System.out.println(this.getClass().getCanonicalName()+":resetPassword(Account):"+account);
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "UPDATE accounts SET password='password123' WHERE account_id=?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, account.getAccountID());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }
    
    @Override
    public Boolean updateAccount(Account account, boolean resetPassword) {
        return updateAccount(account);
    }
    
    @Override
    public Boolean updateAccount(Account account) {
        System.out.println(this.getClass().getCanonicalName()+":updateAccount(Account):"+account);
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "UPDATE accounts SET first_name=?, last_name=?, status_id=? WHERE account_id=?";      

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(4, account.getAccountID());
            ps.setString(1, account.getFirstName());
            ps.setString(2, account.getLastName());
            ps.setInt(3, account.getStatusID());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }
    
    @Override
    public Boolean changePassword(Account account) {
        System.out.println(this.getClass().getCanonicalName()+":changePassword(account):"+account);
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = h2DSDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "UPDATE accounts SET password=? WHERE account_id=?";      

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(2, account.getAccountID());
            ps.setString(1, account.getPassword());           
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            h2DSDAOFactory.freeConnection(connection);
        }
    }
}

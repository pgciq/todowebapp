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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.github.faimoh.todowebapp.dao.AccountSessionDAO;
import io.github.faimoh.todowebapp.model.AccountSession;

/**
 * H2 implementation of AccountSessionDAO for development environment
 * @author Faisal Ahmed Pasha Mohammed https://github.com/faimoh
 */
public class H2DataSourceAccountSessionDAO implements AccountSessionDAO {

    @Override
    public AccountSession findLastAccountSession(int accountID) {
        H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
        Connection connection = H2DataSourceDAOFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM account_sessions WHERE account_id=? order by session_created desc limit 1 offset 1";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, accountID);
            rs = ps.executeQuery();
            AccountSession accountSession = new AccountSession();;
            while (rs.next()) {
                accountSession.setAccountID(rs.getInt("account_id"));
                accountSession.setSessionID(rs.getString("session_id"));
                accountSession.setSessionCreated(rs.getTimestamp("session_created"));
                accountSession.setSessionEnd(rs.getTimestamp("session_end"));
            }
            return accountSession;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            H2DataSourceDAOFactory.freeConnection(connection);
        }
    }

    @Override
    public Boolean insertAccountSession(AccountSession accountSession) {
        if (accountSession == null) {
            return false;
        } else {
            H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
            Connection connection = H2DataSourceDAOFactory.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String query = "INSERT INTO account_sessions (session_id, account_id, session_created) values (?, ?, ?)";

            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, accountSession.getSessionID());
                ps.setInt(2, accountSession.getAccountID());
                ps.setTimestamp(3, accountSession.getSessionCreated());
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                H2DataSourceDAOFactory.freeConnection(connection);
            }
        }
    }

    @Override
    public Boolean updateAccountSession(AccountSession accountSession) {
        if (accountSession == null) {
            return false;
        } else {
            H2DataSourceDAOFactory h2DSDAOFactory = H2DataSourceDAOFactory.getInstance();
            Connection connection = H2DataSourceDAOFactory.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String query = "UPDATE account_sessions SET session_end=? WHERE session_Id=?";

            try {
                ps = connection.prepareStatement(query);
                ps.setTimestamp(1, accountSession.getSessionEnd());
                ps.setString(2, accountSession.getSessionID());
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                H2DataSourceDAOFactory.freeConnection(connection);
            }
        }
    }

    @Override
    public AccountSession getAccountSession(String sessionID) {
        return new AccountSession();
    }

    @Override
    public ArrayList<AccountSession> getAllAccountSessions(int accountID) {
        return new ArrayList();
    }
}

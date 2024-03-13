package org.murugappan.DAO;

import java.sql.SQLException;

public interface UserCredentials {
    void createUserCredentials(String name, String password, String roll) throws SQLException;
    void fetchRole(String name, String password) throws SQLException;
}

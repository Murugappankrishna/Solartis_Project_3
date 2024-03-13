package org.murugappan.DAO;
import org.murugappan.repo.JDBC;

import java.sql.*;

public class UserCredentialsImpl implements UserCredentials {
    JDBC jdbc=new JDBC();
    Connection con= jdbc.establishConnection();


    @Override
    public void createUserCredentials(String name, String password, String roll) {
        PreparedStatement preparedStatement;
        try {
        	
            preparedStatement = con.prepareStatement("INSERT INTO user_Credentials (username, password, role) VALUES (?,?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,roll);
            int rowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }
        finally {

            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void fetchRole(String name, String password) {
        PreparedStatement preparedStatement = null;
        try {
        	
            preparedStatement = con.prepareStatement("select role from user_Credentials where username= ? AND password = ?");
        
            preparedStatement.setString(1,name);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()) {
            	
                System.out.println(rs.getString(1));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {

            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Admin;

/**
 *
 * @author Mateus de Morais
 */
public class AdminDAO {
    
    public void create(Admin admin) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO admin (username, password) VALUES (?, ?)");
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
    
    public List<Admin> read() {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Admin> admins;
        admins = new ArrayList<>();
        
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM admin");
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                Admin admin = new Admin();
                
                admin.setId(resultSet.getInt("id"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admins.add(admin);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TenantDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement, resultSet);
        }
        
        return admins;
    }
}

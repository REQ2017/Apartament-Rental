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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Apartment;

/**
 *
 * @author Mateus de Morais
 */
public class ApartmentDAO {
    
    public void create(Apartment apartment) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO apartment (building, number, comments) VALUES (?, ?, ?)");
            preparedStatement.setString(1, apartment.getBuilding());
            preparedStatement.setInt(2, apartment.getNumber());
            preparedStatement.setString(3, apartment.getComments());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
    
    public List<Apartment> read() {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Apartment> apartments;
        apartments = new ArrayList<>();
        
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM apartment");
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                Apartment apartment = new Apartment();
                
                apartment.setId(resultSet.getInt("id"));
                apartment.setBuilding(resultSet.getString("building"));
                apartment.setNumber(resultSet.getInt("number"));
                apartment.setComments(resultSet.getString("comments"));
                apartments.add(apartment);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement, resultSet);
        }
        
        if (apartments.size() > 0) {
            Collections.sort(apartments, new Comparator<Apartment>() {
                @Override
                public int compare(final Apartment object1, final Apartment object2) {
                    return (Integer.toString(object1.getNumber())).compareTo(Integer.toString(object2.getNumber()));
                }
            });
          }
        
        return apartments;
    }
    
    public void update(Apartment apartment) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("UPDATE apartment SET building = ?, number = ?, comments = ? WHERE id = ?");
            preparedStatement.setString(1, apartment.getBuilding());
            preparedStatement.setInt(2, apartment.getNumber());
            preparedStatement.setString(3, apartment.getComments());
            preparedStatement.setInt(4, apartment.getId());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
    public void delete(Apartment apartment) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM apartment WHERE id = ?");
            preparedStatement.setInt(1, apartment.getId());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
}

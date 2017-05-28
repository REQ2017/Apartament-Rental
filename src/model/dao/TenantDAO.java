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
import model.bean.Tenant;

/**
 *
 * @author Mateus de Morais
 */
public class TenantDAO {
    
    public void create(Tenant tenant) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO tenant (name, cpf, rg, birthday, phone, landline, apartment) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, tenant.getName());
            preparedStatement.setString(2, tenant.getCpf());
            preparedStatement.setString(3, tenant.getRg());
            preparedStatement.setDate(4, tenant.getBirthday());
            preparedStatement.setString(5, tenant.getPhone());
            preparedStatement.setString(6, tenant.getLandline());
            preparedStatement.setInt(7, tenant.getApartment());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
    
    public List<Tenant> read() {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        List<Tenant> tenants;
        tenants = new ArrayList<>();
        
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM tenant");
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                Tenant tenant = new Tenant();
                
                tenant.setId(resultSet.getInt("id"));
                tenant.setName(resultSet.getString("name"));
                tenant.setCpf(resultSet.getString("cpf"));
                tenant.setRg(resultSet.getString("rg"));
                tenant.setBirthday(resultSet.getDate("birthday"));
                tenant.setPhone(resultSet.getString("phone"));
                tenant.setLandline(resultSet.getString("landline"));
                tenant.setApartment(resultSet.getInt("apartment"));
                tenants.add(tenant);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TenantDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement, resultSet);
        }
        
        if (tenants.size() > 0) {
            Collections.sort(tenants, new Comparator<Tenant>() {
                @Override
                public int compare(final Tenant object1, final Tenant object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
          }
        
        return tenants;
    }
    
    public void update(Tenant tenant) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("UPDATE tenant SET name = ?, cpf = ?, rg = ?, birthday = ?, phone = ?, landline = ?, apartment = ? WHERE id = ?");
            preparedStatement.setString(1, tenant.getName());
            preparedStatement.setString(2, tenant.getCpf());
            preparedStatement.setString(3, tenant.getRg());
            preparedStatement.setDate(4, tenant.getBirthday());
            preparedStatement.setString(5, tenant.getPhone());
            preparedStatement.setString(6, tenant.getLandline());
            preparedStatement.setInt(7, tenant.getApartment());
            preparedStatement.setInt(8, tenant.getId());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
    
    public void delete(Tenant tenant) {
        
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM tenant WHERE id = ?");
            preparedStatement.setInt(1, tenant.getId());
            
            preparedStatement.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, preparedStatement);
        }
    }
}

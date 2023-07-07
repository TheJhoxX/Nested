/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nested.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import nested.modelo.Usuario;

/**
 *
 * @author zen1xx
 */
public class SolicitudDB {
    
    
    public static void insertSolicitud(int idU1, int idU2) throws SQLException, IOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO Solicitud (idU1, idU2) VALUES (?,?);";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idU1);
            ps.setInt(2, idU2);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Integer> getSolicitudesById(int id){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null; 
        ArrayList<Integer> solicitudes = new ArrayList<>();
        String query = "SELECT s.idU1 FROM Solicitud S WHERE s.idU2 = ?";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                solicitudes.add(rs.getInt(1));
            }
            ps.close();
            pool.freeConnection(conn);
            return solicitudes;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static void deleteSolicitud(int idU1, int idU2){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM Solicitud WHERE idU1 = ? AND idU2 = ?;";
        try{
            ps = conn.prepareStatement(query);
            ps.setInt(1, idU1);
            ps.setInt(2, idU2);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

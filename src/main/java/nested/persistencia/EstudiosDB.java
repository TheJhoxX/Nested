/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nested.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jacobo M
 */
public class EstudiosDB {
    
    public static ArrayList<String> getUniversidades() throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        String query = "SELECT * FROM Estudios";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> estudios = new ArrayList();
        try{
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                estudios.add(rs.getString(1));
            }
            rs.close();
            ps.close();
            pool.freeConnection(conn);
            return estudios;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}

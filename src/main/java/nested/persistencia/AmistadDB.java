package nested.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import nested.modelo.Usuario;

/**
 *
 * @author zen1xx
 */
public class AmistadDB {
    
    private static final String CONSULTAR_AMISTAD = "SELECT * FROM Amistad "
            + "WHERE (idU1 = ? AND idU2 = ?) OR (idU1 = ? AND idU2 = ?)";
    
    public static ArrayList<Usuario> obtenerAmigosUsuario(int id){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Usuario u inner join Amistad a on u.idU=a.idU2 where a.idU1=? and u.idU <> ?";
        ArrayList<Usuario> listaAmigos=new ArrayList<Usuario>();
        
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ps.setInt(2, id);
            rs = ps.executeQuery();
            while(rs.next()){
                
                Usuario user = new Usuario();
                user.setId(rs.getShort(1));
                user.setNombre(rs.getString(2));
                user.setApellidos(rs.getString(3));
                user.setFechaNacimiento(rs.getObject(4, LocalDate.class));
                user.setCorreo(rs.getString(5));
                user.setUniversidad(rs.getString(6));
                user.setGrado(rs.getString(7));
                user.setInstagram(rs.getString(8));
                user.setArroba(rs.getString(9));
                user.setSexo(rs.getString(10));
                user.setContrasena(rs.getString(11));
                listaAmigos.add(user);

            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return listaAmigos;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean sonAmigos(int idU1,int idU2){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = CONSULTAR_AMISTAD;
        boolean sonAmigos = false;
        
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, idU1);
            ps.setInt(2, idU2);
            ps.setInt(3, idU2);
            ps.setInt(4, idU1);
            

            rs = ps.executeQuery();
            if(rs.next()){
                sonAmigos = true;

            }
            
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sonAmigos;
    }
    
    public static void doubleInsert(int idU1, int idU2){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        PreparedStatement ps = null;
        String query1 = "INSERT INTO Amistad(idU1, idU2) VALUES (?,?);";
        String query2 = "INSERT INTO Amistad(idU1, idU2) VALUES (?,?);";
        try{
            ps = conn.prepareStatement(query1);
            ps.setInt(1,idU1);
            ps.setInt(2, idU2);
            ps.executeUpdate();
            ps = conn.prepareStatement(query2);
            ps.setInt(1, idU2);
            ps.setInt(2, idU1);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
 
}

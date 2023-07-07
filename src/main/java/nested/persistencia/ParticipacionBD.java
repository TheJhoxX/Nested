package nested.persistencia;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import nested.modelo.Usuario;

/**
 * @author thejhoxx
 */

public class ParticipacionBD {
    
    public static final String INCLUIR_PARTICIPACION = "INSERT INTO Participacion"
            + " (idU,idN) VALUES (?,?)";
    
    public static final String OBTENER_PARTICIPANTES_NEST = "SELECT * FROM Participacion p \n"
            + " INNER JOIN Nest n ON p.idN = n.idN"
            + " where n.idN=?";
    public static final String OBTENER_PARTICIPANTES= "Select * From  Participacion p inner join Usuario u where p.idU=u.idU";
    
    public static final String ELIMINAR_PARTICIPACION = "DELETE FROM Participacion" 
            + " WHERE idU=? AND idN=?";
    
    public static final String CONSULTAR_PARTICIPACION = "SELECT * FROM Participacion"
            + " WHERE idU=? AND idN=?";
    
    private static final String ELIMINAR_PARTICIPACIONES = "DELETE FROM Participacion"
            + " WHERE idN=?";
    
    public static void insertarParticipacion(int idUsuario, int idN){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
                String query = INCLUIR_PARTICIPACION;

        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idN);
            ps.executeQuery();
            
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            pool.freeConnection(connection);
            e.printStackTrace();
        }
    }
    
    /*
    Función que retorna el número de personas apuntadas a un Nest
    */
    public static int contarParticipantes(int idN) throws IOException, FileNotFoundException{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            String query = OBTENER_PARTICIPANTES_NEST;
            int contador = 0;
            
            
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idN);
                ResultSet result = ps.executeQuery();
                
                while(result.next()){
                    contador++;
                }
                
                connection.close();
                
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            
            
            
            
            return contador;
    }
    
     public static ArrayList<Usuario> ObtenerParticipantes(int idN) throws IOException, FileNotFoundException{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            String query = OBTENER_PARTICIPANTES_NEST;
            ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
            
            
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idN);
                ResultSet rs = ps.executeQuery();
                
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
                    listaParticipantes.add(user);
                }
                
                rs.close();
                ps.close();
                pool.freeConnection(connection);
                return listaParticipantes;
                
            }
            catch (SQLException e){
                e.printStackTrace();
                return null;
            }
            
     }
            
     public static ListMultimap<Integer, Usuario> ObtenerParticipantesTotales() throws IOException, FileNotFoundException{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            String query = OBTENER_PARTICIPANTES;
            ListMultimap<Integer, Usuario> multiMap = ArrayListMultimap.create();
            
            try {
                ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next()){
                    Usuario user = new Usuario();
                    int idN = rs.getInt(2);
                    user.setId(rs.getShort(4));
                    user.setNombre(rs.getString(5));
                    user.setApellidos(rs.getString(6));
                    user.setFechaNacimiento(rs.getObject(7, LocalDate.class));
                    user.setCorreo(rs.getString(8));
                    user.setUniversidad(rs.getString(9));
                    user.setGrado(rs.getString(10));
                    user.setInstagram(rs.getString(11));
                    user.setArroba(rs.getString(12));
                    user.setSexo(rs.getString(13));
                    user.setContrasena(rs.getString(14));
                    multiMap.put(idN,user);
                    
                }
                
                rs.close();
                ps.close();
                pool.freeConnection(connection);
                return multiMap;
                
            }
            catch (SQLException e){
                e.printStackTrace();
                return null;
            }     
        
    }
    
    public static void eliminarParticipacion(int idUsuario, int idN){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
                String query = ELIMINAR_PARTICIPACION;

        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idN);
            ps.executeQuery();
            
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            pool.freeConnection(connection);
            e.printStackTrace();
        }
    }
    
    public static boolean consultarParticipacion(int idUsuario, int idN){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
                String query = CONSULTAR_PARTICIPACION;
        boolean participacion = false;

        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idN);
            ResultSet result = ps.executeQuery();
            
            if(result.next()){
                participacion = true;
            }
            else {
                participacion = false;
            }
            
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            pool.freeConnection(connection);
            e.printStackTrace();
        }
        
        return participacion;
    }
    
    public static void actualizarVotado(int idU, int idN) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE Participacion SET votado = true WHERE idU = ? AND idN = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idU);
            ps.setInt(2, idN);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean consultarVotado(int idU, int idN) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT votado FROM Participacion WHERE idU = ? AND idN = ?";
        boolean votado = false;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idU);
            ps.setInt(2, idN);
            rs = ps.executeQuery();
            if (rs.next()) {
                votado = rs.getBoolean("votado");
            }
            ps.close();
            pool.freeConnection(connection);
            return votado;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votado;
    }
    
    public static boolean eliminarParticipaciones(int idN){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
                String query = ELIMINAR_PARTICIPACIONES;
        boolean participacion = false;

        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idN);
            ps.executeQuery();
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            pool.freeConnection(connection);
            e.printStackTrace();
        }
        
        return participacion;
    }
}

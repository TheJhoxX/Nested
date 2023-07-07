package nested.persistencia;

import nested.modelo.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;

/**
 *
 * @author Jacobo M
 */
public class UsuarioDB {
    
    private static final String GET_ID_USUARIO = "SELECT idU FROM Usuario WHERE arroba=?";
    
    public static int insert(Usuario user, ServletContext context) throws SQLException, IOException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "INSERT INTO Usuario (nombre,apellidos,fechaNacimiento,correo,universidad,grado,instagram,arroba,sexo,password,pfp) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        try{
            ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellidos());
            ps.setDate(3, Date.valueOf(user.getFechaNacimiento()));
            ps.setString(4, user.getCorreo());
            ps.setString(5, user.getUniversidad());
            ps.setString(6,user.getGrado());
            ps.setString(7,user.getInstagram());
            ps.setString(8, user.getArroba());
            ps.setString(9, user.getSexo());
            ps.setString(10, user.getContrasena());
            
            /* Se usa una imagen genérica para registrar al usuario,
            una vez esté registrado ya podrá modificarla a su gusto
            */
            File imagenGenerica = new File(context.getRealPath("/imagenes") + "/foto-perfil-generica.png");              
            FileInputStream fis = new FileInputStream(imagenGenerica);
            ps.setBlob(11, fis);
                
            
            
            int res = 0;
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                res = rs.getInt(1);
            }
            ps.close();
            pool.freeConnection(connection);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public static boolean loggedCorreo(String correo){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT correo FROM Usuario WHERE correo = ?";
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            boolean res = rs.next();
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean loggedArroba(String arroba){
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT arroba FROM Usuario WHERE arroba = ?";
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, arroba);
            rs = ps.executeQuery();
            boolean res = rs.next();
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return res;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static Usuario selectUser(String correo){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Usuario WHERE correo = ?";
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            Usuario user = null;
            if(rs.next()){
                user = new Usuario();
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
                user.setReputacion(rs.getInt(13));

            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static ArrayList<Usuario>  selectUserById(int id){
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
                user.setReputacion(rs.getInt(13));
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
    
    public static void getImagen(int idUsuario, OutputStream respuesta, ServletContext context){
        
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement statement = null;
            statement = connection.prepareStatement(
                    "SELECT pfp FROM Usuario WHERE idU=?");
            statement.setInt(1, idUsuario);
            
            ResultSet result = statement.executeQuery();
            if (result.next()){
                Blob blob = result.getBlob("pfp");
                if (!result.wasNull() && blob.length() > 1) {
                    InputStream imagen = blob.getBinaryStream();
                    byte[] buffer = new byte[1000];
                    int len = imagen.read(buffer);
                    while (len != -1) {
                        respuesta.write(buffer, 0, len);
                        len = imagen.read(buffer);
                    }
                    imagen.close(); 
                }else {
                    File imagenGenericaFile = new File(context.getRealPath("/imagenes") + "/foto-perfil-generica.png");
                    InputStream imagenGenerica = new FileInputStream(imagenGenericaFile);
                    byte[] buffer = new byte[1000];
                    int len = imagenGenerica.read(buffer);
                    while (len != -1) {
                        respuesta.write(buffer, 0, len);
                        len = imagenGenerica.read(buffer);
                    }
                    imagenGenerica.close();
                }
                
            }
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void update(Usuario user) throws SQLException, IOException{
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = pool.getConnection();
    PreparedStatement ps = null;
    String query = "UPDATE Usuario SET nombre = ?, apellidos = ?, fechaNacimiento = ?, universidad = ?, grado = ?, instagram = ?, arroba = ?, sexo = ?, pfp= ? WHERE idU = ?";
    try{
        if(user.getPfp()== null){
            query = "UPDATE Usuario SET nombre = ?, apellidos = ?, fechaNacimiento = ?, universidad = ?, grado = ?, instagram = ?, arroba = ?, sexo = ? WHERE idU = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(9, user.getId());
        }else{
            ps = connection.prepareStatement(query);
            ps.setBlob(9, user.getPfp().getInputStream());
            ps.setInt(10, user.getId());
        }
        ps.setString(1, user.getNombre());
        ps.setString(2, user.getApellidos());
        ps.setDate(3, Date.valueOf(user.getFechaNacimiento()));
        ps.setString(4, user.getUniversidad());
        ps.setString(5,user.getGrado());
        ps.setString(6,user.getInstagram());
        ps.setString(7, user.getArroba());
        ps.setString(8, user.getSexo());
        
        ps.executeUpdate();
        System.out.println(user.getId());
        ps.close();
        pool.freeConnection(connection);
        
    }catch(SQLException e){
        e.printStackTrace();
    }
}
    public static Usuario selectUserID(int idUser){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Usuario WHERE idU = ?";
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();
            Usuario user = null;
            if(rs.next()){
                user = new Usuario();
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
                user.setReputacion(rs.getInt(13));

            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static ArrayList<Usuario> getUsers(int idU){
        ArrayList<Usuario> users = new ArrayList<> ();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Usuario WHERE idU <> ? AND idU NOT IN ("
                + "SELECT idU2 FROM Solicitud WHERE idU1= ? )"
                + "AND idU NOT IN ("
                + "SELECT idU2 FROM Amistad WHERE idU1 = ? )"
                + "AND idU NOT IN ("
                + "SELECT idU1 FROM Amistad WHERE idU2 = ? )";
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, idU);
            ps.setInt(2, idU);
            ps.setInt(3, idU);
            ps.setInt(4, idU);
            rs = ps.executeQuery();
            Usuario user = null;
            while(rs.next()){
                user = new Usuario();
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
                user.setReputacion(13);
                users.add(user);
            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static void updateReputacion(int idUser, int value){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE Usuario SET reputacion = ? WHERE idU = ?";
        try{
            int reputacionActual = obtenerReputacion(connection, idUser);
            int nuevaReputacion = (value == 1) ? reputacionActual + 1 : reputacionActual - 1;
            ps = connection.prepareStatement(query);
            ps.setInt(1, nuevaReputacion);
            ps.setInt(2, idUser);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private static int obtenerReputacion(Connection connection, int idUser){
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT reputacion FROM Usuario WHERE idU = ?";
        int reputacion = 0;
        try{
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUser);
            rs = ps.executeQuery();
            if(rs.next()){
                reputacion = rs.getInt("reputacion");
            }
            rs.close();
            ps.close();
            return reputacion;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public static int getIdUsuario(String arroba){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = GET_ID_USUARIO;
        int idUsuario = 0;
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, arroba);
            rs = ps.executeQuery();
            if(rs.next()){
                idUsuario = rs.getInt("idU");

            }
            rs.close();
            ps.close();
            pool.freeConnection(connection);
            return idUsuario;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void changePswd(String correo, String pswd){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE Usuario SET password = ? WHERE correo = ?";
        
        try{
            ps = connection.prepareStatement(query);
            ps.setString(1, pswd);
            ps.setString(2,correo);
            ps.executeUpdate();
            ps.close();
            pool.freeConnection(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

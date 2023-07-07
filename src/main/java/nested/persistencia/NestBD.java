package nested.persistencia;
import java.io.*;
import java.sql.*;
import nested.modelo.*;
import java.util.ArrayList;
import javax.servlet.ServletContext;

public class NestBD {
    
    private static final String PUBLICAR_NEST = "INSERT INTO Nest "
            + "(idOrganizador,nombre,ubicacion,descripcion,fechaRealizacion,"
            + "fechaFinalizacion,limitePersonas,publico,imagenFondo) VALUES (?,?,?,?,?,?,?,?,?)";
    
    private static final String OBTENER_PARTICIPACIONES_USUARIO = 
            "SELECT * FROM Nest n\n" +
            "INNER JOIN PARTICIPACION p ON n.idN = p.idN"
            + " where p.idU=?";
    
    private static final String OBTENER_IMAGEN_FONDO_NEST = "SELECT imagenFondo FROM Nest WHERE idN=?";
    
    private static final String OBTENER_TODOS_LOS_NEST = "SELECT * FROM Nest";
    
    private static final String ELIMINAR_NEST = "DELETE FROM Nest WHERE idN=?";

    
    
    
	public static int insert(Nest nest, ServletContext context) throws IOException, FileNotFoundException{
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		PreparedStatement ps = null;
		String query = PUBLICAR_NEST;
        
		
		try {
			ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, nest.getIdOrganizador());
			ps.setString(2, nest.getNombre());
			ps.setString(3, nest.getUbicacion());
			ps.setString(4, nest.getDescripcion());
                        System.out.println(nest.getFechaDeRealizacion());
			ps.setTimestamp(5, nest.getFechaDeRealizacion());
                        ps.setTimestamp(6, nest.getFechaDeFinalizacion());
			ps.setInt(7, nest.getLimitePersonas());
			ps.setBoolean(8, nest.getPublico());
                        
                        if (nest.getImagenFondo() != null){
                            ps.setBlob(9, nest.getImagenFondo().getInputStream());
                        }
                        else {
                            /* Se usa una imagen genérica para registrar el nest,
                            una vez publicado ya no se podrá modificar la imagen
                            */
                            File imagenGenerica = new File(context.getRealPath("/imagenes") + "/fondo-nest-generico.png");              
                            FileInputStream fis = new FileInputStream(imagenGenerica);
                            ps.setBlob(9, fis);
                        }
                            
                        
			int res = 0;
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				res = rs.getInt(1);
			}
			ps.close();
			pool.freeConnection(connection);
			return res;
		}

		catch (SQLException e) {
			e.printStackTrace();
                        pool.freeConnection(connection);
			return 0;
		}
	}
        
        /**
         * Funcion que retorna los Nests a los que esta apuntado el usuario
         * @param idUsuario identificador del usuario
         * @return listado de los nests
         * @throws IOException
         * @throws FileNotFoundException 
         */
        public static ArrayList<Nest> getNestsUsuario(int idUsuario) throws IOException, FileNotFoundException{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            String query = OBTENER_PARTICIPACIONES_USUARIO;
            ArrayList<Nest> nestsUsuario = new ArrayList<Nest>();
            
            
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idUsuario);
                ResultSet result = ps.executeQuery();
                
                while(result.next()){
                    
                    int idN = result.getInt("idN");
                    int idOrganizador = result.getInt("idOrganizador");
                    String nombre =  result.getString("nombre");
                    String ubicacion = result.getString("ubicacion");
                    String descripcion = result.getString("descripcion");
                    Timestamp fechaRealizacion = result.getTimestamp("fechaRealizacion");
                    int limitePersonas = result.getInt("limitePersonas");
                    boolean publico = result.getBoolean("publico");
                    InputStream imagenFondo = result.getBlob("imagenFondo").getBinaryStream();
                    imagenFondo.close();
                    Nest nest = new Nest();
                    nest.setID(idN);
                    nest.setIdOrganizador(idOrganizador);
                    nest.setNombre(nombre);
                    nest.setUbicacion(ubicacion);
                    nest.setDescripcion(descripcion);
                    nest.setFechaRealizacion(fechaRealizacion);
                    nest.setLimitePersonas(limitePersonas);
                    nest.setPublico(publico);
                    nestsUsuario.add(nest);
                }
                
                connection.close();
                
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            
            
            
            return nestsUsuario;
        }
        
    public static void getImagen(int idNest, OutputStream respuesta){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = OBTENER_IMAGEN_FONDO_NEST;
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idNest);
            ResultSet result = ps.executeQuery();
            
            if (result.next()){
                Blob blob = result.getBlob("imagenFondo");
                if (!result.wasNull() && blob.length() > 1) {
                    InputStream imagen = blob.getBinaryStream();
                    byte[] buffer = new byte[1000];
                    int len = imagen.read(buffer);
                    while (len != -1) {
                        respuesta.write(buffer, 0, len);
                        len = imagen.read(buffer);
                    }
                    imagen.close(); 
                }
                
            }
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            e.printStackTrace();
            pool.freeConnection(connection);
        }
    }
    
    

    
    
    /*
    Funcion que retorna todos los Nests existentes en la DB
    */
    public static ArrayList<Nest> getNests() throws IOException, FileNotFoundException{
            ConnectionPool pool = ConnectionPool.getInstance();
            Connection connection = pool.getConnection();
            PreparedStatement ps = null;
            String query = OBTENER_TODOS_LOS_NEST;
            ArrayList<Nest> nestsUsuario = new ArrayList<Nest>();
            
            
            try {
                ps = connection.prepareStatement(query);
                ResultSet result = ps.executeQuery();
                
                while(result.next()){
                    
                    int idN = result.getInt("idN");
                    int idOrganizador = result.getInt("idOrganizador");
                    String nombre =  result.getString("nombre");
                    String ubicacion = result.getString("ubicacion");
                    String descripcion = result.getString("descripcion");
                    Timestamp fechaRealizacion = result.getTimestamp("fechaRealizacion");
                    Timestamp fechaFinalizacion = result.getTimestamp("fechaFinalizacion");
                    int limitePersonas = result.getInt("limitePersonas");
                    boolean publico = result.getBoolean("publico");
                    InputStream imagenFondo = result.getBlob("imagenFondo").getBinaryStream();
                    imagenFondo.close();
                    Nest nest = new Nest();
                    nest.setID(idN);
                    nest.setIdOrganizador(idOrganizador);
                    nest.setNombre(nombre);
                    nest.setUbicacion(ubicacion);
                    nest.setDescripcion(descripcion);
                    nest.setFechaRealizacion(fechaRealizacion);
                    nest.setFechaFinalizacion(fechaFinalizacion);
                    nest.setLimitePersonas(limitePersonas);
                    nest.setPublico(publico);
                    nestsUsuario.add(nest);
                }
                
                connection.close();
                
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            
            
            
            return nestsUsuario;
        }
    
    public static void eliminarNest(int idNest){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = ELIMINAR_NEST;
        try {
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, idNest);
            ps.executeQuery();
            
            ps.close();
            pool.freeConnection(connection);
        }
        
        catch (Exception e){
            pool.freeConnection(connection);
            e.printStackTrace();
        }
    }

    

}

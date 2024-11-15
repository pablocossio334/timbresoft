package capaPersistencia;
import capaLogica.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class conexionAbase {
	 private static final String URL = "jdbc:mysql://localhost:3306/recreos";
	    private static final String USER = "root";
	    private static final String PASSWORD = "soporte";
	    
	    
	    
	    
	    public static Connection getConection() {
	    	try {
	            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("Conexión exitosa a la base de datos");
	            return connection;
	            
	        } catch (SQLException e) {
	            System.out.println("Error al conectar a la base de datos");
	            e.printStackTrace();
	            return null;
	        }
	    	
	    	
	    	
	    	
	    }
	    public boolean CargarEvento(LocalTime hora,int duracion) {
	    	Connection conexion=getConection();
	    	//INSERT INTO tiempos(horaEntrada,durTimbre) values ('22:13',5);
	    	Time sqlTime=Time.valueOf(hora);
	    	String query = "INSERT INTO tiempos(horaEntrada, durTimbre) VALUES (?, ?)";
	    	try {
	            PreparedStatement preparedStatement = conexion.prepareStatement(query);
	            // Establece los valores de los parámetros
	            preparedStatement.setTime(1, sqlTime);
	            preparedStatement.setInt(2, duracion);
	            
	            // Ejecuta la consulta
	            int filasInsertadas = preparedStatement.executeUpdate();

	            if (filasInsertadas > 0) {
	                System.out.println("Se agregó el evento.");
	                return true;
	            } else {
	                System.out.println("No se agregó ningún registro.");
	                return false;
	            }
	        } catch (SQLException e) {
	            System.out.println("No se pudo agregar el evento.");
	            
	            e.printStackTrace();
	            return false;
	        }
	    	
	    	
	    	
	    }
	    
	    public static boolean EliminarEvento(LocalTime hora) {
	        Time sqlTime = Time.valueOf(hora);
	        String query = "DELETE FROM tiempos WHERE horaEntrada = ?";
	        Connection conexion = getConection();
	        
	        try {
	            PreparedStatement preparedStatement = conexion.prepareStatement(query);
	            preparedStatement.setTime(1, sqlTime);
	            
	            // Ejecutar la consulta de eliminación
	            int filasEliminadas = preparedStatement.executeUpdate();
	            
	            // Verificar si se eliminó algún registro
	            if (filasEliminadas > 0) {
	                System.out.println("Se eliminó el evento con hora: " + hora);
	                return true;
	            } else {
	                System.out.println("No se encontró ningún evento con la hora especificada.");
	                return false;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	  public static ResultSet proximoEvento() {
		  String query = "SELECT * FROM tiempos " +
                  "WHERE horaEntrada > CURTIME() " +
                  "ORDER BY ABS(TIMESTAMPDIFF(SECOND, CURTIME(), horaEntrada)) ASC " +
                  "LIMIT 1;";
		  Connection conexion=getConection();
		  try {
	    		PreparedStatement preparedStatement = conexion.prepareStatement(query);
	    		System.out.println("se ejecuto la consulta");
	    		ResultSet resultSet = preparedStatement.executeQuery();
	    		System.out.println(query);
	    		
	    		return resultSet;
		  }catch (SQLException e) 
		  {
			  return null;
		  }
		  
	  }
	    
	    
	    public static ArrayList <eventos> cargarEventos(){
	    	ArrayList<eventos> horarios=new ArrayList<eventos>() ;
	    	String query = "SELECT * FROM tiempos ORDER BY horaEntrada ";


	    	Connection conexion=getConection();
	    	try {
	    		PreparedStatement preparedStatement = conexion.prepareStatement(query);
	    		System.out.println("se ejecuto la consulta");
	    		ResultSet resultSet = preparedStatement.executeQuery();
	    		while(resultSet.next()) {
	    			
	    			eventos e=new eventos();
	    			e.setHoraEvento(resultSet.getTime("horaEntrada").toLocalTime());
	    			e.setDuracion(resultSet.getInt("durTimbre"));
	    			horarios.add(e);
	    			
	    			
	    		}
	    		return horarios;
	    	} catch (SQLException e) {
	    		// TODO Bloque catch generado automáticamente
	    		e.printStackTrace();
	    		return null;
	    	}
	    }


	    public static void main(String[] args) {
	        ResultSet resultSet = proximoEvento();
	        
	        try {
	            if (resultSet != null && resultSet.next()) {
	                // Suponiendo que `horaEntrada` es de tipo TIME y `durTimbre` es de tipo INT en la tabla
	                Time horaEntrada = resultSet.getTime("horaEntrada");
	                int durTimbre = resultSet.getInt("durTimbre");
	                
	                System.out.println("Hora de entrada más cercana: " + horaEntrada);
	                System.out.println("Duración del timbre: " + durTimbre);
	            } else {
	                System.out.println("No se encontró ningún registro.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


}


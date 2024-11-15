package capaLogica;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import capaPersistencia.conexionAbase;
import java.util.ArrayList;


public class eventos {

	private LocalTime horaEvento;
	public LocalTime getHoraEvento() {
		return horaEvento;
	}
	public void setHoraEvento(LocalTime horaEvento) {
		this.horaEvento = horaEvento;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	private int duracion;
	public eventos() {
		
	}
	public eventos obtenerProximoEvento() {
		ResultSet proximo=conexionAbase.proximoEvento();
		eventos proximoEvento=new eventos();
		
		try {
			while(proximo.next()) {
			proximoEvento.setHoraEvento(proximo.getTime("horaEntrada").toLocalTime());
			proximoEvento.setDuracion(proximo.getInt("durTimbre"));
			return proximoEvento;
			}
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			return null;
		}
		return proximoEvento;
		
		
		
		
	}
	public ArrayList<eventos> obtenerEventosBaseDato(){
		ArrayList<eventos> events=conexionAbase.cargarEventos();
		return events;
	}
	
	public boolean agregarEventoAbase() {
		boolean seAgrego=new conexionAbase().CargarEvento(horaEvento, duracion);
		return seAgrego;
	
	}
	public boolean eliminarEventoBase() {
	boolean consulta=conexionAbase.EliminarEvento(horaEvento);
	return consulta;
	}
	public static void main(String[] args) {
		try {
			eventos t=new eventos().obtenerProximoEvento();
			System.out.println(t.getHoraEvento());
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
}

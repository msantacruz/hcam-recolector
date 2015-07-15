package ec.gob.iess.casamaquinas.recolector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManejadorPresionConstante {

	public void registrarEstadoBombasAlarma(boolean bomba1, boolean bomba2, boolean bomba3, boolean alarma) {
		Connection conn = null;
		
		try {
			conn = GestorConexion.obtenerConexion();
			PreparedStatement ps= conn.prepareStatement("insert into estado_bombas(id,fecha,bomba1,bomba2,bomba3,alarma)"
					+ " values(nextval('seq_estado_bombas'), current_timestamp, ?, ?, ?, ?)");
			ps.setBoolean(1, bomba1);
			ps.setBoolean(2, bomba2);
			ps.setBoolean(3, bomba3);
			ps.setBoolean(4, alarma);
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void registrarPresionFlujo(String presion, String flujo) {
		Connection conn = null;
		
		try {
			conn = GestorConexion.obtenerConexion();
			PreparedStatement ps= conn.prepareStatement("insert into presion_flujo(id,fecha,presion,flujo)"
					+ " values(nextval('seq_presion_flujo'), current_timestamp, ?, ?)");
			ps.setString(1, presion.trim());
			ps.setString(2, flujo.trim());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

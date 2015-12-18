package ec.gob.iess.casamaquinas.recolector.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;

public class ManejadorMovimientoDiesel {

	public void registrarMovimiento(String tipoEvento, Double cantidad, Double acumulado) {
		Connection conn = null;
		
		try {
			conn = GestorConexion.obtenerConexion();
			PreparedStatement ps= conn.prepareStatement("insert into movimiento_diesel(id,fecha,evento,cantidad,acumulado)"
					+ " values(nextval('seq_movimiento'), current_timestamp, ?, ?, ?)");
			ps.setString(1, tipoEvento);
			ps.setDouble(2, cantidad);
			ps.setDouble(3, acumulado);
			
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

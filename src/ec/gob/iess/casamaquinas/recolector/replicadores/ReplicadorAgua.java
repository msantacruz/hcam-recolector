package ec.gob.iess.casamaquinas.recolector.replicadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionAguaDTO;
import ec.gob.iess.casamaquinas.recolector.http.ManejadorHttp;

public class ReplicadorAgua {

	public static void main(String[] args) throws Exception {		
		ReplicadorAgua replicador =  new ReplicadorAgua();
		do {
			replicador.migrarAgua();
			Thread.sleep(60000);
		} while (true); 
	}

	private void migrarAgua() {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psUpdateEstado = null;
		ResultSet rs = null;

		try {
			List<ReplicacionAguaDTO> resultado = new  ArrayList<ReplicacionAguaDTO>();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from agua where migrado=false");
			rs = ps.executeQuery();
			while (rs.next()) {
				ReplicacionAguaDTO aguaDTO = new ReplicacionAguaDTO();
				aguaDTO.setId(rs.getLong("id"));
				aguaDTO.setFecha(rs.getTimestamp("fecha"));
				aguaDTO.setFlujo(rs.getBigDecimal("flujo"));
				aguaDTO.setPresion(rs.getBigDecimal("presion"));
				aguaDTO.setBomba1(rs.getString("bomba_1"));
				aguaDTO.setBomba2(rs.getString("bomba_2"));
				aguaDTO.setBomba3(rs.getString("bomba_3"));
				aguaDTO.setAlarma(rs.getString("alarma"));
				resultado.add(aguaDTO);
			}
			
			if (resultado.size()>0) {
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				if (manejadorHttp.enviarRegistrosAgua(resultado)) {
					psUpdateEstado = conn.prepareStatement("update agua set migrado = true where id=?");
					for (ReplicacionAguaDTO aguaDTO: resultado) {
						psUpdateEstado.setLong(1, aguaDTO.getId());
						psUpdateEstado.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (psUpdateEstado != null)
					psUpdateEstado.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

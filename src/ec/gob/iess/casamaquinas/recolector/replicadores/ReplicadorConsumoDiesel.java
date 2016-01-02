package ec.gob.iess.casamaquinas.recolector.replicadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionConsumoDieselDTO;
import ec.gob.iess.casamaquinas.recolector.http.ManejadorHttp;

public class ReplicadorConsumoDiesel {

	public static void main(String[] args) throws Exception {		
		ReplicadorConsumoDiesel replicador =  new ReplicadorConsumoDiesel();
		do {
			replicador.migrarConsumoDiesel();
			replicador.migrarConsumoMesDiesel();
			Thread.sleep(3600000);
		} while (true); 
	}

	private void migrarConsumoDiesel() {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psUpdateEstado = null;
		ResultSet rs = null;

		try {
			List<ReplicacionConsumoDieselDTO> resultado = new  ArrayList<ReplicacionConsumoDieselDTO>();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from consumo_diesel where migrado=false order by fecha asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ReplicacionConsumoDieselDTO consumoDieselDTO = new ReplicacionConsumoDieselDTO();
				consumoDieselDTO.setId(rs.getLong("id"));
				consumoDieselDTO.setFecha(rs.getTimestamp("fecha"));
				consumoDieselDTO.setConsumo(rs.getBigDecimal("consumo"));
				resultado.add(consumoDieselDTO);
			}
			
			if (resultado.size()>0) {
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				if (manejadorHttp.enviarRegistrosConsumoDiesel(resultado)) {
					psUpdateEstado = conn.prepareStatement("update consumo_diesel set migrado = true where id=?");
					int cont = 0;
					for (ReplicacionConsumoDieselDTO dieselDTO: resultado) {
						if (resultado.size() > cont) {
							cont++;
							psUpdateEstado.setLong(1, dieselDTO.getId());
							psUpdateEstado.executeUpdate();
						}
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
	
	private void migrarConsumoMesDiesel() {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psUpdateEstado = null;
		ResultSet rs = null;

		try {
			List<ReplicacionConsumoDieselDTO> resultado = new  ArrayList<ReplicacionConsumoDieselDTO>();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from consumo_mes_diesel where migrado=false order by fecha asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ReplicacionConsumoDieselDTO consumoDieselDTO = new ReplicacionConsumoDieselDTO();
				consumoDieselDTO.setId(rs.getLong("id"));
				consumoDieselDTO.setFecha(rs.getTimestamp("fecha"));
				consumoDieselDTO.setConsumo(rs.getBigDecimal("consumo_total_mes"));
				resultado.add(consumoDieselDTO);
			}
			
			if (resultado.size()>0) {
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				if (manejadorHttp.enviarRegistrosConsumoMesDiesel(resultado)) {
					psUpdateEstado = conn.prepareStatement("update consumo_mes_diesel set migrado = true where id=?");
					int cont = 0;
					for (ReplicacionConsumoDieselDTO dieselDTO: resultado) {
						if (resultado.size() > cont) {
							cont++;
							psUpdateEstado.setLong(1, dieselDTO.getId());
							psUpdateEstado.executeUpdate();
						}
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

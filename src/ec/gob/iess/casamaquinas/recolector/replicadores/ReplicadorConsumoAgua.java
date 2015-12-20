package ec.gob.iess.casamaquinas.recolector.replicadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionConsumoAguaDTO;
import ec.gob.iess.casamaquinas.recolector.http.ManejadorHttp;

public class ReplicadorConsumoAgua {

	public static void main(String[] args) throws Exception {		
		ReplicadorConsumoAgua replicador =  new ReplicadorConsumoAgua();
		do {
			replicador.migrarConsumoAgua();
			replicador.migrarConsumoMesAgua();
			Thread.sleep(3600000);
		} while (true); 
	}

	private void migrarConsumoAgua() {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psUpdateEstado = null;
		ResultSet rs = null;

		try {
			List<ReplicacionConsumoAguaDTO> resultado = new  ArrayList<ReplicacionConsumoAguaDTO>();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from consumo_agua where migrado=false order by fecha asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ReplicacionConsumoAguaDTO consumoAguaDTO = new ReplicacionConsumoAguaDTO();
				consumoAguaDTO.setId(rs.getLong("id"));
				consumoAguaDTO.setFecha(rs.getTimestamp("fecha"));
				consumoAguaDTO.setConsumo(rs.getBigDecimal("consumo"));
				resultado.add(consumoAguaDTO);
			}
			
			if (resultado.size()>0) {
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				if (manejadorHttp.enviarRegistrosConsumoAgua(resultado)) {
					psUpdateEstado = conn.prepareStatement("update consumo_agua set migrado = true where id=?");
					int cont = 1;
					for (ReplicacionConsumoAguaDTO aguaDTO: resultado) {
						if (resultado.size() > cont) {
							cont++;
							psUpdateEstado.setLong(1, aguaDTO.getId());
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
	
	private void migrarConsumoMesAgua() {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psUpdateEstado = null;
		ResultSet rs = null;

		try {
			List<ReplicacionConsumoAguaDTO> resultado = new  ArrayList<ReplicacionConsumoAguaDTO>();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from consumo_mes_agua where migrado=false order by fecha asc");
			rs = ps.executeQuery();
			while (rs.next()) {
				ReplicacionConsumoAguaDTO consumoAguaDTO = new ReplicacionConsumoAguaDTO();
				consumoAguaDTO.setId(rs.getLong("id"));
				consumoAguaDTO.setFecha(rs.getTimestamp("fecha"));
				consumoAguaDTO.setConsumo(rs.getBigDecimal("consumo_total_mes"));
				resultado.add(consumoAguaDTO);
			}
			
			if (resultado.size()>0) {
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				if (manejadorHttp.enviarRegistrosConsumoMesAgua(resultado)) {
					psUpdateEstado = conn.prepareStatement("update consumo_mes_agua set migrado = true where id=?");
					int cont = 1;
					for (ReplicacionConsumoAguaDTO aguaDTO: resultado) {
						if (resultado.size() > cont) {
							cont++;
							psUpdateEstado.setLong(1, aguaDTO.getId());
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

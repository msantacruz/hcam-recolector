package ec.gob.iess.casamaquinas.recolector.replicadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.EstadoBombasDTO;
import ec.gob.iess.casamaquinas.recolector.dto.PresionFlujoDTO;
import ec.gob.iess.casamaquinas.recolector.http.ManejadorHttp;

public class ReplicadorPresionFlujoEstadoBombas {

	public static void main(String[] args) throws Exception {		
		ReplicadorPresionFlujoEstadoBombas replicador =  new ReplicadorPresionFlujoEstadoBombas();
		do {
			replicador.migrarPresionFlujo();
			replicador.migrarEstadoBombas();
			Thread.sleep(2000);
		} while (true); 
	}

	private void migrarPresionFlujo() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			PresionFlujoDTO presion = new PresionFlujoDTO();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from presion_flujo order by id desc limit 1");
			rs = ps.executeQuery();
			if (rs.next()) {
				presion.setId(rs.getLong("id"));
				presion.setFecha(rs.getTimestamp("fecha"));
				presion.setFlujo(rs.getString("flujo"));
				presion.setPresion(rs.getString("presion"));
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				manejadorHttp.enviarRegistroPresion(presion);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void migrarEstadoBombas() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			EstadoBombasDTO estadoBombas = new EstadoBombasDTO();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from estado_bombas order by id desc limit 1");
			rs = ps.executeQuery();
			if (rs.next()) {
				estadoBombas.setId(rs.getLong("id"));
				estadoBombas.setFecha(rs.getTimestamp("fecha"));
				estadoBombas.setBomba1(rs.getBoolean("bomba1"));
				estadoBombas.setBomba2(rs.getBoolean("bomba2"));
				estadoBombas.setBomba3(rs.getBoolean("bomba3"));
				estadoBombas.setAlarma(rs.getBoolean("alarma"));
				estadoBombas.setBajaPresion(rs.getBoolean("bajapresion"));
				estadoBombas.setAltaPresion(rs.getBoolean("altapresion"));
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				manejadorHttp.enviarRegistroEstadoBombas(estadoBombas);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

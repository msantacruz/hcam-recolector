package ec.gob.iess.casamaquinas.recolector.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.EstadoBombasDTO;
import ec.gob.iess.casamaquinas.recolector.dto.PresionFlujoDTO;

public class ManejadorPresionConstante {

	public void registrarEstadoBombasAlarma(boolean bomba1, boolean bomba2, boolean bomba3, boolean alarma, boolean bajapresion, boolean altapresion) {
		Connection conn = null;
		
		try {
			conn = GestorConexion.obtenerConexion();
			PreparedStatement ps= conn.prepareStatement("insert into estado_bombas(id,fecha,bomba1,bomba2,bomba3,alarma,bajapresion,altapresion)"
					+ " values(nextval('seq_estado_bombas'), current_timestamp, ?, ?, ?, ?, ?, ?)");
			ps.setBoolean(1, bomba1);
			ps.setBoolean(2, bomba2);
			ps.setBoolean(3, bomba3);
			ps.setBoolean(4, alarma);
			ps.setBoolean(5, bajapresion);
			ps.setBoolean(6, altapresion);
			
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
	
	public List<EstadoBombasDTO> buscarRegistoBombasReplicar() {
		List<EstadoBombasDTO> lista = new ArrayList<EstadoBombasDTO>();
		
		Connection conn = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			conn = GestorConexion.obtenerConexion();
			ps= conn.prepareStatement("select fecha,bomba1,bomba2,bomba3,alarma,bajapresion,altapresion from estado_bombas"
					+ " where replicado = false");
			
			rs = ps.executeQuery();
			while (rs.next()) {
				EstadoBombasDTO estadoBombasDTO = new EstadoBombasDTO();
				estadoBombasDTO.setFecha(rs.getTimestamp("fecha"));
				estadoBombasDTO.setAlarma(rs.getBoolean("alarma"));
				estadoBombasDTO.setAltaPresion(rs.getBoolean("altapresion"));
				estadoBombasDTO.setBajaPresion(rs.getBoolean("bajapresion"));
				estadoBombasDTO.setBomba1(rs.getBoolean("bomba1"));
				estadoBombasDTO.setBomba2(rs.getBoolean("bomba2"));
				estadoBombasDTO.setBomba3(rs.getBoolean("bomba3"));
				lista.add(estadoBombasDTO);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return lista;
	}
	
	public List<PresionFlujoDTO> buscarRegistoPresionFlujoReplicar() {
		List<PresionFlujoDTO> lista = new ArrayList<PresionFlujoDTO>();
		
		Connection conn = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try {
			conn = GestorConexion.obtenerConexion();
			ps= conn.prepareStatement("select fecha,presion,flujo"
					+ " where replicado = false");			
			rs = ps.executeQuery();
			while (rs.next()) {
				PresionFlujoDTO presionFlujoDTO = new PresionFlujoDTO();
				presionFlujoDTO.setFecha(rs.getTimestamp("fecha"));
				presionFlujoDTO.setFlujo(rs.getString("flujo"));
				presionFlujoDTO.setPresion("presion");
				lista.add(presionFlujoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e1) {}
			if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {}
		}
		return lista;
	}
	
	public void limpiarRegistros() {
		Connection conn = null;
		PreparedStatement ps= null;
		PreparedStatement ps1= null;
		try {
			conn = GestorConexion.obtenerConexion();
			ps= conn.prepareStatement("delete from presion_flujo where replicado = true and cons");
			
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

package ec.gob.iess.casamaquinas.recolector.replicadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionDatosDieselDTO;
import ec.gob.iess.casamaquinas.recolector.http.ManejadorHttp;

public class ReplicadorDatosDiesel {

	public static void main(String[] args) throws Exception {		
		ReplicadorDatosDiesel replicador =  new ReplicadorDatosDiesel();
		do {
			replicador.migrarDatosPlcDiesel();
			Thread.sleep(2000);
		} while (true); 
	}

	private void migrarDatosPlcDiesel() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ReplicacionDatosDieselDTO datosDiesel = new ReplicacionDatosDieselDTO();
			
			conn = GestorConexion.obtenerConexion();
			ps = conn.prepareStatement("select * from datos_plc_diesel order by id desc limit 1");
			rs = ps.executeQuery();
			if (rs.next()) {
				datosDiesel.setId(rs.getLong("id"));
				datosDiesel.setFecha(rs.getTimestamp("fecha"));
				datosDiesel.setTemperatura(rs.getInt("temperatura"));
				datosDiesel.setBajo_tanque1(rs.getInt("bajo_tanque1"));
				datosDiesel.setAlto_tanque1(rs.getInt("alto_tanque1"));
				datosDiesel.setBajo_tanque2(rs.getInt("bajo_tanque2"));
				datosDiesel.setAlto_tanque2(rs.getInt("alto_tanque2"));
				datosDiesel.setPulsos_entrada(rs.getDouble("pulsos_entrada"));
				datosDiesel.setBomba_ingreso(rs.getInt("bomba_ingreso"));
				datosDiesel.setFrecuencia_entrada(rs.getInt("frecuencia_entrada"));
				datosDiesel.setBomba_tdiario(rs.getInt("bomba_tdiario"));
				datosDiesel.setGalones_salida(rs.getInt("galones_salida"));
				datosDiesel.setFracc_galonsalida(rs.getInt("fracc_galonsalida"));
				datosDiesel.setFrecuencia_salida(rs.getInt("frecuencia_salida"));
				datosDiesel.setFlujo_salida(rs.getInt("flujo_salida"));
				datosDiesel.setFracc_flujosalida(rs.getInt("fracc_flujosalida"));
				datosDiesel.setGalones_entrada(rs.getInt("galones_entrada"));
				datosDiesel.setFracc_galonentrada(rs.getInt("fracc_galonentrada"));
				datosDiesel.setFlujo_entrada(rs.getInt("flujo_entrada"));
				datosDiesel.setFracc_flujoentrada(rs.getInt("fracc_flujoentrada"));
				datosDiesel.setParo_emergencia(rs.getInt("paro_emergencia"));
				datosDiesel.setInicio_galont1(rs.getInt("inicio_galont1"));
				datosDiesel.setInicio_fraccgalont1(rs.getInt("inicio_fraccgalont1"));
				datosDiesel.setInicio_galont2(rs.getInt("inicio_galont2"));
				datosDiesel.setInicio_fraccgalont2(rs.getInt("inicio_fraccgalont2"));
				datosDiesel.setTotal_galont1(rs.getInt("total_galont1"));
				datosDiesel.setTotal_fraccgalont1(rs.getInt("total_fraccgalont1"));
				datosDiesel.setTotal_galont2(rs.getInt("total_galont2"));
				datosDiesel.setTotal_fraccgalont2(rs.getInt("total_fraccgalont2"));
				datosDiesel.setPedido_tanque(rs.getInt("pedido_tanque"));
				datosDiesel.setTanque_uso(rs.getInt("tanque_uso"));
				datosDiesel.setModo(rs.getInt("modo"));
				datosDiesel.setResetIngreso(rs.getInt("reset_ingreso"));
				datosDiesel.setConsumo(rs.getInt("consumo"));
				datosDiesel.setFraccConsumo(rs.getInt("fracc_consumo"));
				
				ManejadorHttp manejadorHttp = new ManejadorHttp();
				manejadorHttp.enviarRegistroDatosDiesel(datosDiesel);
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
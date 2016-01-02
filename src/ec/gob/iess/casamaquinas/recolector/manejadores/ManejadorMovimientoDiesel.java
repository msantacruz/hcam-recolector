package ec.gob.iess.casamaquinas.recolector.manejadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ec.gob.iess.casamaquinas.recolector.GestorConexion;

public class ManejadorMovimientoDiesel {

	public void registrarMovimiento(Integer temperatura, Integer bajo_tanque1, Integer alto_tanque1, Integer bajo_tanque2, 
			Integer alto_tanque2, Integer pulsos_entrada, Integer bomba_ingreso, Integer frecuencia_entrada, 
			Integer bomba_tdiario,Integer galones_salida, Integer fracc_galonsalida, Integer frecuencia_salida, 
			Integer flujo_salida, Integer fracc_flujosalida,Integer galones_entrada, Integer fracc_galonentrada, 
			Integer flujo_entrada, Integer fracc_flujoentrada, Integer paro_emergencia, Integer inicio_galont1, 
			Integer inicio_fraccgalont1, Integer inicio_galont2, Integer inicio_fraccgalont2, Integer total_galont1, 
			Integer total_fraccgalont1, Integer total_galont2, Integer total_fraccgalont2, Integer pedido_tanque, 
			Integer tanque_uso, Integer modo, Integer reset_ingreso ,Integer consumo, Integer fracc_consumo) {
		
		Connection conn = null;
		
		try {
			conn = GestorConexion.obtenerConexion();
			PreparedStatement ps= conn.prepareStatement("insert into datos_plc_diesel(id,fecha,temperatura,bajo_tanque1,"
					+ "alto_tanque1,bajo_tanque2,alto_tanque2,pulsos_entrada,bomba_ingreso,frecuencia_entrada,"
					+ "bomba_tdiario,galones_salida,fracc_galonsalida,frecuencia_salida,flujo_salida,fracc_flujosalida,"
					+ "galones_entrada,fracc_galonentrada,flujo_entrada,fracc_flujoentrada,paro_emergencia,inicio_galont1,"
					+ "inicio_fraccgalont1,inicio_galont2,inicio_fraccgalont2,total_galont1,total_fraccgalont1,"
					+ "total_galont2,total_fraccgalont2,pedido_tanque,tanque_uso,modo,reset_ingreso,consumo,fracc_consumo)"
					+ " values(nextval('seq_movimiento'), current_timestamp, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
					+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, temperatura);
			ps.setInt(2, bajo_tanque1);
			ps.setInt(3, alto_tanque1);
			ps.setInt(4, bajo_tanque2);
			ps.setInt(5, alto_tanque2);
			ps.setDouble(6, pulsos_entrada);
			ps.setInt(7, bomba_ingreso);
			ps.setInt(8, frecuencia_entrada);
			ps.setInt(9, bomba_tdiario);
			ps.setInt(10, galones_salida);
			ps.setInt(11, fracc_galonsalida);
			ps.setInt(12, frecuencia_salida);
			ps.setInt(13, flujo_salida);
			ps.setInt(14, fracc_flujosalida);
			ps.setInt(15, galones_entrada);
			ps.setInt(16, fracc_galonentrada);
			ps.setInt(17, flujo_entrada);
			ps.setInt(18, fracc_flujoentrada);
			ps.setInt(19, paro_emergencia);
			ps.setInt(20, inicio_galont1);
			ps.setInt(21, inicio_fraccgalont1);
			ps.setInt(22, inicio_galont2);
			ps.setInt(23, inicio_fraccgalont2);
			ps.setInt(24, total_galont1);
			ps.setInt(25, total_fraccgalont1);
			ps.setInt(26, total_galont2);
			ps.setInt(27, total_fraccgalont2);
			ps.setInt(28, pedido_tanque);
			ps.setInt(29, tanque_uso);
			ps.setInt(30, modo);
			ps.setInt(31,  reset_ingreso);
			ps.setInt(32, consumo);
			ps.setInt(33, fracc_consumo);
			
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

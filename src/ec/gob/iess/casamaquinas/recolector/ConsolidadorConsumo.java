package ec.gob.iess.casamaquinas.recolector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ConsolidadorConsumo {

	public static void main(String[] args) {
		ConsolidadorConsumo consolidadorConsumo = new ConsolidadorConsumo();
		do {
			consolidadorConsumo.consolidarConsumo();
			try {
				Thread.sleep(3600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(true);
	}
	
	private void consolidarConsumo() {
		Connection conn = null;
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psUpdateConsumo = null;
		PreparedStatement psSelectDia = null;
		PreparedStatement psSelectMes = null;
		PreparedStatement psUpdateConsumoMes = null;
		PreparedStatement psInsertConsumoMes = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		ResultSet rs2 =null;
		ResultSet rs3 =null;
		ResultSet rs4 =null;
		ResultSet rs5 =null;
		
		Date fechaAnterior = null;
		BigDecimal flujoSuma = new BigDecimal(0);
		int contador = 0;
		try {
			conn = GestorConexion.obtenerConexion();
			
			psInsert = conn
					.prepareStatement("INSERT INTO consumo_agua(id, fecha, consumo)"
							+ "VALUES (nextval('seq_consumo_agua'), ?, ?)");
			
			psUpdateConsumo = conn
					.prepareStatement("UPDATE consumo_agua set consumo = ? where id = ?");
			
			
			psSelectDia = conn
					.prepareStatement("select * from consumo_agua where date_trunc('day', fecha::timestamp) = date_trunc('day', ?::timestamp) ");
			
			psUpdate = conn
					.prepareStatement("UPDATE agua set consolidado = true where date_trunc('hour', fecha::timestamp)"
							+ " = date_trunc('hour', ?::timestamp)");			
			
			ps = conn
					.prepareStatement("select * from agua where fecha < date_trunc('hour', now()::timestamp) "
							+ " and consolidado = false order by fecha");
			
			psSelectMes = conn
					.prepareStatement("select * from consumo_mes_agua where date_trunc('month', fecha::timestamp) = date_trunc('month', ?::timestamp) ");
			
			psUpdateConsumoMes = conn
					.prepareStatement("UPDATE consumo_mes_agua set consumo_total_mes = ? where id = ?");
			
			psInsertConsumoMes = conn
					.prepareStatement("INSERT INTO consumo_mes_agua(id, fecha, consumo_total_mes)"
							+ "VALUES (nextval('seq_consumo_mes_agua'), ?, ?)");
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Date fecha = rs.getTimestamp("fecha");
				if (fechaAnterior == null) {
					contador++;
					flujoSuma = rs.getBigDecimal("flujo");
					fechaAnterior = fecha;
				} else {
					if (mismaHora(fechaAnterior, fecha)) {
						contador++;
						flujoSuma = flujoSuma.add(rs.getBigDecimal("flujo"));
					} else {
						BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
						BigDecimal valor = promFlujo.multiply(new BigDecimal(60));
						
						// Acumula por día
						psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
						rs2 = psSelectDia.executeQuery();
						if (rs2.next()) {
							
							BigDecimal consumo = rs2.getBigDecimal("consumo");
							BigDecimal acumulado = consumo.add(valor);
							psUpdateConsumo.setBigDecimal(1, acumulado);
							psUpdateConsumo.setLong(2, rs2.getLong("id"));
							psUpdateConsumo.execute();
							psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psUpdate.execute();
						} else {
							psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psInsert.setBigDecimal(2, valor);
							psInsert.execute();
							psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psUpdate.execute();	
						}
						
						// Acumula por mes
						psSelectMes.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
						rs4 = psSelectMes.executeQuery();
						if (rs4.next()) {
							BigDecimal consumoTotalMes = rs4.getBigDecimal("consumo_total_mes");
							BigDecimal acumuladoMes = consumoTotalMes.add(valor);
							psUpdateConsumoMes.setBigDecimal(1, acumuladoMes);
							psUpdateConsumoMes.setLong(2, rs4.getLong("id"));
							psUpdateConsumoMes.execute();
						} else {
							psInsertConsumoMes.setTimestamp(1, new Timestamp(redondeoMes(fechaAnterior).getTime()));
							psInsertConsumoMes.setBigDecimal(2, valor);
							psInsertConsumoMes.execute();
						}
						fechaAnterior = fecha;
						contador = 1;
						flujoSuma = rs.getBigDecimal("flujo");
					}	
				}
			}
			if (contador>0) {
				
				BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
				BigDecimal valor = promFlujo.multiply(new BigDecimal(60));
				// Acumula por día
				psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
				rs3 = psSelectDia.executeQuery();
				if (rs3.next()) {
					BigDecimal consumo = rs3.getBigDecimal("consumo");
					BigDecimal acumulado = consumo.add(valor);
					psUpdateConsumo.setBigDecimal(1, acumulado);
					psUpdateConsumo.setLong(2, rs3.getLong("id"));
					psUpdateConsumo.execute();
					psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psUpdate.execute();
				} else {
					psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psInsert.setBigDecimal(2, valor);
					psInsert.execute();
					psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psUpdate.execute();	
				}
				
				// Acumula por mes
				psSelectMes.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
				rs5 = psSelectMes.executeQuery();
				if (rs5.next()) {
					BigDecimal consumoTotalMes = rs5.getBigDecimal("consumo_total_mes");
					BigDecimal acumuladoMes = consumoTotalMes.add(valor);
					psUpdateConsumoMes.setBigDecimal(1, acumuladoMes);
					psUpdateConsumoMes.setLong(2, rs5.getLong("id"));
					psUpdateConsumoMes.execute();
				} else {
					psInsertConsumoMes.setTimestamp(1, new Timestamp(redondeoMes(fechaAnterior).getTime()));
					psInsertConsumoMes.setBigDecimal(2, valor);
					psInsertConsumoMes.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (rs2!=null)
					rs2.close();
				if (rs3!=null)
					rs3.close();
				if (rs4 != null)
					rs4.close();
				if (rs5 != null)
					rs5.close();
				if (psInsert!=null)
					psInsert.close();
				if (psUpdate!=null)
					psUpdate.close();
				if (psUpdateConsumo!=null)
					psUpdateConsumo.close();
				if (psSelectDia!=null)
					psSelectDia.close();
				if (psSelectMes != null)
					psSelectMes.close();
				if (psUpdateConsumoMes != null) 
					psUpdateConsumoMes.close();
				if (psInsertConsumoMes != null)
					psInsertConsumoMes.close();
				if (ps!=null)
					ps.close();				
				if (conn!=null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Date redondeoHora(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}
	
	private Date redondeoMes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	private boolean mismaHora(Date fechaAnterior, Date fechaNueva) {
		Date fecha1 = redondeoHora(fechaAnterior);
		Date fecha2 = redondeoHora(fechaNueva);
		if (fecha1.compareTo(fecha2)==0) {
			return true;
		} else {
			return false;
		}
	}

}

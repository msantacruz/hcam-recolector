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
		consolidadorConsumo.consolidarConsumo();
	}
	
	private void consolidarConsumo() {
		Connection conn = null;
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psUpdateConsumo = null;
		PreparedStatement psSelectDia = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		ResultSet rs2 =null;
		ResultSet rs3 =null;
		
		Date fechaAnterior = null;
		BigDecimal presionSuma = new BigDecimal(0);
		int contador = 0;
		try {
			conn = GestorConexion.obtenerConexion();
			
			psInsert = conn
					.prepareStatement("INSERT INTO consumo_agua(id, fecha, consumo)"
							+ "VALUES (nextval('seq_consumo_agua'), ?, ?)");
			
			psUpdateConsumo = conn
					.prepareStatement("UPDATE consumo_agua set consumo = consumo + ? where id = ?)");
			
			
			psSelectDia = conn
					.prepareStatement("select * from consumo_agua where date_trunc('day', fecha::timestamp) = date_trunc('day', ?::timestamp) ");
			
			psUpdate = conn
					.prepareStatement("UPDATE agua set consolidado = true where date_trunc('hour', fecha::timestamp)"
							+ " = date_trunc('hour', ?::timestamp)");			
			
			ps = conn
					.prepareStatement("select * from agua where fecha < date_trunc('hour', now()::timestamp) "
							+ " and consolidado = false order by fecha");
			rs = ps.executeQuery();
			while (rs.next()) {
				Date fecha = rs.getTimestamp("fecha");
				if (fechaAnterior == null) {
					contador++;
					presionSuma = rs.getBigDecimal("presion");
					fechaAnterior = fecha;
				} else {
					if (mismaHora(fechaAnterior, fecha)) {
						contador++;
						presionSuma = presionSuma.add(rs.getBigDecimal("presion"));
					} else {
						psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
						rs2 = psSelectDia.executeQuery();
						if (rs2.next()) {
							BigDecimal promPresion = presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP);
							psUpdateConsumo.setBigDecimal(1, promPresion.multiply(new BigDecimal(60)));
							psUpdateConsumo.setLong(2, rs2.getLong("id"));
							psUpdate.execute();
						} else {
							psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							BigDecimal promPresion = presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP);
							psInsert.setBigDecimal(2, promPresion.multiply(new BigDecimal(60)));
							psInsert.execute();
							psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psUpdate.execute();	
						}
						fechaAnterior = fecha;
						contador = 1;
						presionSuma = rs.getBigDecimal("presion");
					}	
				}
			}
			if (contador>0) {
				psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
				rs3 = psSelectDia.executeQuery();
				if (rs3.next()) {
					BigDecimal promPresion = presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP);
					psUpdateConsumo.setBigDecimal(1, promPresion.multiply(new BigDecimal(60)));
					psUpdateConsumo.setLong(2, rs3.getLong("id"));
					psUpdate.execute();
				} else {
					psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					BigDecimal promPresion = presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP);
					psInsert.setBigDecimal(2, promPresion.multiply(new BigDecimal(60)));
					psInsert.execute();
					psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psUpdate.execute();	
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				rs2.close();
				rs3.close();
				psInsert.close();
				psUpdate.close();
				psUpdateConsumo.close();
				psSelectDia.close();
				ps.close();				
				conn.close();
			} catch (SQLException e) {
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

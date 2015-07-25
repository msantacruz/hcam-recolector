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
						psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
						rs2 = psSelectDia.executeQuery();
						if (rs2.next()) {
							BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
							//System.out.println("Promedio: " + promFlujo);
							BigDecimal valor = promFlujo.multiply(new BigDecimal(60));
							//System.out.println("fecha: " + fechaAnterior + "  valor: " + valor);
							BigDecimal consumo = rs2.getBigDecimal("consumo");
							BigDecimal acumulado = consumo.add(valor);
							psUpdateConsumo.setBigDecimal(1, acumulado);
							psUpdateConsumo.setLong(2, rs2.getLong("id"));
							psUpdateConsumo.execute();
							psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psUpdate.execute();
						} else {
							psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
							//System.out.println("Promedio: " + promFlujo);
							//System.out.println("fecha: " + fechaAnterior + "  valor: " + promFlujo.multiply(new BigDecimal(60)));
							psInsert.setBigDecimal(2, promFlujo.multiply(new BigDecimal(60)));
							psInsert.execute();
							psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
							psUpdate.execute();	
						}
						fechaAnterior = fecha;
						contador = 1;
						flujoSuma = rs.getBigDecimal("flujo");
					}	
				}
			}
			if (contador>0) {
				psSelectDia.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
				rs3 = psSelectDia.executeQuery();
				if (rs3.next()) {
					BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
					//System.out.println("Promedio: " + promFlujo);
					BigDecimal valor = promFlujo.multiply(new BigDecimal(60));
					//System.out.println("fecha: " + fechaAnterior + "  valor: " + valor);
					BigDecimal consumo = rs3.getBigDecimal("consumo");
					BigDecimal acumulado = consumo.add(valor);
					psUpdateConsumo.setBigDecimal(1, acumulado);
					psUpdateConsumo.setLong(2, rs3.getLong("id"));
					psUpdateConsumo.execute();
					psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psUpdate.execute();
				} else {
					psInsert.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					BigDecimal promFlujo = flujoSuma.divide(new BigDecimal(contador), 4, BigDecimal.ROUND_UP);
					//System.out.println("Promedio: " + promFlujo);
					//System.out.println("fecha: " + fechaAnterior + "  valor: " + promFlujo.multiply(new BigDecimal(60)));
					psInsert.setBigDecimal(2, promFlujo.multiply(new BigDecimal(60)));
					psInsert.execute();
					psUpdate.setTimestamp(1, new Timestamp(redondeoHora(fechaAnterior).getTime()));
					psUpdate.execute();	
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
				if (psInsert!=null)
					psInsert.close();
				if (psUpdate!=null)
					psUpdate.close();
				if (psUpdateConsumo!=null)
					psUpdateConsumo.close();
				if (psSelectDia!=null)
					psSelectDia.close();
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

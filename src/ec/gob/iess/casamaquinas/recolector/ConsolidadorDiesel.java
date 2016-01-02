package ec.gob.iess.casamaquinas.recolector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class ConsolidadorDiesel {

	public void consolidarConsumoDiesel(int entero, int decimal) {
		Connection conn = null;
		PreparedStatement psSelectDia = null;
		PreparedStatement psInsertDia = null;
		PreparedStatement psUpdateDia = null;
		PreparedStatement psSelectMes = null;
		PreparedStatement psInsertMes = null;
		PreparedStatement psUpdateMes = null;
		ResultSet rsDia = null;
		ResultSet rsMes = null;
		
		BigDecimal valor = new BigDecimal(entero);
		
		valor = valor.add(new BigDecimal(decimal).divide(new BigDecimal(100)));
		
		try {
			conn = GestorConexion.obtenerConexion();
			
			// Acumula por día
			
			psSelectDia = conn.prepareStatement("select * from consumo_diesel where date_trunc('day', fecha::timestamp) = date_trunc('day', current_timestamp::timestamp) ");
			
			psInsertDia = conn.prepareStatement("INSERT INTO consumo_diesel(id, fecha, consumo) VALUES (nextval('seq_consumo_diesel'), ?, ?)");
			psUpdateDia = conn.prepareStatement("UPDATE consumo_diesel SET consumo = ? where id=?");
			
			rsDia = psSelectDia.executeQuery();
			if (rsDia.next()) {
				System.out.println("Va a sumar el dia");
				BigDecimal consumoAnterior = rsDia.getBigDecimal("consumo");
				System.out.println("Valor anterior: " + consumoAnterior);
				BigDecimal nuevoConsumo = consumoAnterior.add(valor);
				System.out.println("Nuevo valor: " + nuevoConsumo);
				psUpdateDia.setBigDecimal(1, nuevoConsumo);
				psUpdateDia.setInt(2, rsDia.getInt("id"));
				psUpdateDia.executeUpdate();
			} else {
				System.out.println("Va a ingresar el dia");
				System.out.println("Valor: " + valor);
				psInsertDia.setTimestamp(1, new Timestamp(redondeoHora(new Date()).getTime()));
				psInsertDia.setBigDecimal(2, valor);
				psInsertDia.executeUpdate();
			}
			
			
			// Acumula por mes
			
			psSelectMes = conn
					.prepareStatement("select * from consumo_mes_diesel where date_trunc('month', fecha::timestamp) = date_trunc('month', current_timestamp::timestamp) ");
			
			psUpdateMes = conn
					.prepareStatement("UPDATE consumo_mes_diesel set consumo_total_mes = ? where id = ?");
			
			psInsertMes = conn
					.prepareStatement("INSERT INTO consumo_mes_diesel(id, fecha, consumo_total_mes)"
							+ "VALUES (nextval('seq_consumo_mes_diesel'), ?, ?)");
			
			rsMes = psSelectMes.executeQuery();
			if (rsMes.next()) {
				System.out.println("Va a sumar el mes");
				BigDecimal consumoTotalMes = rsMes.getBigDecimal("consumo_total_mes");
				System.out.println("Valor anterior: " + consumoTotalMes);
				BigDecimal acumuladoMes = consumoTotalMes.add(valor);
				System.out.println("Nuevo valor: " + acumuladoMes);
				psUpdateMes.setBigDecimal(1, acumuladoMes);
				psUpdateMes.setLong(2, rsMes.getLong("id"));
				psUpdateMes.execute();
			} else {
				System.out.println("Va a ingresar el mes");
				System.out.println("Valor: " + valor);
				psInsertMes.setTimestamp(1, new Timestamp(redondeoMes(new Date()).getTime()));
				psInsertMes.setBigDecimal(2, valor);
				psInsertMes.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psSelectDia!=null)
					psSelectDia.close();
				if (psInsertDia!=null)
					psInsertDia.close();
				if (psUpdateDia!=null)
					psUpdateDia.close();
				if (psSelectMes!=null)
					psSelectMes.close();
				if (psInsertMes!=null)
					psInsertMes.close();
				if (psUpdateMes!=null)
					psUpdateMes.close();
				if (rsDia != null) 
					rsDia.close();
				if (rsMes != null) 
					rsMes.close();
				if (conn!=null)
					conn.close();
			} catch(Exception e) {}
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
}

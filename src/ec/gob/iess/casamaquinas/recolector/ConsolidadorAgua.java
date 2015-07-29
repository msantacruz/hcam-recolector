package ec.gob.iess.casamaquinas.recolector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ConsolidadorAgua {

	public static void main(String[] args) {
		ConsolidadorAgua replicadorConsolidador = new ConsolidadorAgua();
		replicadorConsolidador.consolidarAgua();
	}

	private void consolidarAgua() {
		Connection conn = null;
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		PreparedStatement psSelectBombas =  null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		Date fechaAnterior = null;
		BigDecimal presionSuma = new BigDecimal(0);
		BigDecimal flujoSuma = new BigDecimal(0);
		int contador = 0;
		try {
			conn = GestorConexion.obtenerConexion();
			
			psInsert = conn
					.prepareStatement("INSERT INTO agua(id, fecha, presion, flujo, bomba_1, bomba_2, bomba_3, alarma)"
							+ "VALUES (nextval('seq_agua'), ?, ?, ?, ?, ?, ?, ?)");
			
			psUpdate = conn
					.prepareStatement("UPDATE presion_flujo set consolidado = true where date_trunc('minutes', fecha::timestamp)"
							+ " = date_trunc('minutes', ?::timestamp)");
			
			psSelectBombas = conn
					.prepareStatement("select * from estado_bombas where date_trunc('minutes', fecha::timestamp)"
							+ " = date_trunc('minutes', ?::timestamp) order by fecha");
			//desc limit 1
			ps = conn
					.prepareStatement("select * from presion_flujo where fecha < date_trunc('minutes', now()::timestamp) "
							+ " and consolidado = false order by fecha");
			rs = ps.executeQuery();
			while (rs.next()) {
				Date fecha = rs.getTimestamp("fecha");
				if (fechaAnterior == null) {
					contador++;
					presionSuma = rs.getBigDecimal("presion");
					flujoSuma = rs.getBigDecimal("flujo");
					fechaAnterior = fecha;
				} else {
					if (mismoMinuto(fechaAnterior, fecha)) {
						contador++;
						presionSuma = presionSuma.add(rs.getBigDecimal("presion"));
						flujoSuma = flujoSuma.add(rs.getBigDecimal("flujo"));
					} else {
						psInsert.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
						psInsert.setBigDecimal(2, presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP));
						psInsert.setBigDecimal(3, flujoSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP));
						psSelectBombas.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
						rs2 = psSelectBombas.executeQuery();
						boolean estadoAlarma = false;
						while (rs2.next()) {
							if (rs2.getBoolean("alarma")){
								estadoAlarma=true;
							}	
								if(rs2.getBoolean("bomba1")){
									psInsert.setString(4, "FUNCIONANDO");
								}
								else {
									psInsert.setString(4, "APAGADA");
								}
								if(rs2.getBoolean("bomba2")){
									psInsert.setString(5, "FUNCIONANDO");
								}
								else{
									psInsert.setString(5, "APAGADA");
								}
								if(rs2.getBoolean("bomba3")){
									psInsert.setString(6, "FUNCIONANDO");
								}
								else{
									psInsert.setString(6, "APAGADA");
								}
						}
						if(estadoAlarma){
							psInsert.setString(7, "ACTIVA");
						}
						else{
							psInsert.setString(7, "INACTIVA");
						}		
						psInsert.execute();
						psUpdate.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
						psUpdate.execute();
						fechaAnterior = fecha;
						contador = 1;
						presionSuma = rs.getBigDecimal("presion");
						flujoSuma = rs.getBigDecimal("flujo");
					}	
				}
			}
			if (contador>0) {
				psInsert.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
				psInsert.setBigDecimal(2, presionSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP));
				psInsert.setBigDecimal(3, flujoSuma.divide(new BigDecimal(contador), 2, BigDecimal.ROUND_UP));
				psSelectBombas.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
				rs3 = psSelectBombas.executeQuery();
				
				boolean estadoAlarma = false;
				while (rs3.next()) {
					if(rs3.getBoolean("alarma")){
						estadoAlarma = true;
					}
						if(rs3.getBoolean("bomba1")){
							psInsert.setString(4, "FUNCIONANDO");
						}
						else {
							psInsert.setString(4, "APAGADA");
						}
						if(rs3.getBoolean("bomba2")){
							psInsert.setString(5, "FUNCIONANDO");
						}
						else{
							psInsert.setString(5, "APAGADA");
						}
						if(rs3.getBoolean("bomba3")){
							psInsert.setString(6, "FUNCIONANDO");
						}
						else{
							psInsert.setString(6, "APAGADA");
						}
				}
				if(estadoAlarma){
					psInsert.setString(7, "ACTIVA");
				}
				else{
					psInsert.setString(7, "INACTIVA");
				}
				psInsert.execute();
				psUpdate.setTimestamp(1, new Timestamp(redondeoMinutos(fechaAnterior).getTime()));
				psUpdate.execute();
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
				if (psSelectBombas!=null)
					psSelectBombas.close();
				if (ps!=null)
					ps.close();				
				if (conn!=null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Date redondeoMinutos(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	private boolean mismoMinuto(Date fechaAnterior, Date fechaNueva) {
		Date fecha1 = redondeoMinutos(fechaAnterior);
		Date fecha2 = redondeoMinutos(fechaNueva);
		if (fecha1.compareTo(fecha2)==0) {
			return true;
		} else {
			return false;
		}
	}
}

package ec.gob.iess.casamaquinas.recolector.dto;

import java.sql.Timestamp;

public class PresionFlujoDTO {

	private String presion;
	private String flujo;
	private Timestamp fecha;
	
	public String getPresion() {
		return presion.trim();
	}
	public void setPresion(String presion) {
		this.presion = presion;
	}
	public String getFlujo() {
		return flujo.trim();
	}
	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}	
}

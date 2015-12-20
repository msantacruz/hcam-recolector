package ec.gob.iess.casamaquinas.recolector.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReplicacionAguaDTO {

	private Long id;
	private Date fecha;
	private BigDecimal presion;
	private BigDecimal flujo;
	private String bomba1;
	private String bomba2;
	private String bomba3;
	private String alarma;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public BigDecimal getPresion() {
		return presion;
	}
	public void setPresion(BigDecimal presion) {
		this.presion = presion;
	}
	public BigDecimal getFlujo() {
		return flujo;
	}
	public void setFlujo(BigDecimal flujo) {
		this.flujo = flujo;
	}
	public String getBomba1() {
		return bomba1;
	}
	public void setBomba1(String bomba1) {
		this.bomba1 = bomba1;
	}
	public String getBomba2() {
		return bomba2;
	}
	public void setBomba2(String bomba2) {
		this.bomba2 = bomba2;
	}
	public String getBomba3() {
		return bomba3;
	}
	public void setBomba3(String bomba3) {
		this.bomba3 = bomba3;
	}
	public String getAlarma() {
		return alarma;
	}
	public void setAlarma(String alarma) {
		this.alarma = alarma;
	}
}

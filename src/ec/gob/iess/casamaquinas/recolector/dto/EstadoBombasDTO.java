package ec.gob.iess.casamaquinas.recolector.dto;

import java.sql.Timestamp;

public class EstadoBombasDTO {
	
	private boolean bomba1;
	private boolean bomba2;
	private boolean bomba3;
	private boolean alarma;
	private boolean altaPresion;
	private boolean bajaPresion;
	private Timestamp fecha;
	
	public boolean isBomba1() {
		return bomba1;
	}
	public void setBomba1(boolean bomba1) {
		this.bomba1 = bomba1;
	}
	public boolean isBomba2() {
		return bomba2;
	}
	public void setBomba2(boolean bomba2) {
		this.bomba2 = bomba2;
	}
	public boolean isBomba3() {
		return bomba3;
	}
	public void setBomba3(boolean bomba3) {
		this.bomba3 = bomba3;
	}
	public boolean isAlarma() {
		return alarma;
	}
	public void setAlarma(boolean alarma) {
		this.alarma = alarma;
	}
	public boolean isAltaPresion() {
		return altaPresion;
	}
	public void setAltaPresion(boolean altaPresion) {
		this.altaPresion = altaPresion;
	}
	public boolean isBajaPresion() {
		return bajaPresion;
	}
	public void setBajaPresion(boolean bajaPresion) {
		this.bajaPresion = bajaPresion;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
}

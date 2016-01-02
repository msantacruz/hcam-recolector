package ec.gob.iess.casamaquinas.recolector.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReplicacionConsumoDieselDTO {
	private Long id;
	private Date fecha;
	private BigDecimal consumo;

	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getConsumo() {
		return consumo;
	}
	public void setConsumo(BigDecimal consumo) {
		this.consumo = consumo;
	}	
}



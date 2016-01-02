package ec.gob.iess.casamaquinas.recolector.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReplicacionDatosDieselDTO {
	
	private Long id;
	private Date fecha;
	private Integer temperatura;
	private Integer bajo_tanque1;
	private Integer alto_tanque1;
	private Integer bajo_tanque2;
	private Integer alto_tanque2;
	private Double pulsos_entrada;
	private Integer bomba_ingreso;
	private Integer frecuencia_entrada;
	private Integer bomba_tdiario;
	private Integer galones_salida;
	private Integer fracc_galonsalida;
	private Integer frecuencia_salida;
	private Integer flujo_salida;
	private Integer fracc_flujosalida;
	private Integer galones_entrada;
	private Integer fracc_galonentrada;
	private Integer flujo_entrada;
	private Integer fracc_flujoentrada;
	private Integer paro_emergencia;
	private Integer inicio_galont1;
	private Integer inicio_fraccgalont1;
	private Integer inicio_galont2;
	private Integer inicio_fraccgalont2;
	private Integer total_galont1;
	private Integer total_fraccgalont1;
	private Integer total_galont2;
	private Integer total_fraccgalont2;
	private Integer pedido_tanque;
	private Integer tanque_uso;
	private Integer modo;
	private BigDecimal valor_total_entrada;
	private BigDecimal valor_total_salida;
	private BigDecimal valor_total_tanque1;
	private BigDecimal valor_total_tanque2;
	private BigDecimal valor_flujo_entrada;
	private BigDecimal valor_flujo_salida;
	private BigDecimal valor_total_acumulado;
	private BigDecimal valor_total_temperatura;
	private Integer resetIngreso;
	private Integer consumo;
	private Integer fraccConsumo;
	
	
	

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}
	public Integer getBajo_tanque1() {
		return bajo_tanque1;
	}
	public void setBajo_tanque1(Integer bajo_tanque1) {
		this.bajo_tanque1 = bajo_tanque1;
	}
	public Integer getAlto_tanque1() {
		return alto_tanque1;
	}
	public void setAlto_tanque1(Integer alto_tanque1) {
		this.alto_tanque1 = alto_tanque1;
	}
	public Integer getBajo_tanque2() {
		return bajo_tanque2;
	}
	public void setBajo_tanque2(Integer bajo_tanque2) {
		this.bajo_tanque2 = bajo_tanque2;
	}
	public Integer getAlto_tanque2() {
		return alto_tanque2;
	}
	public void setAlto_tanque2(Integer alto_tanque2) {
		this.alto_tanque2 = alto_tanque2;
	}
	public Double getPulsos_entrada() {
		return pulsos_entrada;
	}
	public void setPulsos_entrada(Double pulsos_entrada) {
		this.pulsos_entrada = pulsos_entrada;
	}
	public Integer getBomba_ingreso() {
		return bomba_ingreso;
	}
	public void setBomba_ingreso(Integer bomba_ingreso) {
		this.bomba_ingreso = bomba_ingreso;
	}
	public Integer getFrecuencia_entrada() {
		return frecuencia_entrada;
	}
	public void setFrecuencia_entrada(Integer frecuencia_entrada) {
		this.frecuencia_entrada = frecuencia_entrada;
	}
	public Integer getBomba_tdiario() {
		return bomba_tdiario;
	}
	public void setBomba_tdiario(Integer bomba_tdiario) {
		this.bomba_tdiario = bomba_tdiario;
	}
	public Integer getGalones_salida() {
		return galones_salida;
	}
	public void setGalones_salida(Integer galones_salida) {
		this.galones_salida = galones_salida;
	}
	public Integer getFracc_galonsalida() {
		return fracc_galonsalida;
	}
	public void setFracc_galonsalida(Integer fracc_galonsalida) {
		this.fracc_galonsalida = fracc_galonsalida;
	}
	public Integer getFrecuencia_salida() {
		return frecuencia_salida;
	}
	public void setFrecuencia_salida(Integer frecuencia_salida) {
		this.frecuencia_salida = frecuencia_salida;
	}
	public Integer getFlujo_salida() {
		return flujo_salida;
	}
	public void setFlujo_salida(Integer flujo_salida) {
		this.flujo_salida = flujo_salida;
	}
	public Integer getFracc_flujosalida() {
		return fracc_flujosalida;
	}
	public void setFracc_flujosalida(Integer fracc_flujosalida) {
		this.fracc_flujosalida = fracc_flujosalida;
	}
	public Integer getGalones_entrada() {
		return galones_entrada;
	}
	public void setGalones_entrada(Integer galones_entrada) {
		this.galones_entrada = galones_entrada;
	}
	public Integer getFracc_galonentrada() {
		return fracc_galonentrada;
	}
	public void setFracc_galonentrada(Integer fracc_galonentrada) {
		this.fracc_galonentrada = fracc_galonentrada;
	}
	public Integer getFlujo_entrada() {
		return flujo_entrada;
	}
	public void setFlujo_entrada(Integer flujo_entrada) {
		this.flujo_entrada = flujo_entrada;
	}	
	public Integer getFracc_flujoentrada() {
		return fracc_flujoentrada;
	}
	public void setFracc_flujoentrada(Integer fracc_flujoentrada) {
		this.fracc_flujoentrada = fracc_flujoentrada;
	}
	public Integer getParo_emergencia() {
		return paro_emergencia;
	}
	public void setParo_emergencia(Integer paro_emergencia) {
		this.paro_emergencia = paro_emergencia;
	}
	public Integer getInicio_galont1() {
		return inicio_galont1;
	}
	public void setInicio_galont1(Integer inicio_galont1) {
		this.inicio_galont1 = inicio_galont1;
	}
	public Integer getInicio_fraccgalont1() {
		return inicio_fraccgalont1;
	}
	public void setInicio_fraccgalont1(Integer inicio_fraccgalont1) {
		this.inicio_fraccgalont1 = inicio_fraccgalont1;
	}
	public Integer getInicio_galont2() {
		return inicio_galont2;
	}
	public void setInicio_galont2(Integer inicio_galont2) {
		this.inicio_galont2 = inicio_galont2;
	}
	public Integer getInicio_fraccgalont2() {
		return inicio_fraccgalont2;
	}
	public void setInicio_fraccgalont2(Integer inicio_fraccgalont2) {
		this.inicio_fraccgalont2 = inicio_fraccgalont2;
	}
	public Integer getTotal_galont1() {
		return total_galont1;
	}
	public void setTotal_galont1(Integer total_galont1) {
		this.total_galont1 = total_galont1;
	}
	public Integer getTotal_fraccgalont1() {
		return total_fraccgalont1;
	}
	public void setTotal_fraccgalont1(Integer total_fraccgalont1) {
		this.total_fraccgalont1 = total_fraccgalont1;
	}
	public Integer getTotal_galont2() {
		return total_galont2;
	}
	public void setTotal_galont2(Integer total_galont2) {
		this.total_galont2 = total_galont2;
	}
	public Integer getTotal_fraccgalont2() {
		return total_fraccgalont2;
	}
	public void setTotal_fraccgalont2(Integer total_fraccgalont2) {
		this.total_fraccgalont2 = total_fraccgalont2;
	}
	public Integer getPedido_tanque() {
		return pedido_tanque;
	}
	public void setPedido_tanque(Integer pedido_tanque) {
		this.pedido_tanque = pedido_tanque;
	}
	public Integer getTanque_uso() {
		return tanque_uso;
	}
	public void setTanque_uso(Integer tanque_uso) {
		this.tanque_uso = tanque_uso;
	}
	public Integer getModo() {
		return modo;
	}
	public void setModo(Integer modo) {
		this.modo = modo;
	}
	public BigDecimal getValor_total_entrada() {
		return valor_total_entrada;
	}
	public void setValor_total_entrada(BigDecimal valor_total_entrada) {
		this.valor_total_entrada = valor_total_entrada;
	}
	public BigDecimal getValor_total_salida() {
		return valor_total_salida;
	}
	public void setValor_total_salida(BigDecimal valor_total_salida) {
		this.valor_total_salida = valor_total_salida;
	}
	public BigDecimal getValor_total_tanque1() {
		return valor_total_tanque1;
	}
	public void setValor_total_tanque1(BigDecimal valor_total_tanque1) {
		this.valor_total_tanque1 = valor_total_tanque1;
	}
	public BigDecimal getValor_total_tanque2() {
		return valor_total_tanque2;
	}
	public void setValor_total_tanque2(BigDecimal valor_total_tanque2) {
		this.valor_total_tanque2 = valor_total_tanque2;
	}
	public BigDecimal getValor_flujo_entrada() {
		return valor_flujo_entrada;
	}
	public void setValor_flujo_entrada(BigDecimal valor_flujo_entrada) {
		this.valor_flujo_entrada = valor_flujo_entrada;
	}
	public BigDecimal getValor_flujo_salida() {
		return valor_flujo_salida;
	}
	public void setValor_flujo_salida(BigDecimal valor_flujo_salida) {
		this.valor_flujo_salida = valor_flujo_salida;
	}
	public BigDecimal getValor_total_acumulado() {
		return valor_total_acumulado;
	}
	public void setValor_total_acumulado(BigDecimal valor_total_acumulado) {
		this.valor_total_acumulado = valor_total_acumulado;
	}
	public BigDecimal getValor_total_temperatura() {
		return valor_total_temperatura;
	}
	public void setValor_total_temperatura(BigDecimal valor_total_temperatura) {
		this.valor_total_temperatura = valor_total_temperatura;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getResetIngreso() {
		return resetIngreso;
	}
	public void setResetIngreso(Integer resetIngreso) {
		this.resetIngreso = resetIngreso;
	}
	public Integer getConsumo() {
		return consumo;
	}
	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}
	public Integer getFraccConsumo() {
		return fraccConsumo;
	}
	public void setFraccConsumo(Integer fraccConsumo) {
		this.fraccConsumo = fraccConsumo;
	}
}

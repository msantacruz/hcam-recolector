package ec.gob.iess.casamaquinas.recolector;

import java.net.InetAddress;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;

public class RecolectorDiesel {

	public static void main(String[] args) throws Exception {
		RecolectorDiesel recolectorDiesel = new RecolectorDiesel();
		recolectorDiesel.ejecutar();
	}
	
	public void ejecutar() throws Exception {
		TCPMasterConnection con = null; // the connection
		ModbusTCPTransaction trans = null; // the transaction

		ReadMultipleRegistersResponse res = null;

		ManejadorMovimientoDiesel manejadorMovimientoDiesel = new ManejadorMovimientoDiesel();

		String evento = "";
		Double cantidad = null;

		InetAddress addr = InetAddress.getByName("192.168.0.1");
		; // the slave's address
		int port = Modbus.DEFAULT_PORT;
		int ref = 0; // the reference; offset where to start reading from
		int count = 20; // the number of DI's to read

		// Open the connection
		con = new TCPMasterConnection(addr);
		con.setPort(port);
		con.connect();

		// Prepare the request
		ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(
				ref, count);

		// Prepare the transaction
		trans = new ModbusTCPTransaction(con);
		trans.setRequest(req);

		// Execute the transaction
		do {
			trans.execute();
			res = (ReadMultipleRegistersResponse) trans.getResponse();
			Register[] registers = res.getRegisters();

			if (registers[0].getValue() == 1) {
				if (registers[1].getValue() == 0) {
					evento = "I";
					cantidad = new Double(registers[10].getValue());
				} else {
					evento = "S";
					cantidad = new Double(registers[11].getValue());
				}
				manejadorMovimientoDiesel.registrarMovimiento(evento, cantidad,
						new Double(registers[12].getValue()));
			}
			Thread.sleep(2000);
		} while (true);
	}

}

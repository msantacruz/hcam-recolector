package ec.gob.iess.casamaquinas.recolector;

import java.net.InetAddress;

import ec.gob.iess.casamaquinas.recolector.manejadores.ManejadorMovimientoDiesel;
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


		InetAddress addr = InetAddress.getByName("192.168.0.1");
		; // the slave's address
		int port = Modbus.DEFAULT_PORT;
		int ref = 0; // the reference; offset where to start reading from
		int count = 60; // the number of DI's to read

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

		//int proximoPedido = 0;
		//int tanqueUso = 0;
		
		// Execute the transaction
		do {
			trans.execute();
			res = (ReadMultipleRegistersResponse) trans.getResponse();
			Register[] registers = res.getRegisters();
			
			//proximoPedido = registers[55].getValue();
			//tanqueUso = registers[57].getValue();
			
			if (registers[3].getValue() == 10 || registers[5].getValue() == 10 || registers[7].getValue() == 10 || registers[9].getValue() == 10 || registers[13].getValue() == 10 || registers[17].getValue() == 10 || registers[37].getValue() == 10) {
				manejadorMovimientoDiesel.registrarMovimiento(registers[1].getValue(),registers[3].getValue(),registers[5].getValue()
						,registers[7].getValue(),registers[9].getValue(),calculoPulsos(registers[10].getValue(), registers[11].getValue())
						,registers[13].getValue(),registers[15].getValue(),registers[17].getValue(),registers[19].getValue()
						,registers[21].getValue(),registers[23].getValue(),registers[25].getValue(),registers[27].getValue()
						,registers[29].getValue(),registers[31].getValue(),registers[33].getValue(),registers[35].getValue()
						,registers[37].getValue(),registers[39].getValue(),registers[41].getValue(),registers[43].getValue()
						,registers[45].getValue(),registers[47].getValue(),registers[49].getValue(),registers[51].getValue()
						,registers[53].getValue(),registers[55].getValue(),registers[57].getValue(),registers[59].getValue());
			}
			
			
			
			Thread.sleep(2000);
		} while (true);
	}

	private int calculoPulsos(int valor1, int valor2) {
		int resultado = valor1*65535;
		resultado = resultado + valor2;
		return resultado;
	}
}

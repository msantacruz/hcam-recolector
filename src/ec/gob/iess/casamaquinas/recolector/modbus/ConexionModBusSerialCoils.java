package ec.gob.iess.casamaquinas.recolector.modbus;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

public class ConexionModBusSerialCoils {

	public static void main(String[] args) throws Exception {
		SerialConnection con = null; // the connection
		ModbusSerialTransaction trans = null; // the transaction

		ReadCoilsRequest req = null; // the request
		ReadCoilsResponse res = null; // the response

		/* Variables for storing the parameters */
		String portname = "COM5"; // the name of the serial port to be used
		int unitid = 1; // the unit identifier we will be talking to
		int ref = 2048; // the reference, where to start reading from
		int count = 3; // the count of IR's to read
		int repeat = 10; // a loop for repeating the transaction

		// 2. Set master identifier
		// ModbusCoupler.createModbusCoupler(null);
		ModbusCoupler.getReference().setUnitID(1);

		// 3. Setup serial parameters
		SerialParameters params = new SerialParameters();
		params.setPortName(portname);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("odd");
		params.setStopbits(1);
		params.setEncoding("rtu");
		params.setEcho(false);

		// 4. Open the connection
		con = new SerialConnection(params);
		con.open();

		// 5. Prepare a request
		req = new ReadCoilsRequest(ref, count);
		req.setUnitID(unitid);
		req.setHeadless();

		// 6. Prepare a transaction
		trans = new ModbusSerialTransaction(con);
		trans.setRequest(req);
		
		// 7. Execute the transaction repeat times
		int k = 0;
		do {
			System.out.println(req.getHexMessage());
			trans.execute();
			res = (ReadCoilsResponse) trans.getResponse();
			for (int n = 0; n < res.getBitCount(); n++) {
				System.out.println("Word " + n + "=" + res.getCoilStatus(n));
			}
			k++;
			Thread.sleep(2000);
		} while (k < repeat);

		// 8. Close the connection
		con.close();
		/*
		 * ReadMultipleRegistersResponse res = null;
		 * 
		 * ManejadorMovimientoDiesel manejadorMovimientoDiesel = new
		 * ManejadorMovimientoDiesel();
		 * 
		 * String evento = ""; Double cantidad = null;
		 * 
		 * InetAddress addr = InetAddress.getByName("192.168.0.1"); ; // the
		 * slave's address int port = Modbus.DEFAULT_PORT; int ref = 0; // the
		 * reference; offset where to start reading from int count = 20; // the
		 * number of DI's to read
		 * 
		 * // Open the connection con = new TCPMasterConnection(addr);
		 * con.setPort(port); con.connect();
		 * 
		 * // Prepare the request ReadMultipleRegistersRequest req = new
		 * ReadMultipleRegistersRequest( ref, count);
		 * 
		 * // Prepare the transaction trans = new ModbusTCPTransaction(con);
		 * trans.setRequest(req);
		 * 
		 * // Execute the transaction do { trans.execute(); res =
		 * (ReadMultipleRegistersResponse) trans.getResponse(); Register[]
		 * registers = res.getRegisters();
		 * 
		 * if (registers[0].getValue() == 1) { if (registers[1].getValue() == 0)
		 * { evento = "I"; cantidad = new Double(registers[10].getValue()); }
		 * else { evento = "S"; cantidad = new Double(registers[11].getValue());
		 * } manejadorMovimientoDiesel.registrarMovimiento(evento, cantidad, new
		 * Double(registers[12].getValue())); } Thread.sleep(2000); } while
		 * (true);
		 */
	}

}

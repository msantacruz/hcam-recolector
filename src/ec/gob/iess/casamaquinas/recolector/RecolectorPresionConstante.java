package ec.gob.iess.casamaquinas.recolector;

import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

public class RecolectorPresionConstante {

	public static void main(String[] args) throws Exception {
		RecolectorPresionConstante recolectorPresionConstante = new RecolectorPresionConstante();
		recolectorPresionConstante.ejecutar();
	}
	
	public void ejecutar() throws Exception {

		ManejadorPresionConstante manejadorPresionConstante = new ManejadorPresionConstante();

		SerialConnection con = null; // the connection
		ModbusSerialTransaction trans = null; // the transaction

		ReadMultipleRegistersRequest req = null; // the request
		ReadMultipleRegistersResponse res = null; // the response

		ReadCoilsResponse resCoils = null;
		
		ReadCoilsResponse resCoilsTipoAlarma = null;

		/* Variables for storing the parameters */
		String portname = "COM5"; // the name of the serial port to be used
		int unitid = 1; // the unit identifier we will be talking to
		int ref = 1088; // the reference, where to start reading from
		int count = 2; // the count of IR's to read
		int refCoils = 2048; // the reference, where to start reading from
		int countCoils = 10; // the count of IR's to read
		int refCoilsTipoAlarma = 3099; // the reference, where to start reading from
		int countCoilsTipoAlarma = 2; // the count of IR's to read
		
		// 2. Set master identifier
		// ModbusCoupler.createModbusCoupler(null);
		ModbusCoupler.getReference().setUnitID(1);

		// 3. Setup serial parameters
		SerialParameters params = new SerialParameters();
		params.setPortName(portname);
		params.setBaudRate(9600);
		params.setDatabits(8);
		params.setParity("Odd");
		params.setStopbits(1);
		params.setEncoding("rtu");
		params.setEcho(false);

		// 4. Open the connection
		con = new SerialConnection(params);
		con.open();

		// 5. Prepare a request
		req = new ReadMultipleRegistersRequest(ref, count);
		req.setUnitID(unitid);
		req.setHeadless();

		// 6. Prepare a transaction
		trans = new ModbusSerialTransaction(con);
		trans.setRequest(req);

		ReadCoilsRequest reqCoils = new ReadCoilsRequest(refCoils, countCoils);
		reqCoils.setUnitID(unitid);
		reqCoils.setHeadless();
		
		ReadCoilsRequest reqCoilsTipoAlarma = new ReadCoilsRequest(refCoilsTipoAlarma, countCoilsTipoAlarma);
		reqCoilsTipoAlarma.setUnitID(unitid);
		reqCoilsTipoAlarma.setHeadless();

		// 6. Prepare a transaction
		ModbusSerialTransaction transCoils = new ModbusSerialTransaction(con);
		transCoils.setRequest(reqCoils);
		ModbusSerialTransaction transCoilsTipoAlarma = new ModbusSerialTransaction(con);
		transCoilsTipoAlarma.setRequest(reqCoilsTipoAlarma);

		// 7. Execute the transaction repeat times
		int k = 1;
		do {
			trans.execute();
			res = (ReadMultipleRegistersResponse) trans.getResponse();
			manejadorPresionConstante.registrarPresionFlujo(
					Integer.toHexString(res.getRegisterValue(0)),
					Integer.toHexString(res.getRegisterValue(1)));
			if (k == 5) {
				transCoils.execute();
				resCoils = (ReadCoilsResponse) transCoils.getResponse();
				
				for(int i=0; i<resCoils.getBitCount(); i++ ) {
					System.out.println(i + ": " + resCoils.getCoilStatus(i));
				}
				
				transCoilsTipoAlarma.execute();
				resCoilsTipoAlarma = (ReadCoilsResponse) transCoilsTipoAlarma.getResponse();
				manejadorPresionConstante.registrarEstadoBombasAlarma(
						resCoils.getCoilStatus(0), resCoils.getCoilStatus(1),
						resCoils.getCoilStatus(2), resCoils.getCoilStatus(8),
						resCoilsTipoAlarma.getCoilStatus(0), resCoilsTipoAlarma.getCoilStatus(1));
				k = 0;
			}
			k++;
			Thread.sleep(2000);
		} while (true);

		// 8. Close the connection
		// con.close();
	}

}

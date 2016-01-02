package ec.gob.iess.casamaquinas.recolector;

import java.io.IOException;

import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.ServiceException;

public class ControladorProgramas extends AbstractService {

	public int serviceMain(String[] args) throws ServiceException {
				
		try {
			String path = "c:\\temp\\hcam-cliente\\";
			
			/*Process processAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
					"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.RecolectorPresionConstante");
			*/
			Process processAgua = Runtime.getRuntime().exec("c:\\Python27\\python.exe "+ path + "recolector.py");
			
			Process processConsolidadorAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
					"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.ConsolidadorAgua");
					
			Process processConsolidadorConsumo = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
					"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.ConsolidadorConsumo");
			
			Process processReplicadorAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
					path + "postgresql-9.3-1100.jdbc4.jar;" +
					path + "commons-logging-1.2.jar;" +
					path + "gson-2.3.1.jar;" +
					path + "httpclient-4.5.1.jar;" +
					path + "httpcore-4.4.3.jar " +
					"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorAgua");
			
			Process processReplicadorConsumoAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
					path + "postgresql-9.3-1100.jdbc4.jar;" +
					path + "commons-logging-1.2.jar;" +
					path + "gson-2.3.1.jar;" +
					path + "httpclient-4.5.1.jar;" +
					path + "httpcore-4.4.3.jar " +
					"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorConsumoAgua");
			
			Process processReplicadorPresionFlujoEstadoBombas = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
					path + "postgresql-9.3-1100.jdbc4.jar;" +
					path + "commons-logging-1.2.jar;" +
					path + "gson-2.3.1.jar;" +
					path + "httpclient-4.5.1.jar;" +
					path + "httpcore-4.4.3.jar " +
					"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorPresionFlujoEstadoBombas");
			
			Process processReplicadorDatosDiesel = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
					path + "postgresql-9.3-1100.jdbc4.jar;" +
					path + "commons-logging-1.2.jar;" +
					path + "gson-2.3.1.jar;" +
					path + "httpclient-4.5.1.jar;" +
					path + "httpcore-4.4.3.jar " +
					"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorDatosDiesel");
			
			Process processReplicadorConsumoDiesel = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
					path + "postgresql-9.3-1100.jdbc4.jar;" +
					path + "commons-logging-1.2.jar;" +
					path + "gson-2.3.1.jar;" +
					path + "httpclient-4.5.1.jar;" +
					path + "httpcore-4.4.3.jar " +
					"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorConsumoDiesel");
			
			while (!shutdown) {
				System.out.println("Proceso Agua: " + processAgua.isAlive());
				if (!processAgua.isAlive()) {
					System.err.println("Proceso Agua SALIDA: " + processAgua.exitValue());
					/*
					processAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
							+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
							"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.RecolectorPresionConstante");
							*/
					processAgua = Runtime.getRuntime().exec("c:\\Python27\\python.exe "+ path + "recolector.py");
					/*sdtInput = new BufferedReader(new InputStreamReader(processAgua.getErrorStream()));
					if((s = sdtInput.readLine()) != null) {
						System.out.println(s);
					}*/
				}
				
				System.out.println("Proceso Consolidador Agua: " + processConsolidadorAgua.isAlive());
				if (!processConsolidadorAgua.isAlive()) {
					System.err.println("Proceso Consolidador Agua SALIDA: " + processConsolidadorAgua.exitValue());
					processConsolidadorAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
							+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
							"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.ConsolidadorAgua");
				}
				
				System.out.println("Proceso Consolidador Consumo: " + processConsolidadorConsumo.isAlive());
				if (!processConsolidadorConsumo.isAlive()) {
					System.err.println("Proceso Consolidador Consumo SALIDA: " + processConsolidadorConsumo.exitValue());
					processConsolidadorConsumo = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
							+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path +
							"postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.ConsolidadorConsumo");
				}
				System.out.println("Proceso Replicador Agua: " + processReplicadorAgua.isAlive());
				if (!processReplicadorAgua.isAlive()) {
					System.err.println("Proceso Replicador Agua SALIDA: " + processReplicadorAgua.exitValue());
					 processReplicadorAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
								+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
								path + "postgresql-9.3-1100.jdbc4.jar;" +
								path + "commons-logging-1.2.jar;" +
								path + "gson-2.3.1.jar;" +
								path + "httpclient-4.5.1.jar;" +
								path + "httpcore-4.4.3.jar " +
								"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorAgua");
				}
				System.out.println("Proceso Replicador Consumo Agua: " + processReplicadorConsumoAgua.isAlive());
				if (!processReplicadorConsumoAgua.isAlive()) {
					System.err.println("Proceso Replicador Consumo Agua SALIDA: " + processReplicadorConsumoAgua.exitValue());
					 processReplicadorAgua = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
								+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
								path + "postgresql-9.3-1100.jdbc4.jar;" +
								path + "commons-logging-1.2.jar;" +
								path + "gson-2.3.1.jar;" +
								path + "httpclient-4.5.1.jar;" +
								path + "httpcore-4.4.3.jar " +
								"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorConsumoAgua");
				}
				System.out.println("Proceso Replicador Presion Flujo y Estado Bombas: " + processReplicadorConsumoAgua.isAlive());
				if (!processReplicadorPresionFlujoEstadoBombas.isAlive()) {
					System.err.println("Proceso Replicador Consumo Agua SALIDA: " + processReplicadorPresionFlujoEstadoBombas.exitValue());
					processReplicadorPresionFlujoEstadoBombas = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
								+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
								path + "postgresql-9.3-1100.jdbc4.jar;" +
								path + "commons-logging-1.2.jar;" +
								path + "gson-2.3.1.jar;" +
								path + "httpclient-4.5.1.jar;" +
								path + "httpcore-4.4.3.jar " +
								"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorPresionFlujoEstadoBombas");
				}
				System.out.println("Proceso Replicador Datos Diesel: " + processReplicadorDatosDiesel.isAlive());
				if (!processReplicadorDatosDiesel.isAlive()) {
					System.err.println("Proceso Replicador Datos Diesel SALIDA: " + processReplicadorDatosDiesel.exitValue());
					processReplicadorDatosDiesel = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
							+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
							path + "postgresql-9.3-1100.jdbc4.jar;" +
							path + "commons-logging-1.2.jar;" +
							path + "gson-2.3.1.jar;" +
							path + "httpclient-4.5.1.jar;" +
							path + "httpcore-4.4.3.jar " +
							"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorDatosDiesel");
				}
				System.out.println("Proceso Replicador Consumo Diesel: " + processReplicadorConsumoDiesel.isAlive());
				if (!processReplicadorConsumoDiesel.isAlive()) {
					System.err.println("Proceso Replicador Consumo Diesel SALIDA: " + processReplicadorConsumoDiesel.exitValue());
					processReplicadorConsumoDiesel = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
							+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ 
							path + "postgresql-9.3-1100.jdbc4.jar;" +
							path + "commons-logging-1.2.jar;" +
							path + "gson-2.3.1.jar;" +
							path + "httpclient-4.5.1.jar;" +
							path + "httpcore-4.4.3.jar " +
							"ec.gob.iess.casamaquinas.recolector.replicadores.ReplicadorConsumoDiesel");
				}
				Thread.sleep(1000);
			}
			
			return 0;
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	}

}

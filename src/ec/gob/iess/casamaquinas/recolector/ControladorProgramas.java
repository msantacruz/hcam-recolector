package ec.gob.iess.casamaquinas.recolector;

import java.io.IOException;

public class ControladorProgramas {

	public static void main(String[] args) {
				
		try {
			String path = "c:\\temp\\hcam-cliente\\";
			
			Process process = Runtime.getRuntime().exec("java.exe -cp " + path + "hcam-recolector.jar;"
					+ path + "jamod-1.2.3-SNAPSHOT.jar;"+ path + "postgresql-9.3-1100.jdbc4.jar ec.gob.iess.casamaquinas.recolector.RecolectorPresionConstante");
			process.waitFor();
			System.out.println(process.exitValue());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*System.out.println("Ejecutando el recolector de movimientos diesel");
		RecolectorDiesel recolectorDiesel = new RecolectorDiesel();
		try {
			recolectorDiesel.ejecutar();
		} catch (Exception e) {
			System.out.println("Error al ejecutar el recolector de movimientos diesel");
			e.printStackTrace();
		}
		
		System.out.println("Ejecutando el medidor de presion constante");
		RecolectorPresionConstante recolectorPresionConstante = new RecolectorPresionConstante();
		try {
			recolectorPresionConstante.ejecutar();
		} catch (Exception e) {
			System.out.println("Error al ejecutar el recolector de presion constante");
			e.printStackTrace();
		}*/
	}

}

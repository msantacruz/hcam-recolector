package 
ec.gob.iess.casamaquinas.recolector;

public class RecolectorDatos {

	public static void main(String[] args) {
		
		System.out.println("Ejecutando el recolector de movimientos diesel");
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
		}
	}

}

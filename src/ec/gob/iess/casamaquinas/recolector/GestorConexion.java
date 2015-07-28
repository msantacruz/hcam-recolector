package ec.gob.iess.casamaquinas.recolector;

import java.sql.Connection;
import java.sql.DriverManager;

public class GestorConexion {

	//private static Connection connection = null;
	
	public static Connection obtenerConexion() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/hcam", "adminbwhvjhr", "xnw5Px3WqUi6");
			return connection;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*public static void cerrarConexion() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}*/
}

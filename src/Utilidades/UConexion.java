package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UConexion {

	private static UConexion uCon;
	private Connection conection;

	// para que sea solo controlable desde aca
	private UConexion() {
	}

	public static UConexion getInstance() {
		if (uCon == null) {
			uCon = new UConexion();
		}
		return uCon;
	}

	public Connection establecerConexion() {
		//https://docs.osgi.org/javadoc/r4v43/core/org/osgi/framework/BundleContext.html#getProperty%28java.lang.String%29
		ResourceBundle rs = ResourceBundle.getBundle("framework");
		//https://stackoverflow.com/questions/17484764/java-lang-classnotfoundexception-com-mysql-jdbc-driver-in-eclipse
		
		try {
			Class.forName(rs.getString("driver"));
			this.conection = DriverManager.getConnection(rs.getString("locationBD"), rs.getString("user"),
					rs.getString("pass"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.conection;
	}

	public void cerrarConexion() {
		try {
			this.conection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

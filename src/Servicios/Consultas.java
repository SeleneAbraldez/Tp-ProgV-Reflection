package Servicios;

import Utilidades.UBean;
import Utilidades.UConexion;

import java.util.ArrayList;

import Anotaciones.Columna;
import Anotaciones.Tabla;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultas {

	/**
	 * Guardar en la base de datos del objeto armando query por medio de reflexion
	 * 
	 * @param obj
	 */
	public static void guardar(Object obj) {
		String consulta = "INSERT INTO ";
		ArrayList<Field> atributos = UBean.obtenerAtributos(obj);
		String cantAtr = "";

		consulta += obj.getClass().getAnnotation(Tabla.class).nombre() + " (";

		for (Field attr : atributos) {
			consulta += attr.getAnnotation(Columna.class).nombre() + ", ";
			// para adaptar bien a la cantidad y no hardcodearlo
			if (attr.getType().getSimpleName().equals("String")) {
				cantAtr += "'" + UBean.ejecutarGet(obj, attr.getName().toString()) + "', ";
			} else {
				cantAtr += UBean.ejecutarGet(obj, attr.getName().toString()) + ", ";
			}

		}

		consulta = consulta.substring(0, consulta.length() - 2);
		consulta += ") VALUES (";
		consulta += cantAtr;
		consulta = consulta.substring(0, consulta.length() - 2);
		consulta += ")";

		conexionConsulta(consulta);
	}

	/**
	 * modificar todas las columnas menos id ararmar query medio de reflrexion
	 * 
	 * @param obj
	 */
	public static void modificar(Object obj) {
		String consulta = "UPDATE ";
		ArrayList<Field> atributos = UBean.obtenerAtributos(obj);
		String id = "";

		consulta += obj.getClass().getAnnotation(Tabla.class).nombre() + " SET ";

		for (Field attr : atributos) {
			if (attr.getAnnotation(Columna.class).nombre().equals("id")) {
				id = UBean.ejecutarGet(obj, attr.getName()).toString();
			} else {
				consulta += attr.getAnnotation(Columna.class).nombre() + "=";
				consulta += "'" + UBean.ejecutarGet(obj, attr.getName()).toString() + "', ";
			}
		}

		consulta = consulta.substring(0, consulta.length() - 2);

		consulta += " WHERE id = " + id;

		conexionConsulta(consulta);
	}

	/**
	 * eliinar regstro de la base de datos ararmar query medio de reflrexion
	 * 
	 * @param obj
	 */
	public static void eliminar(Object obj) {
		String consulta = "DELETE FROM ";
		ArrayList<Field> atributos = UBean.obtenerAtributos(obj);
		String id = "";

		consulta += obj.getClass().getAnnotation(Tabla.class).nombre() + " ";

		for (Field attr : atributos) {
			if (attr.getAnnotation(Columna.class).nombre().equals("id")) {
				id = UBean.ejecutarGet(obj, attr.getName()).toString();
				break;
			}
		}

		consulta += "WHERE id = " + id;

		System.out.println(consulta);

		conexionConsulta(consulta);
	}

	/**
	 * Devuelve objeto de la clasee peduda con datos cargados armarse la query por
	 * medio de reflexión
	 * 
	 * @param c:  clase del objeto
	 * @param id: id del objeto pedido
	 * @return Object: objeto con datos
	 */
	public static Object obtenerPorId(Class c, Object id) {
		String consulta = "SELECT * FROM ";
		Constructor constructor;
		Object instancia = null; 

		try {
			constructor = c.getConstructor();
			instancia = constructor.newInstance();

			consulta += "" + (((Tabla) c.getAnnotation(Tabla.class)).nombre()) + "";
			consulta += " WHERE id = " + id;

			UConexion uCone = UConexion.getInstance();
			Connection conn = uCone.establecerConexion();
			PreparedStatement ps = conn.prepareStatement(consulta);
			System.out.println(consulta);
			ResultSet rs = ps.executeQuery();

			ps.execute();

			while (rs.next()) {
				for (Field attr : UBean.obtenerAtributos(instancia)) {
					if (attr.getType().getSimpleName().equals("String")) {
						UBean.ejecutarSet(instancia, attr.getAnnotation(Columna.class).nombre(),
								rs.getString(attr.getAnnotation(Columna.class).nombre()));
					} else if (attr.getType().getSimpleName().equals("int")) {
						UBean.ejecutarSet(instancia, attr.getAnnotation(Columna.class).nombre(),
								rs.getInt(attr.getAnnotation(Columna.class).nombre()));
					}
				}
			}

			uCone.cerrarConexion();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return instancia;
	}

	// separe la conexion proque me parecia que estaba repitiendo demasiado el
	// codigo si no
	public static void conexionConsulta(String consulta) {
		try {
			UConexion uCone = UConexion.getInstance();
			Connection conn = uCone.establecerConexion();
			PreparedStatement ps = conn.prepareStatement(consulta);

			ps.execute();
			System.out.println(consulta);

			uCone.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

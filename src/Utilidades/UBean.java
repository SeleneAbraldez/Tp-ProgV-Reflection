package Utilidades;

import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UBean {

	/**
	 * Devuelve los atributos del objeto pasado
	 * 
	 * @param obj: objeto pasado para averiguar atributos
	 * @return ArrayList<Field>: arraylist con todos los atributos
	 */
	public static ArrayList<Field> obtenerAtributos(Object o) {
		ArrayList<Field> listAtts = new ArrayList<Field>();
		Class<? extends Object> c = o.getClass();

		Field[] atts = c.getDeclaredFields();
		for (Field f : atts) {
			listAtts.add(f);
		}

		return listAtts;
	}

	/**
	 * Ejecuta set del objeto pasado del atributo pasado con valor especifico 7
	 * 
	 * @param o:     objeto a ejecutar
	 * @param att:   atributo que se quiere settaer
	 * @param valor: valor a settear
	 */
	public static void ejecutarSet(Object o, String att, Object valor) {
		Class<? extends Object> c = o.getClass();
		String nombreAtt = att.substring(0, 1).toUpperCase() + att.substring(1);
		Method[] metodos = c.getDeclaredMethods();

		for (Method m : metodos) {
			if (m.getName().equals("set" + nombreAtt)) {
				Object[] params = new Object[1];
				// no es necesario chequear que el valor sea uno valido para el att porque por
				// como estamos programando no hay chance de que pase
				params[0] = valor;
				try {
					m.invoke(o, params);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * Ejecuta get del objeto pasado del atributo pasado
	 * 
	 * @param o:   objeto a ejecutar
	 * @param att: atributo que se quiere traer
	 * @return Object: objeto con valor del att
	 */
	public static Object ejecutarGet(Object o, String att) {
		Object valorAtt = null;
		Class<? extends Object> c = o.getClass();
		String nombreAtt = att.substring(0, 1).toUpperCase() + att.substring(1);
		Method[] metodos = c.getDeclaredMethods();

		for (Method m : metodos) {
			if (m.getName().equals("get" + nombreAtt)) {
				try {
					valorAtt = m.invoke(o, null);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}

		return valorAtt;
	}

}

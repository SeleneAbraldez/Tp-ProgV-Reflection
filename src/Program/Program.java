package Program;

import Clases.Persona;
import Servicios.Consultas;

public class Program {

	public static void main(String[] args) {

		Persona p = new Persona(10, "Selenee", "A");

		//Consultas.guardar(p);
		//Consultas.modificar(p);
		//Consultas.eliminar(p);		
		//Consultas.obtenerPorId(Persona.class, 9);
		System.out.println(Consultas.obtenerPorId(Persona.class, 9));
		
	}
	
}

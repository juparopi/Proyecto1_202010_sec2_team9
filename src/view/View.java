package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar comparendos");
			System.out.println("2. Jaime");
			System.out.println("3. Jaime");
			System.out.println("4. Jaime");
			System.out.println("5. Primer comparendo con una infracción");
			System.out.println("6. Todos los comparendos con una infracción en orden ascendente por fecha");
			System.out.println("7. Comparendos por Infracción en servicio Particular y servicio Público");
			System.out.println("8. Comparendos por infracción entre ciertas fechas");
			System.out.println("9. Rankig de N infracciones más comunes entre ciertas fechas");
			System.out.println("10. Jaime");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
			System.out.println(modelo);
		}
}
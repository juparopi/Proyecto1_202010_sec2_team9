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
			System.out.println("2. Buscar el primer comparendo por localidad dada");
			System.out.println("3. Buscar todos los comparendos en una fecha dada");
			System.out.println("4. Comparar tipos de comparendos en dos fechas dadas");
			System.out.println("5. Buscar el primer comparendo por infracci�n dada");
			System.out.println("6. Consultar todos los comparendos con una infracci�n dada");
			System.out.println("7. Comparar tipos de comparendos para dos c�digos dados");
			System.out.println("8. Mostrar cantidades de cada tipo de comparendo entre dos fechas dadas");
			System.out.println("9. Comparar tipos de comparendos en dos fechas dadas");
			System.out.println("10. Consultar informaci�n de los N c�digos con m�s infracciones en un periodo de tiempo dado");
			System.out.println("11. Hacer un histograma con el n�mero total de comparendos por localidad");
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
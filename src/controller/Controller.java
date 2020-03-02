package controller;

import java.util.Scanner;

import javax.swing.JFileChooser;

import java.io.*;

import model.data_structures.IQueue;
import model.data_structures.ListaEncadenada;
import model.data_structures.NodoLista;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}
		
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		Integer dato = null;
		Integer respuesta = null;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
					view.printMessage("--------- \nSe realiza la carga de los comparendos: ");
					//String ruta = "./data/comparendos_dei_2018.geojson";   // Comparendos totales
					String ruta = "./data/comparendos_dei_2018_small.geojson";     //Prueba comparendos
					modelo.cargarDatos(ruta);
					Comparendo comparendoMaxObj = modelo.darComparendoConMaxObj();
				    view.printMessage("\nArreglo creado");
				    view.printMessage("\nNumero actual de comparendos " + modelo.darArreglo().darTamano());
				    view.printMessage("\nComparendo con mayo ObjectId: OnjectId ="+comparendoMaxObj.darObjectId()+", Fecha :"+comparendoMaxObj.darFecha()+", Infraccion : "+comparendoMaxObj.darInfraccion()+", Clase Vehiculo :"+comparendoMaxObj.darClaseVehi()+", Tipo Servicio : "+comparendoMaxObj.darTipoServi()+", localidad = "+comparendoMaxObj.darLocalidad());
				    view.printMessage("\nLa zonaMinMax esta definida por: \nMenor latitud: "+modelo.darMinLat()+", Menor longuitud: "+modelo.darMinLon()+", Mayor latitud: "+modelo.darMaxLat()+" y Mayor longuitud: "+modelo.darMaxLon() );
					break;

				case 2:
					
					break;
					

				case 3:
					
					break;
					
				case 4:
					
					break;

				case 5:
					view.printMessage("--------- \nInserte la infracción que desea buscar: ");
					String infracc = lector.nextLine();
					Comparendo comp = modelo.darPrimerComparendoXInfracc(infracc);
					if( comp == null)
					{
						view.printMessage("\n No se encontró ningun comparendo con la infracción "+infracc);
					}
					else
					{
						view.printMessage("\n Primer comparendo con la infracción "+infracc+" es:");
						view.printMessage("\n"+comp.darObjectId()+", Fecha :"+comp.darFecha()+", Infraccion : "+comp.darInfraccion()+", Clase Vehiculo :"+comp.darClaseVehi()+", Tipo Servicio : "+comp.darTipoServi()+", localidad = "+comp.darLocalidad());
					}
					break;

				case 6:
					view.printMessage("--------- \nInserte la infracción que desea buscar: ");
					infracc = lector.nextLine();
					ListaEncadenada<Comparendo> list = modelo.darComparendosXInfracc(infracc);
					view.printMessage("\nHay un total de "+list.darTamano()+" comparendos con la infracción "+infracc);
					if(list.darPrimero()!= null)
					{
						view.printMessage(" y son:");
						NodoLista<Comparendo> nodo = list.darPrimero();
						for(int i = 0; i < list.darTamano(); i++)
						{	
							comp = nodo.darElemento();
							view.printMessage("\n"+comp.darObjectId()+", Fecha :"+comp.darFecha()+", Infraccion : "+comp.darInfraccion()+", Clase Vehiculo :"+comp.darClaseVehi()+", Tipo Servicio : "+comp.darTipoServi()+", localidad = "+comp.darLocalidad());
							nodo = nodo.darSiguiente();
						}
					}
					break;
				case 7:
					
					break;

			}
		}
		
	}	
}
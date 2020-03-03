package controller;

import java.util.Scanner;

import javax.swing.JFileChooser;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.io.*;

import model.data_structures.IQueue;
import model.data_structures.ListaEncadenada;
import model.data_structures.NodoLista;
import model.logic.Comparendo;
import model.logic.Linea;
import model.logic.Linea2;
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
				    view.printMessage("\nComparendo con mayor ObjectId: OnjectId ="+comparendoMaxObj.darObjectId()+", Fecha :"+comparendoMaxObj.darFecha()+", Infraccion : "+comparendoMaxObj.darInfraccion()+", Clase Vehiculo :"+comparendoMaxObj.darClaseVehi()+", Tipo Servicio : "+comparendoMaxObj.darTipoServi()+", localidad = "+comparendoMaxObj.darLocalidad());
				    view.printMessage("\nLa zonaMinMax esta definida por: \nMenor latitud: "+modelo.darMinLat()+", Menor longuitud: "+modelo.darMinLon()+", Mayor latitud: "+modelo.darMaxLat()+" y Mayor longuitud: "+modelo.darMaxLon() );
					break;

				case 2:
					view.printMessage("--------- \nInserte la localidad que desea buscar: ");
					String loc = lector.next();
					Comparendo comp1 = modelo.darPrimerComparendoXLocalidad(loc);
					if( comp1 == null)
					{
						view.printMessage("\n No se encontró ningun comparendo con la localidad "+loc);
					}
					else
					{
						view.printMessage("\n Primer comparendo con la localidad "+loc+" es:");
						view.printMessage("\n"+comp1.darObjectId()+", Fecha :"+comp1.darFecha()+", Infraccion : "+comp1.darInfraccion()+", Clase Vehiculo :"+comp1.darClaseVehi()+", Tipo Servicio : "+comp1.darTipoServi()+", localidad = "+comp1.darLocalidad());
					}
					break;

				case 3:
					view.printMessage("--------- \nInserte la fecha para la que desea encontrar infracciones: ");
					String fecha = lector.next();
					ListaEncadenada<Comparendo> lista = modelo.darComparendosXFecha(fecha);
					view.printMessage("\nHay un total de "+lista.darTamano()+" comparendos con la fecha "+fecha);
					if(lista.darPrimero()!= null)
					{
						System.out.print(" y son: ");
						NodoLista<Comparendo> nodo = lista.darPrimero();
						for(int i = lista.darTamano() - 1; i >= 0; i--)
						{	
							Comparendo comparendo = nodo.darElemento();
							view.printMessage("\n"+comparendo.darObjectId()+", Fecha :"+comparendo.darFecha()+", Infraccion : "+comparendo.darInfraccion()+", Clase Vehiculo :"+comparendo.darClaseVehi()+", Tipo Servicio : "+comparendo.darTipoServi()+", localidad = "+comparendo.darLocalidad());
							nodo = nodo.darSiguiente();
						}
					}
					break;

				case 4:
					view.printMessage("--------- \nInserte la primera fecha para la cual desea comparar los tipos de comparendo: ");
					String fecha1 = lector.next();
					view.printMessage("--------- \nInserte la segunda fecha para la cual desea comparar los tipos de comparendo: ");
					String fecha2 = lector.next();
					ListaEncadenada<Comparendo> l1 = modelo.darComparendosXFecha(fecha1);
					ListaEncadenada<Comparendo> l2 = modelo.darComparendosXFecha(fecha2);
					if(l1.darPrimero() != null || l2.darPrimero() != null){
						view.printMessage("Infracción | "+fecha1+" | "+fecha2+"\n");
						String comparado = null;
						if(l1.darPrimero().darElemento().darInfraccion().compareTo(l2.darPrimero().darElemento().darInfraccion()) <= 0){
							comparado = l1.darPrimero().darElemento().darInfraccion();
						}else{
							comparado = l2.darPrimero().darElemento().darInfraccion();
						}
						view.printMessage(comparado + " | ");
						int cont1 = 0;
						int cont2 = 0;
						while(l1.darPrimero() != null && l2.darPrimero() != null){
							if(l1.darPrimero().darElemento().darInfraccion().equals(comparado)){	
								cont1++;
								l1.eliminar(l1.darPrimero().darElemento());
							}
							if(l2.darPrimero().darElemento().darInfraccion().equals(comparado)){	
								cont2++;
								l2.eliminar(l2.darPrimero().darElemento());
							}
							if(!(l1.darPrimero() == null) && !(l2.darPrimero() == null) && !(l1.darPrimero().darElemento().darInfraccion().equals(comparado)) && !(l2.darPrimero().darElemento().darInfraccion().equals(comparado)))
							{
								view.printMessage(cont1 + " | " + cont2 + "\n");
								if(l1.darPrimero().darElemento().darInfraccion().compareTo(l2.darPrimero().darElemento().darInfraccion()) <= 0){
									comparado = l1.darPrimero().darElemento().darInfraccion();
								}else{
									comparado = l2.darPrimero().darElemento().darInfraccion();
								}
								view.printMessage(comparado + " | ");
								cont1 = 0;
								cont2 = 0;
							}
						}
						if(l1.darPrimero() != null){
							while(l1.darPrimero() != null){
								if(l1.darPrimero().darElemento().darInfraccion().equals(comparado)){	
									cont1++;
									l1.eliminar(l1.darPrimero().darElemento());
								}
								if(!(l1.darPrimero() == null) && !(l1.darPrimero().darElemento().darInfraccion().equals(comparado)))
								{
									view.printMessage(cont1 + " | " + cont2 + "\n");
									comparado = l1.darPrimero().darElemento().darInfraccion();
									view.printMessage(comparado + " | ");
									cont1 = 0;
									cont2 = 0;
								}
							}
						}
						else if(l2.darPrimero() != null){
							while(l2.darPrimero() != null){
								if(l2.darPrimero().darElemento().darInfraccion().equals(comparado)){	
									cont2++;
									l2.eliminar(l2.darPrimero().darElemento());
								}
								if(!(l2.darPrimero() == null) && !(l2.darPrimero().darElemento().darInfraccion().equals(comparado)))
								{
									view.printMessage(cont1 + " | " + cont2 + "\n");
									comparado = l2.darPrimero().darElemento().darInfraccion();
									view.printMessage(comparado + " | ");
									cont1 = 0;
									cont2 = 0;
								}
							}
						}
					}
					break;

				case 5:
					view.printMessage("--------- \nInserte la infracción que desea buscar: ");
					String infracc = lector.next();
					Comparendo comp = modelo.darPrimerComparendoXInfracc(infracc);
					if( comp == null)
					{
						view.printMessage("\n No se encontró ningun comparendo con la infracción "+infracc);
					}
					else
					{
						view.printMessage("\n Primer comparendo con la infracción "+infracc+" es:");
						view.printMessage("\nObjectId: "+comp.darObjectId()+", Fecha :"+comp.darFecha()+", Infraccion : "+comp.darInfraccion()+", Clase Vehiculo :"+comp.darClaseVehi()+", Tipo Servicio : "+comp.darTipoServi()+", localidad = "+comp.darLocalidad());
					}
					break;

				case 6:
					view.printMessage("--------- \nInserte la infracción que desea buscar: ");
					infracc = lector.next();
					ListaEncadenada<Comparendo> list = modelo.darComparendosXInfracc(infracc);
					view.printMessage("\nHay un total de "+list.darTamano()+" comparendos con la infracción "+infracc);
					if(list.darPrimero()!= null)
					{
						System.out.print(" y son:");
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
					view.printMessage("--------- \nComparación de comparendos por Infracción en servicio Particular y servicio Público");
					ListaEncadenada<Linea> lista1 = modelo.darComparendosXTipoServi();
					view.printMessage("\nInfracción\t|Particular\t|Público\t");
					NodoLista<Linea> nod = lista1.darPrimero();
					for(int i = 0; i < lista1.darTamano(); i++)
					{
						view.printMessage(nod.darElemento().darInfraccion()+"\t\t|"+nod.darElemento().darParticular()+"\t\t|"+nod.darElemento().darPublico());
						nod = nod.darSiguiente();
					}
					
					break;
				case 8:
					view.printMessage("--------- \nInserte la localidad en la que quiere consultar comparendos: ");
					String locali = lector.next();
					view.printMessage("\nInserte la fecha desde la que quiere consultar, formato(aaaa/mm/dd): ");
					String fechaMin = lector.next();
					view.printMessage("\nInserte la fecha hasta la que quiere consultar, formato(aaaa/mm/dd): ");
					String fechaMax = lector.next();
					ListaEncadenada<Linea2> lista2 = modelo.darComparendosXLocalidadYFechas(locali, fechaMin, fechaMax);
					view.printMessage("\nComparación de comparendos en "+locali+" del "+fechaMin+" al "+fechaMax+":");
					view.printMessage("\nInfracción\t|# Comparendos");
					NodoLista<Linea2> nodo = lista2.darPrimero();
					for(int i = 0; i < lista2.darTamano(); i++)
					{
						view.printMessage("\n"+nodo.darElemento().darInfraccion()+"\t\t|"+nodo.darElemento().darCantidad());
						nodo = nodo.darSiguiente();
					}
					
					break;
				case 9:
					view.printMessage("--------- \nInserte el número de infracciones con mayor repetición que quiere ver: ");
					int N = lector.nextInt();
					view.printMessage("\nInserte la fecha desde la que quiere consultar, formato(aaaa/mm/dd): ");
					fechaMin = lector.next();
					view.printMessage("\nInserte la fecha hasta la que quiere consultar, formato(aaaa/mm/dd): ");
					fechaMax = lector.next();
					lista2 = modelo.darNMayoresInfracc(N, fechaMin, fechaMax);
					view.printMessage("\nRanking de las "+N+" mayores infracciones del "+fechaMin+" al "+fechaMax+":");
					view.printMessage("\nInfracción\t|# Comparendos");
					nodo = lista2.darPrimero();
					for(int i = 0; i < lista2.darTamano(); i++)
					{
						view.printMessage("\n"+nodo.darElemento().darInfraccion()+"\t|"+nodo.darElemento().darCantidad());
						nodo = nodo.darSiguiente();
					}
					break;
				
				case 10:
					view.printMessage("--------- \nHistograma de comparendos por localidad:\n");
					view.printMessage("Aproximación de número de comparendos por localidad.\n");
					String[] localidades = new String[] {"Antonio Nariño", "Barrios Unidos", "Bosa", "Chapinero", "Ciudad Bolivar", "Engativa", "Fontibon", "Kennedy", "La Candelaria", "Los Martires", "Puente Aranda", "Rafael Uribe Uribe", "San Cristobal", "Santa Fe", "Suba", "Sumapaz", "Teusaquillo", "Tunjuelito", "Usaquen", "Usme"};
					int total = 0;
					for(int i = 0; i < localidades.length; i ++){
						int j = modelo.darComparendosXLocalidad(localidades[i]).darTamano();
						while(localidades[i].length() < 20){
							localidades[i] = localidades[i] + "-";
						}
						total += j;
						view.printMessage(localidades[i] + "|");
						while(j > 0){
							view.printMessage("*");
							j -= 50;
						}
						view.printMessage("\n");
						view.printMessage("\n");
					}
					view.printMessage("Total comparendos: " + total);
					
					break;

			}
		}
	}

}	

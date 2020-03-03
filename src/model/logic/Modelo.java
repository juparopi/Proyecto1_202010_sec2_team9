package model.logic;

import java.util.Comparator;

import com.sun.javafx.geom.Area;
import com.sun.media.sound.AlawCodec;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import model.data_structures.ArregloDinamico;
import model.data_structures.IArregloDinamico;
import model.data_structures.IListaEncadenada;
import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.ListaEncadenada;
import model.data_structures.NodoLista;
import model.data_structures.Queue;
import model.data_structures.Stack;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private ArregloDinamico<Comparendo> arreglo;
	
	private Comparendo comparendoConMaxObj;
	
	private double minLat;
	private double minLon;
	private double maxLat;
	private double maxLon;
	
	/**
	 * Constructor del modelo del mundo
	 */
	public Modelo()
	{
		arreglo = new ArregloDinamico<Comparendo>(10);
	}
	
	public void cargarDatos(String ruta)
	{
		JsonGsonProcessing objetoJsonGson = new JsonGsonProcessing( ruta);
		objetoJsonGson.iniciarLectura(objetoJsonGson, arreglo);
		comparendoConMaxObj = objetoJsonGson.darComparendoMayorOb();
		minLat = objetoJsonGson.darMinLat();
		minLon = objetoJsonGson.darMinLon();
		maxLat = objetoJsonGson.darMaxLat();
		maxLon = objetoJsonGson.darMaxLon();
		
	}
	
	public IArregloDinamico<Comparendo> darArreglo()
	{
		return arreglo;
	}
	public Comparendo darComparendoConMaxObj()
	{
		return comparendoConMaxObj;
	}
	public double darMaxLat()
	{
		return maxLat;
	}
	public double darMaxLon()
	{
		return maxLon;
	}
	public double darMinLat()
	{
		return minLat;
	}
	public double darMinLon()
	{
		return minLon;
	}
	
		
	
	
	
	
	
	//Comparendos por llenar
	
	public Comparendo darPrimerComparendoXLocalidad(String localidad){
		return null;
	}
	
	public IArregloDinamico darComparendosXLocalidad(String localidad){
		return null;
	}
	
	public IArregloDinamico compararComparendosXFecha(String fecha1, String fecha2){
		return null;
	}


	
	public Comparendo darPrimerComparendoXInfracc(String infracc)
	{
		Comparendo rta = null;
		for(int i = 0; i < arreglo.darCapacidad();i++)
		{
			
			if(arreglo.darElemento(i).darInfraccion().equals(infracc))
			{
				rta = arreglo.darElemento(i);
			}
		}
		return rta;
	}
	public ListaEncadenada darComparendosXInfracc(String infracc)
	{
		ListaEncadenada<Comparendo> lista = new ListaEncadenada<Comparendo>();
		Comparendo.ComparadorCompxFecha compxFech = new Comparendo.ComparadorCompxFecha();
		for(int i = 0; i < arreglo.darCapacidad();i++)
		{
			if(arreglo.darElemento(i).darInfraccion().equals(infracc))
			{
				lista.agregarEnOrdenA(arreglo.darElemento(i), compxFech);
			}
		}
		
		return lista;
	}
	
	public ListaEncadenada darComparendosXTipoServi()
	{
		ListaEncadenada<Linea> lista = new ListaEncadenada<Linea>();
		Linea.ComparadorCompxInfracc comparador = new Linea.ComparadorCompxInfracc();
		
		for(int i = 0; i<arreglo.darTamano();i++)
		{
			Linea lineaBusqueda = new Linea(arreglo.darElemento(i).darInfraccion());
			Linea lineaEncontrada = lista.buscar(lineaBusqueda);
			if(lineaEncontrada ==null)
			{
				lineaBusqueda.aumentarValor(arreglo.darElemento(i).darTipoServi());
				lista.agregarEnOrdenA(lineaBusqueda, comparador);
			}
			else
			{
				lineaEncontrada.aumentarValor(arreglo.darElemento(i).darTipoServi());
			}
		}
		return lista;
	}
	
	/**
	 * Metodos parte grupal
	 */
	
	
	public ListaEncadenada darComparendosXLocalidadYFechas(String locali, String fechaMin, String fechaMax)
	{
		ListaEncadenada<Linea2> lista = new ListaEncadenada<Linea2>();
		Linea2.ComparadorCompxInfracc compxInfr = new Linea2.ComparadorCompxInfracc();
		for(int i = 0; i < arreglo.darTamano(); i++)
		{
			if(arreglo.darElemento(i).darLocalidad().equals(locali))
			{
				if((arreglo.darElemento(i).darFecha().compareTo(fechaMin) > 0 && arreglo.darElemento(i).darFecha().compareTo(fechaMax) <0)||(arreglo.darElemento(i).darFecha().compareTo(fechaMin) == 0)||(arreglo.darElemento(i).darFecha().compareTo(fechaMax)==0))  
				{
					if(lista.darPrimero() == null)
					{
						Linea2 lin = new Linea2(arreglo.darElemento(i).darInfraccion());
						lin.aumentarCantidad();
						lista.agregar(lin);
					}
					else
					{
						Linea2 busq = new Linea2(arreglo.darElemento(i).darInfraccion());
						Linea2 lin = lista.buscar(busq);
						if(lin == null)
						{
							busq.aumentarCantidad();
							lista.agregarEnOrdenA(busq, compxInfr);
						}
						else
						{
							lin.aumentarCantidad();
							lista.agregarEnOrdenA(lin, compxInfr);
						}
					}
				}
			}
		}
		
		return lista;
	}
	
	public ListaEncadenada darNMayoresInfracc(int N, String fechaMin, String fechaMax)
	{
		ListaEncadenada<Linea2> lista = new ListaEncadenada<Linea2>();
		ListaEncadenada<Linea2> rta = new ListaEncadenada<Linea2>();
		for(int i = 0; i < arreglo.darTamano(); i++)
		{
			if((arreglo.darElemento(i).darFecha().compareTo(fechaMin) > 0 && arreglo.darElemento(i).darFecha().compareTo(fechaMax) <0)||(arreglo.darElemento(i).darFecha().compareTo(fechaMin) == 0)||(arreglo.darElemento(i).darFecha().compareTo(fechaMax)==0))  
			{
				if(lista.darPrimero() == null)
				{
					Linea2 lin = new Linea2(arreglo.darElemento(i).darInfraccion());
					lin.aumentarCantidad();
					lista.agregar(lin);
				}
				else
				{
					Linea2 busq = new Linea2(arreglo.darElemento(i).darInfraccion());
					Linea2 lin = lista.buscar(busq);
					if(lin == null)
					{
						busq.aumentarCantidad();
						lista.agregar(busq);
					}
					else
					{
						lin.aumentarCantidad();
						lista.agregar(lin);
					}
				}
			}
		}
		for(int i = 0; i < N; i++)
		{
			NodoLista<Linea2> mayor = lista.darPrimero();
			NodoLista<Linea2> nodo = lista.darPrimero();
			int mayorCantidad = 0;
			for(int j = 0; j < lista.darTamano(); j++)
			{
				if(nodo.darElemento().darCantidad()>mayorCantidad)
				{
					mayor = nodo;
					mayorCantidad = nodo.darElemento().darCantidad();
				}
				nodo = nodo.darSiguiente();
			}
			rta.agregar(mayor.darElemento());
			lista.eliminar(mayor.darElemento());
		}
		
		
		return rta;
	}
	
	public IArregloDinamico darHist(){
		return null;
	}
	

}
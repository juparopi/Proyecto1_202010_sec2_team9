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
				lista.agregarEnOrden(arreglo.darElemento(i), compxFech);
			}
		}
		
		return lista;
	}
	
	public ListaEncadenada darComparendosXTipoServi()
	{
		ListaEncadenada<Linea> lista = new ListaEncadenada<Linea>();
		Linea.ComparadorCompxInfracc compxInfr = new Linea.ComparadorCompxInfracc();
		for(int i = 0; i < arreglo.darTamano(); i++)
		{
			if(lista.darPrimero() == null)
			{
				Linea lin = new Linea(arreglo.darElemento(i).darInfraccion());
				lin.aumentarValor(arreglo.darElemento(i).darTipoServi());
				lista.agregar(lin);
			}
			else
			{
				Linea busq = new Linea(arreglo.darElemento(i).darInfraccion());
				Linea lin = lista.buscar(busq);
				if(lin == null)
				{
					busq.aumentarValor(arreglo.darElemento(i).darTipoServi());
					lista.agregarEnOrden(busq, compxInfr);
				}
				else
				{
					lin.aumentarValor(arreglo.darElemento(i).darTipoServi());
					lista.agregarEnOrden(lin, compxInfr);
				}
			}
		}
		
		return lista;
	}
	
	/**
	 * Metodos parte grupal
	 */
	
	
	public IArregloDinamico darComparendosXLocalidadYFechas(String locali, String fechaMin, String fechaMax)
	{
		return null;
	}
	
	public IArregloDinamico darNMayoresInfracc(int N, String fechaMin, String fechaMax)
	{
		return null;
	}
	
	public IArregloDinamico darHist(){
		return null;
	}
	

}
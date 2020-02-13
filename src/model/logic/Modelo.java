package model.logic;

import com.sun.javafx.geom.Area;
import com.sun.media.sound.AlawCodec;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import model.data_structures.ArregloDinamico;
import model.data_structures.IArregloDinamico;
import model.data_structures.IListaEncadenada;
import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.data_structures.ListaEncadenada;
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
	private IArregloDinamico<Comparendo> arreglo;
	
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
	
		
	
	

}
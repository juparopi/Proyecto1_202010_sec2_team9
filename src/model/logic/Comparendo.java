package model.logic;

import java.util.Comparator;

public class Comparendo implements Comparable<Comparendo>
{
	/**
	 * Identificador numericom del comparendo. Se usa en el compareTo
	 */
	private int objectId;
	
	/**
	 * Localidad en la que se crea el comparendo
	 */
	private String localidad;
	
	private String fecha;
	
	private String claseVehi;
	
	private String tipoServi;
	
	private String infrac;

	/**
	 * Coordenada 1, longuitud
	 */
	private double longuitud;
	
	/**
	 * Coordenada 2, latitud
	 */
	private double latitud;
	
	public Comparendo(int ob, String loc, double lon, double lat, String pFecha, String pClaseVehi, String pTipoServi, String pInfra)
	{
		objectId = ob;
		localidad = loc;
		longuitud = lon;
		latitud = lat;
		
		fecha = pFecha;
		claseVehi = pClaseVehi;
		tipoServi = pTipoServi;
		infrac = pInfra;
	}
	
	public String darFecha()
	{
		return fecha;
	}
	public String darClaseVehi()
	{
		return claseVehi;
	}
	public String darTipoServi()
	{
		return tipoServi;
	}
	public String darInfraccion()
	{
		return infrac;
	}
	
	/**
	 *  Retorna el objectId
	 * @return ObjectId del comparendo
	 */
	public int darObjectId()
	{
		return objectId;
	}
	
	/**
	 *  Retorna la localidad
	 * @return localidad del comparendo
	 */	
	public String darLocalidad()
	{
		return localidad;
	}
	
	/**
	 *  Retorna la latitud
	 * @return latitud del comparendo
	 */
	public double darLatitud()
	{
		return latitud;
	}
	
	/**
	 *  Retorna la longuitud
	 * @return longuitud del comparendo
	 */
	public double darLonguitud()
	{
		return longuitud;
	}
	

	public int compareTo(Comparendo o) 
	{
		return (o.darObjectId()-objectId);
	}
	
	public static class ComparadorCompxFecha implements Comparator<Comparendo>
	{
		public int compare(Comparendo comp1, Comparendo comp2) {
			return comp1.darFecha().compareTo(comp2.darFecha());
		}	
	}
}

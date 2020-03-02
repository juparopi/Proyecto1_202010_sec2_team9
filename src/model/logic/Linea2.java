package model.logic;

import java.util.Comparator;

public class Linea2 implements Comparable<Linea2>
{
	private String infraccion;
	private int cant;
	public Linea2(String pInfracc)
	{
		infraccion = pInfracc;
		cant = 0;
	}
	public String darInfraccion()
	{
		return infraccion;
	}
	public int darCantidad()
	{
		return cant;
	}
	public void aumentarCantidad()
	{
		cant++;
	}
	public int compareTo(Linea2 lin)
	{
		return infraccion.compareTo(lin.darInfraccion());
	}
	public static class ComparadorCompxInfracc implements Comparator<Linea2>
	{
		public int compare(Linea2 lin1, Linea2 lin2) {
			return lin1.darInfraccion().compareTo(lin2.darInfraccion());
		}	
	}	

}
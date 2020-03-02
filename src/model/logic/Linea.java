package model.logic;

import java.util.Comparator;

public class Linea implements Comparable<Linea>
{
	private String infraccion;
	private int particular;
	private int publico;
	public Linea(String pInfracc)
	{
		infraccion = pInfracc;
		particular = 0;
		publico = 0;
	}
	public String darInfraccion()
	{
		return infraccion;
	}
	public int darParticular()
	{
		return particular;
	}
	public int darPublico()
	{
		return publico;
	}
	public void aumentarValor(String parOpubl)
	{
		if(parOpubl.equals("Particular"))
		{
			particular++;
		}
		else
		{
			publico++;
		}
	}
	public int compareTo(Linea lin)
	{
		return infraccion.compareTo(lin.darInfraccion());
	}
	public static class ComparadorCompxInfracc implements Comparator<Linea>
	{
		public int compare(Linea lin1, Linea lin2) {
			return lin1.darInfraccion().compareTo(lin2.darInfraccion());
		}	
	}
	
}
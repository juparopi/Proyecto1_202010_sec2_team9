package model.data_structures;

import java.util.Comparator;

import javax.swing.plaf.synth.SynthSeparatorUI;

import model.logic.Comparendo;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class  ListaEncadenada<T extends Comparable<T>> implements IListaEncadenada<T>{
		/**
		 * Numero de nodos de la lista
		 */
        private int tamano;

        /**
         * Primer nodo
         */
        private NodoLista<T> primero;
        
        private NodoLista<T> ultimo;

        /**
         * Construir una lista
         */
		public ListaEncadenada( )
        {
			tamano = 0;
			primero = null;
			ultimo = null;
        }
        
		public void agregar( T dato )
        {
			NodoLista<T> nuevo = new NodoLista<T>(dato);
               if(primero == null)
               {
            	   primero = nuevo;
            	   ultimo = nuevo;
               }
               else
               {
            	   ultimo.cambiarSiguiente(nuevo);
            	   ultimo = ultimo.darSiguiente();
               }
               tamano++;
       }
		
		public void agregarEnOrdenA(T dato, Comparator<T> comparador)
		{
			NodoLista<T> nuevo = new NodoLista<T>(dato);
            if(primero == null)
            {
         	   primero = nuevo;
         	   ultimo = nuevo;
            }
            else
            {
            	boolean agregado = false;
            	if(comparador.compare(primero.darElemento(), nuevo.darElemento()) >0)
            	{
            		nuevo.cambiarSiguiente(primero);
            		primero = nuevo;
            		agregado = true;
            	}
            	else
            	{
            		NodoLista<T> actual = primero;
            		while(actual.darSiguiente()!=null && agregado == false)
            		{
            			if((comparador.compare(actual.darSiguiente().darElemento(), nuevo.darElemento())>0)|| (comparador.compare(actual.darSiguiente().darElemento(), nuevo.darElemento())==0))
            			{
            				System.out.println("entra");
            				nuevo.cambiarSiguiente(actual.darSiguiente());
            				actual.cambiarSiguiente(nuevo);
            				agregado = true;
            			}
            			else
            			{
            				actual = actual.darSiguiente();
            			}
            		}
            	}
            	if(agregado == false)
            	{
            		ultimo.cambiarSiguiente(nuevo);
            		ultimo = ultimo.darSiguiente();
            	}
            }
            tamano++;
		}
		
		public void agregarEnOrdenD(T dato, Comparator<T> comparador)
		{
			NodoLista<T> nuevo = new NodoLista<T>(dato);
            if(primero == null)
            {
         	   primero = nuevo;
         	   ultimo = nuevo;
            }
            else
            {
            	boolean agregado = false;
            	if(comparador.compare(primero.darElemento(), nuevo.darElemento()) <0)
            	{
            		nuevo.cambiarSiguiente(primero);
            		primero = nuevo;
            		agregado = true;
            	}
            	else
            	{
            		NodoLista<T> actual = primero;
            		while(actual.darSiguiente()!=null && agregado == false)
            		{
            			if((comparador.compare(actual.darSiguiente().darElemento(), nuevo.darElemento())<0)|| (comparador.compare(actual.darSiguiente().darElemento(), nuevo.darElemento())==0))
            			{
            				nuevo.cambiarSiguiente(actual.darSiguiente());
            				actual.cambiarSiguiente(nuevo);
            				agregado = true;
            			}
            			else
            			{
            				actual = actual.darSiguiente();
            			}
            		}
            	}
            	if(agregado == false)
            	{
            		ultimo.cambiarSiguiente(nuevo);
            		ultimo = ultimo.darSiguiente();
            	}
            }
            tamano++;
		}
		

		public NodoLista<T> darPrimero() 
		{
			return primero;
		}
		
		public NodoLista<T> darUltimo()
		{
			return ultimo;
		}

		public int darTamano() 
		{
			return tamano;
		}

		public T buscar(T dato) 
		{
			T rta = null;
			if(tamano>0)
			{
				boolean encontrado = false;
				NodoLista<T> actual = primero;
				for (int i = 0; i < tamano && encontrado == false; i++) 
				{
					if(actual.darElemento().compareTo(dato) == 0)
					{
						encontrado = true;
						rta = actual.darElemento();
					}
					else
					{
						actual = actual.darSiguiente();
					}
				}
			}
			return rta;
		}

		public T eliminar(T dato) 
		{
			T rta = null;
			if(buscar(dato)!=null)
			{
				boolean encontrado = false;
				NodoLista<T> actual = primero;
				NodoLista<T> anterior = null;
				for (int i = 0; i < tamano && encontrado == false; i++) 
				{
					if(actual.darElemento().compareTo(dato) == 0)
					{
						if(actual == primero)
						{
							encontrado = true;
							rta = actual.darElemento();
							tamano--;
							primero = primero.darSiguiente();
						}
						else
						{
							encontrado = true;
							rta = actual.darElemento();
							tamano--;
							anterior.cambiarSiguiente(actual.darSiguiente());
						}
						
					}
					else
					{
						anterior = actual;
						actual = actual.darSiguiente();
					}
				}
			}
			return rta;
		}

}
package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class  ArregloDinamico<T extends Comparable<T>> implements IArregloDinamico<T>{
		/**
		 * Capacidad maxima del arreglo
		 */
        private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
        private int tamanoAct;
        /**
         * Arreglo de elementos de tamaNo maximo
         */
        private T elementos[ ];

        /**
         * Construir un arreglo con la capacidad maxima inicial.
         * @param max Capacidad maxima inicial
         */
		public ArregloDinamico( int max )
        {
               elementos = (T[]) new Comparable[max];
               tamanoMax = max;
               tamanoAct = 0;
        }
        
		public void agregar( T dato )
        {
               if ( tamanoAct == tamanoMax )
               {  // caso de arreglo lleno (aumentar tamaNo)
                    tamanoMax = 2 * tamanoMax;
                    T [ ] copia = elementos;
                    elementos = (T[]) new Comparable[tamanoMax];
                    for ( int i = 0; i < tamanoAct; i++)
                    {
                     	 elementos[i] = copia[i];
                    } 

               }	
               elementos[tamanoAct] = dato;
               tamanoAct++;
       }

		public int darCapacidad() {
			return tamanoMax;
		}

		public int darTamano() {
			return tamanoAct;
		}

		public T darElemento(int i) {
			// TODO implementar
			return elementos[i];
		}

		public T buscar(T dato) {
			// TODO implementar
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
			T rta = null;
			for(int i = 0; i < tamanoAct; i++)
			{
				if(dato.compareTo(darElemento(i)) == 0)
				{
					rta = darElemento(i);
					break;
				}
			}
			return rta;
		}

		public T eliminar(T dato) {
			// TODO implementar
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
			T rta = null;
			int pos = 0;
			for(int i = 0; i < tamanoAct; i++)
			{
				if(dato.compareTo(darElemento(i)) == 0)
				{
					pos = i;
					rta =darElemento(i);
				}
			}
			if(pos+1 == tamanoAct)
			{
				elementos[pos]=null;
				tamanoAct--;
			}
			else
			{
				for(int i = pos+1; i < tamanoAct; i++)
				{
					elementos[i-1]=elementos[i];
				}
				elementos[tamanoAct-1]=null;
				tamanoAct--;
			}
			return rta;
		}
		
		public void eliminarUltimo()
		{
			elementos[tamanoAct-1] = null;
		}

}
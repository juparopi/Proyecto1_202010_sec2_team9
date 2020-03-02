package model.logic;

/**
 * Example from
 * http://www.studytrails.com/java/json/java-google-json-parse-json-token-by-token.jsp
 * Modificacion Prof fernando De la Rosa para leer archivo GoeJson con comparendos de Bogota
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
	 

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import model.data_structures.IArregloDinamico;
import model.data_structures.IListaEncadenada;
import model.data_structures.ListaEncadenada;
	 
public class JsonGsonProcessing
{
	// Ejemplos archivos formato JSON
	private final static String areas_JSON_FILE = "./data/areas.json"; // Processing JSONArray
	private final static String dogs_JSON_FILE = "./data/dogs.json"; // Processing JSONArray
	private final static String firstevents_JSON_FILE = "./data/firstevents.json"; // Processing JSONArray
	private final static String owners_JSON_FILE = "./data/owners.json"; // Processing JSONArray
	private final static String secondevents_JSON_FILE = "./data/secondevents.json"; // Processing JSONArray
	
	// Ejemplo archivo formato GEOJSON
	private String comparendos_small_GEOJSON_FILE = "./data/comparendos_dei_2018_small.geojson"; // Processing JSONObject
	
	
	private String rutaArchivoJSON;
	private boolean inicioArrayComparendos;  // identificacion Inicio Array Comparendos
	private boolean leyendoPropiedades;      // inicio propiedad properties de un comparendo
	private boolean leyendoGeometria;        // inicio propiedad geometry de un comparendo
	private boolean crearObjComparendo;      // terminacion de lectura de un objeto Comparendo JSON
	
	private String propiedad;
	private boolean identificarObjectId;	// identificacion propiedad OBJECTID
	private int objectId;					// valor de OBJECTID (NUMBER)
	private boolean identificarLocalidad;   // identificacion propiedad LOCALIDAD
	private String localidad;				// valor de LOCALIDAD (STRING)
	private boolean identificarFecha;
	private String fecha;
	private boolean identificarClaseVehi;
	private String claseVehi;
	private boolean identificarTipoServi;
	private String tipoServi;
	private boolean identificarInfra;
	private String infra;
	
	
	
	private boolean identificarLongitud;    // identificacion propiedad coordinates
	private boolean identificarLatitud;     // identificacion propiedad coordinates
	private double longitud;				// valor de longitud geografica
	private double latitud;					// valor de latitud geografica
	
	private IArregloDinamico<Comparendo> arreglo;
	private Comparendo comparendoConMayorObjectId;
	private double minLon = 0.0000000000001;
	private double minLat;
	private double maxLon;
	private double maxLat;	
	
	/** Metodo constructor */
	public JsonGsonProcessing(String rutaArchivo)
	{
		rutaArchivoJSON = rutaArchivo;
		inicioArrayComparendos = false;
		leyendoPropiedades = false;
		leyendoGeometria = false;
		crearObjComparendo = false;
		
		propiedad = "";
		identificarObjectId = false;
		objectId = -1;
		identificarFecha = false;
		fecha = "";
		identificarClaseVehi = false;
		claseVehi = "";
		identificarTipoServi = false;
		tipoServi = "";
		identificarInfra = false;
		infra = "";
		identificarLocalidad = false;
		localidad = "";
		identificarLongitud = false;
		identificarLatitud = false;		
		longitud = 0.0;
		latitud = 0.0;
	}
	
	/**
	 * Handle an Object. Consume the first token which is BEGIN_OBJECT. Within
	 * the Object there could be array or non array tokens. We write handler
	 * methods for both. Noe the peek() method. It is used to find out the type
	 * of the next token without actually consuming it.
	 *
	 * @param reader
	 * @throws IOException
	 */
	private void handleObject(JsonReader reader) throws IOException
	{
		reader.beginObject();
		while (reader.hasNext()) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.BEGIN_ARRAY))
			{
				handleArray(reader);
			}
			else if (token.equals(JsonToken.BEGIN_OBJECT)) {
				handleObject(reader);

				// adicional
				reader.endObject();
				if ( crearObjComparendo )
				{
					crearComparendo();
				}
			}
			else
			{
				handleNonArrayToken(reader, token);
			}
		}
	}
	 
	/**
	 * Handle a json array. The first token would be JsonToken.BEGIN_ARRAY.
	 * Arrays may contain objects or primitives.
	 *
	 * @param reader
	 * @throws IOException
	 */
	public void handleArray(JsonReader reader) throws IOException
	{
		boolean finish = false;
		reader.beginArray();
		while (!finish) {
			JsonToken token = reader.peek();
			if (token.equals(JsonToken.END_ARRAY)) {

				reader.endArray();
				finish = true;
			} else if (token.equals(JsonToken.BEGIN_OBJECT)) {
				handleObject(reader);
			} else if (token.equals(JsonToken.END_OBJECT)) {
				reader.endObject();
				if ( crearObjComparendo )
				{
					crearComparendo();
				}

			} else
				handleNonArrayToken(reader, token);
		}
	}

	/**
	 * Handle non array non object tokens
	 *
	 * @param reader
	 * @param token
	 * @throws IOException
	 */
	public void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException
	{
		if (token.equals(JsonToken.NAME))
		{
			propiedad = reader.nextName();
			if (propiedad.equalsIgnoreCase("features"))
			{  // Identificacion del JSON Array de comparendos
				inicioArrayComparendos = true;
			}
			if (inicioArrayComparendos)
			{
				if ( propiedad.equalsIgnoreCase("properties") )
				{  // Se comienza a identificar las propiedades de un comparendo
					leyendoPropiedades = true;
				}
				else if ( propiedad.equalsIgnoreCase("geometry") )
				{  // Se comienza a identificar la geometria de un comparendo
					leyendoGeometria= true;
				}	            

				if ( leyendoPropiedades )
				{
					if ( propiedad.equalsIgnoreCase("OBJECTID"))
					{  // Se identifica la propiedad OBJECTID de un comparendo
						identificarObjectId = true;
					}
					else if ( propiedad.equalsIgnoreCase("FECHA_HORA"))
					{	// Se identifica la propiedad FECHA HORA de un comparendo
						identificarFecha = true;
					}
					else if ( propiedad.equalsIgnoreCase("CLASE_VEHI"))
					{	// Se identifica la propiedad Clase Vehi de un comparendo
						identificarClaseVehi = true;
					}
					else if ( propiedad.equalsIgnoreCase("TIPO_SERVI"))
					{	// Se identifica la propiedad Tipo Servi de un comparendo
						identificarTipoServi = true;
					}
					else if ( propiedad.equalsIgnoreCase("INFRACCION"))
					{	// Se identifica la propiedad INFRACCION de un comparendo
						identificarInfra = true;
					}
					else if ( propiedad.equalsIgnoreCase("LOCALIDAD"))
					{	// Se identifica la propiedad LOCALIDAD de un comparendo
						// LOCALIDAD termina la seccion de Propiedades
						identificarLocalidad = true;
						leyendoPropiedades = false; 
					}
				}
				else if ( leyendoGeometria )
				{
					if ( propiedad.equalsIgnoreCase("coordinates"))
					{  // Se identifica la propiedad coordinates de un comparendo
						identificarLongitud = true;
						identificarLatitud = true;
						leyendoGeometria = false; // coordinates termina la seccion de Geometry
						crearObjComparendo = true;
					}
					
				}
				
			}
		}
		else if (token.equals(JsonToken.STRING))
		{
			String valorString = reader.nextString();
            if ( identificarLocalidad )
			{
				localidad = valorString;
				identificarLocalidad = false;		
			}
            else if ( identificarFecha )
			{
				fecha = valorString;
				identificarFecha = false;		
			}
            else if ( identificarClaseVehi )
			{
				claseVehi = valorString;
				identificarClaseVehi = false;		
			}
            else if ( identificarTipoServi)
			{
				tipoServi = valorString;
				identificarTipoServi = false;		
			}
            else if ( identificarInfra )
			{
				infra = valorString;
				identificarInfra = false;		
			}
			else
			{
			}
		}
		else if (token.equals(JsonToken.NUMBER))
		{
			double valorNumerico = reader.nextDouble();
			if ( identificarObjectId )
			{
				objectId = (int) valorNumerico;
				identificarObjectId = false;
			}
			else if ( identificarLongitud )
			{
				longitud = valorNumerico;
				latitud = reader.nextDouble();
				identificarLongitud = false;
				identificarLatitud = false;
			}
			else
			{
				
			}
		}
		else if (token.equals(JsonToken.BOOLEAN))
		{
			boolean valorBool = reader.nextBoolean();
		}
		else
		{
			reader.skipValue();
		}
	}
	    	 
	public void processingJSONFile( ) 
	{
		try
		{
			BufferedReader rd = null;
			StringReader srd = null;
			
			rd = new BufferedReader(new FileReader(rutaArchivoJSON));
			String inputLine = null;
			StringBuilder builder = new StringBuilder();

			//Store the contents of the file to the StringBuilder.
			while((inputLine = rd.readLine()) != null)
			{
				builder.append(inputLine);
			}
			srd = new StringReader(builder.toString());

			JsonReader reader = new JsonReader( srd );
			
			handleObject(reader);


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Creacion de un comparendo a partir de la lectura de todas sus propiedades
	 */
	public void crearComparendo()
	{   
		Comparendo rta = new Comparendo(objectId, localidad, longitud, latitud,fecha,claseVehi,tipoServi,infra);
		int maxObj = 0;
		if(objectId > maxObj)
		{
			comparendoConMayorObjectId = rta;
			maxObj = objectId;
		}
		if(minLon == 0.0000000000001)
		{
			minLat = latitud;
			minLon = longitud;
			maxLon = longitud;
			maxLat = latitud;
		}
		else
		{
			if(longitud>maxLon)maxLon=longitud;
			if(longitud<minLon)minLon=longitud;
			if(latitud>maxLat)maxLat=latitud;
			if(latitud<minLat)minLon=latitud;
		}
		
		leyendoPropiedades = false;
		leyendoGeometria = false;
		crearObjComparendo = false;
		arreglo.agregar(rta);
	}
	
	public IArregloDinamico<Comparendo> darArreglo()
	{
		return arreglo;
	}
	public Comparendo darComparendoMayorOb()
	{
		return comparendoConMayorObjectId;
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
	public void iniciarLectura(JsonGsonProcessing objetoJsonGson, IArregloDinamico<Comparendo> pArreglo)
	{
		arreglo = pArreglo;
		// Inicializar el objeto de procesamiento con el nombre del archivo JSON o comparendos GEOJSON 

		objetoJsonGson.processingJSONFile();
	}

}


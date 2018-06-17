package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

import java.lang.reflect.Field;
import services.DatabaseConnectionService;
import utils.Dato;
import utils.Transformacion;
import utils.Valor;
import utils.Clase;
import utils.Atributo;
import deepcopy.*;
import narytree.N_AryTree;

public class AdminDashboardService {
	private Clase clase;
	private N_AryTree<String> result = new N_AryTree<String>();
	
	public AdminDashboardService() {
		
	}
	
	public ArrayList<String> getAvailableDatabases() {
		ArrayList<String> availableDatabases = new ArrayList<>();
		
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306","admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getCatalogs();
			
			while(resultSet.next()) {
				availableDatabases.add(resultSet.getString("TABLE_CAT"));
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return availableDatabases;
	}
	
	public ArrayList<String> getDatabaseTables(String database){
		ArrayList<String> databaseTables = new ArrayList<>();
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getTables(null, null, "%", null);
			
			while(resultSet.next()) {
				databaseTables.add(resultSet.getString(3));
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return databaseTables;
	}
	
	public ArrayList<ArrayList<String>> getTableContent(String database, String table){
		ArrayList<ArrayList<String>> tableContent = new ArrayList<ArrayList<String>>();
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getColumns(null, null, table, null);
			Map<Integer, String> jdbcMappings = getAllJdbcTypeNames();
			ArrayList<String> row = new ArrayList<>();
			
			//String columnName;
			//String dataType;
			//String columnSize;
			//String decimalDigits;
			//String isNullable;
			//String isAutoIncrement;
			
			row.add("COLUMN_NAME");
			row.add("DATA_TYPE");
			row.add("COLUMN_SIZE");
			row.add("DECIMAL_DIGITS");
			row.add("IS_NULLABLE");
			row.add("IS_AUTOINCREMENT");
			
			tableContent.add(row);
			
			while(resultSet.next()) {
				row = new ArrayList<>();
				
				//columnName 		= resultSet.getString("COLUMN_NAME");
				//dataType 		= resultSet.getString("DATA_TYPE");
				//columnSize 		= resultSet.getString("COLUMN_SIZE");
				//decimalDigits 	= resultSet.getString("DECIMAL_DIGITS");
				//isNullable 		= resultSet.getString("IS_NULLABLE");
				//isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT");
				
				row.add(resultSet.getString("COLUMN_NAME"));
				row.add(jdbcMappings.get(resultSet.getInt("DATA_TYPE")));
				row.add(resultSet.getString("COLUMN_SIZE"));
				row.add(resultSet.getString("DECIMAL_DIGITS"));
				row.add(resultSet.getString("IS_NULLABLE"));
				row.add(resultSet.getString("IS_AUTOINCREMENT"));
				
				tableContent.add(row);
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return tableContent;
	}
	
	public String getDecisionTree(ArrayList<ArrayList<String>> tableSet) {
		ArrayList<ArrayList<String>> tableContent = new ArrayList<ArrayList<String>>();
		String decisionTree = "";
		LinkedHashMap<String,Set<String>> differentElements = new LinkedHashMap<String,Set<String>>();
		HashMap<String,Atributo> atributos = new HashMap<>();
		
		String database = tableSet.get(0).get(0);
		if(database.equals("spotify")) {	
			try {
				DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
				Connection connection = dbConnection.getConnection();
				
				String attributeBuilder = "";
				String relation = "";
				String attribute = "";
				
				/*** Elementos para ID3 ***/
				String className = "";
				boolean check = true;
				
				for(int i=0; i < tableSet.size(); i++) {
					relation = tableSet.get(i).get(1);
					attribute = tableSet.get(i).get(2);
					
					if(i == tableSet.size()-1 || tableSet.size() == 1) {
						attributeBuilder += " " + relation + "." + attribute + " ";
					}
					else {
						attributeBuilder += " " + relation + "." + attribute + " , ";
					}
					if(tableSet.get(i).get(4).equals("true") && check) {
						className = tableSet.get(i).get(1) + "." + tableSet.get(i).get(2);
						check = false;
					}
				}
				
				System.out.println("\n==============================================================");
				System.out.println("QUERY: \n");
				String queryString = "SELECT" + attributeBuilder +
						"\nFROM user, playlist_has_followers, playlist, playlist_has_songs, song, genre, region, artist" +
						"\nWHERE region.idregion = user.idregion" +
						"\nAND user.email = playlist_has_followers.email" +
						"\nAND playlist_has_followers.idplaylist = playlist.idplaylist" +
						"\nAND playlist.idplaylist = playlist_has_songs.idplaylist" + 
						"\nAND playlist_has_songs.idsong = song.idsong" +
						"\nAND song.idgenre = genre.idgenre" + 
						"\nAND song.idartist = artist.idartist" +
						"\nLIMIT 5000;";
				
				System.out.println(queryString);
				
				PreparedStatement queryUsers = null;
				ResultSet result;
				
				queryUsers = connection.prepareStatement(queryString);
				result = queryUsers.executeQuery();
				ResultSetMetaData rsmd = result.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				
				if(result.isBeforeFirst()) {
					ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
					ArrayList<String> attributes =	new ArrayList<>();
					ArrayList<String> relations =   new ArrayList<>();
					
					while(result.next()){
						ArrayList<String> tuple = new ArrayList<>();
						for(int i=1; i<=columnsNumber; i++) {
							attributes.add(rsmd.getColumnName(i));
							relations.add(rsmd.getTableName(i));
							tuple.add(result.getString(i));
						}
						dataSet.add(tuple);
					}
					
					Transformacion transform = new Transformacion(dataSet, attributes, relations);
					tableContent = transform.transformData();
					differentElements = transform.getElementSet();
					
					System.out.println("==============================================================");
					System.out.println("KEYSET");
					for(String key : differentElements.keySet()) {
						System.out.print("  " + key + ":   ");
						for(String x : differentElements.get(key)) {
							System.out.print("  " + x + "  ");
						}
						System.out.println("");
					}
					System.out.println("==============================================================");
					System.out.println("ARRAYSET");
					String[] header = differentElements.keySet().toArray(new String[differentElements.size()]);
					for(String head : header) {
						System.out.println("  " + head);
					}
					
					System.out.println("==============================================================");
					System.out.println("AGREGANDO CLASE Y ATRIBUTOS");
					System.out.print("  " + className + ":   ");
					clase = new Clase(className);
					for(String x : differentElements.get(className)) {
						clase.agregaValor(x);
					}
					System.out.println("***Clase Agregado***");
					
					ArrayList<Dato> tabla= new ArrayList<>();
					
					for(String key : differentElements.keySet()) {
						if(!key.equals(className)) {
							System.out.print("  " + key + ":   ");
							HashMap<String, Valor> valores = new HashMap<>();
							for(String x : differentElements.get(key)) {
								Clase copiaClase = (Clase) DeepCopy.copy(clase);
								Valor valor = new Valor(copiaClase,x);
								valores.put(x, valor);
							}
							
							Atributo atributo = new Atributo(key, valores);
							atributos.put(key, atributo);
							System.out.println("***Atributo Agregado***");
						}
					}
					//System.out.println("==============================================================\n\n");
					//*** SECCION DE SEGMENTACION ***//
					for(int i=0; i<tableContent.size(); i++) {
						Dato dato = new Dato();
						//System.out.print("Dato: [ ");			
						for(int j=0; j < tableContent.get(i).size(); j++) {
							String value = tableContent.get(i).get(j);
							if(!header[j].equals(className)) {
								if(atributos.get(header[j]).obtenerValor(value).obtenerClase() != null)
	                                //System.out.print("Clase: " + atributos.get(header[j]).obtenerValor(value).obtenerClase() + " ");
								
								atributos.get(header[j]).obtenerValor(value).incrementaFrecuencia(tableContent.get(i).get(header.length-1));
								dato.agregarColumna(header[j], value);
	                                
								//System.out.print(header[j] + "=" + value + " " + atributos.get(header[j]).obtenerValor(value).obtenerFrecuencia(tableContent.get(i).get(header.length-1)) + " ");
								
								if(header.length > 0){
	                                dato.agregarColumna(header[header.length-1], tableContent.get(i).get(header.length-1));
	                                //System.out.print(header[header.length-1] + "=" + tableContent.get(i).get(header.length-1) + " ");   
	                                clase.incrementaFrecuencia(tableContent.get(i).get(header.length-1));
	                            }
	                            tabla.add(dato);
	                            //System.out.println("]");
							}
						}	
						//System.out.println("");
					}
					//printTabla(tabla);
					
					System.out.println("==============================================================");
					String temp;
			        System.out.println("\n***Prueba de frecuencias por atributo-valor***");
			        Iterator iterador = atributos.keySet().iterator();
			        while(iterador.hasNext()){
			            Atributo testAtributo = atributos.get((String)iterador.next());
			            HashMap<String, Valor> testValores = testAtributo.obtenerValores();
			            Iterator iterador2 = testValores.keySet().iterator();
			            while(iterador2.hasNext()){
			                Valor testValor = testValores.get((String)iterador2.next());
			                Clase testClase = testValor.obtenerClase();
			                Iterator iterador3 = testClase.obtenerValores().iterator();
			                while(iterador3.hasNext()){
			                   temp = (String)iterador3.next();
			                   System.out.println("Atributo: " + testAtributo.obtenerNombre() +  "  Valor de Atributo: " + testValor.obtenerNombre() + " Valor de Clase: " + temp + "  Frecuencia: " + testClase.obtenerFrecuencia(temp));
			                }
			            }
			        }
			        
			        System.out.println("==============================================================");
			        System.out.println("\n***Calculando la Entropia Binaria para cada atributo***");
			        /*** CALCULANDO LA ENTROPIA BINARIA PARA CADA ATRIBUTO ***/
			        iterador = atributos.keySet().iterator();
			        Object[] vals = clase.obtenerValores().toArray();
			        System.out.println("Clase valor: " + vals[0]);
			        System.out.println("Clase valor: " + vals[1]);
			        
			        double p = (double) clase.obtenerFrecuencia((String)vals[0]);
			        double n = (double) clase.obtenerFrecuencia((String)vals[1]);
			        
			        System.out.println("Valor de p: " + p);
			        System.out.println("Valor de n: " + n);
			        double valueLogp = (p/(p+n)); 
			        double valueLogn = (n/(p+n));
			        double informacion = -valueLogp * (Math.log(valueLogp)/Math.log(2)) -valueLogn *(Math.log(valueLogn)/Math.log(2));
			        double maxganancia = 0;
			        String max = "";
			        
			        while(iterador.hasNext()){
			        	System.out.println("*****************************");
			            Atributo testAtributo = atributos.get((String)iterador.next());
			            testAtributo.calcularEntropia((String)vals[0], (String)vals[1]);
			            testAtributo.calcularGanancia(informacion);
			            if(testAtributo.obtenerGanancia() > maxganancia){
			                maxganancia = testAtributo.obtenerGanancia();
			                max = testAtributo.obtenerNombre();
			            }
			        }
			        
			        System.out.println("\nEl atributo con maxima ganancia es: " + max + " Ganancia: " + maxganancia);
			        
			        /*** CONSTRUCCION DEL ARBOL DE DECISIONES ***/
			        N_AryTree<String> arbolDecision = new N_AryTree<>();
			        /*Agregamos el nodo padre */
			        arbolDecision.setData(max);
			        /*Agregamos los nodos hijos, que son los valores del atributo con ganancia maxima*/
			        Atributo seleccionado = atributos.get(max);
			        iterador = seleccionado.obtenerValores().keySet().iterator();
			        while(iterador.hasNext()){
			        	System.out.println("\n==========================================================");
			            String nombreSel = seleccionado.obtenerValor((String)iterador.next()).obtenerNombre();
			            System.out.println("Valor atributo seleccionado: " + nombreSel);
			            /*** CONSTRUCCION DE NUEVAS TABLAS DADO UN CRITERIO DE SELECCION + VALOR ***/
			            ArrayList<Dato> nuevaTabla = creaNuevaTabla(tabla, max, nombreSel);
			            //printTabla(nuevaTabla);
			            //printTabla(creaNuevaTabla(tabla, max, nombreSel));
			            if(seleccionado.obtenerValor(nombreSel).consultarEntropia() == 0){
			                System.out.println("Es una Hoja");
			                List<N_AryTree<String>> leaf = new ArrayList<>();
			                leaf.add(new N_AryTree<String>(seleccionado.obtenerValor(nombreSel).obtenerClase().obtenerDominante()));
			                arbolDecision.addChild(new N_AryTree(nombreSel, leaf));
			            }
			            else{
			                /***APLICAMOS EL PROCESO ANTERIOR DE FORMA RECURSIVA ***/
			                System.out.println("Requiere de mas divisiones");
			                HashMap<String, Atributo> nuevoMapa = creaNuevoMapa(nuevaTabla, max, atributos);
			                arbolDecision.addChild(new N_AryTree(nombreSel, particion(nuevaTabla, nuevoMapa)));
			            }
			        }
			        /* Imprimimos el arbol de decision*/
			        System.out.println("\n\n***** ARBOL DE DECISION (ID3) ****\n\n");
			        arbolDecision.print("", true, arbolDecision);
			        decisionTree = serialize(arbolDecision);
			        this.result = arbolDecision;
				}
				else {
					System.out.println("Cursor is not before the first record or there are no rows in Result Set");
				}
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return decisionTree;
	}
	
	public List<N_AryTree<String>> particion(ArrayList<Dato> tabla, HashMap<String, Atributo> mapa){
        List<N_AryTree<String>> tree  = new ArrayList<>();
        
        if(!tabla.isEmpty()){
        	if(!mapa.isEmpty()){
	        		/*** CALCULANDO LA ENTROPIA BINARIA PARA CADA ATRIBUTO ***/
	                Iterator iterador = mapa.keySet().iterator();
	                Object[] vals = clase.obtenerValores().toArray();
	                System.out.println("Clase valor: " + vals[0]);
	    	        System.out.println("Clase valor: " + vals[1]);
	    	        
	    	        double p = (double) clase.obtenerFrecuencia((String)vals[0]);
	    	        double n = (double) clase.obtenerFrecuencia((String)vals[1]);
	    	        
	    	        System.out.println("Valor de p: " + p);
	    	        System.out.println("Valor de n: " + n);
	    	        double valueLogp = (p/(p+n)); 
	    	        double valueLogn = (n/(p+n));
	    	        double informacion = -valueLogp * (Math.log(valueLogp)/Math.log(2)) -valueLogn *(Math.log(valueLogn)/Math.log(2));
	    	        System.out.println("Información: " + informacion);
	    	        double maxganancia = 0;
	    	        String max = "";
	            
	                while(iterador.hasNext()){
	                	System.out.println("*****************************");
	                    Atributo testAtributo = mapa.get((String)iterador.next());
	                    testAtributo.calcularEntropia((String)vals[0], (String)vals[1]);
	                    testAtributo.calcularGanancia(informacion);
	                    if(testAtributo.obtenerGanancia() >= maxganancia){
	                        maxganancia = testAtributo.obtenerGanancia();
	                        max = testAtributo.obtenerNombre();
	                    }
	                }
	                
	                System.out.println("\nEl atributo con maxima ganancia es: " + max + " Ganancia: " + maxganancia);
	                
	                /*** CONSTRUCCION DEL ARBOL DE DECISIONES ***/
	                N_AryTree<String> arbolDecision = new N_AryTree<>();
	                /*Agregamos el nodo padre */
	                arbolDecision.setData(max);
	                /*Agregamos los nodos hijos, que son los valores del atributo con ganancia maxima*/
	                Atributo seleccionado = mapa.get(max);
	                iterador = seleccionado.obtenerValores().keySet().iterator();
	                
	                while(iterador.hasNext()){
	                	System.out.println("\n==========================================================");
	    	            String nombreSel = seleccionado.obtenerValor((String)iterador.next()).obtenerNombre();
	    	            System.out.println("Valor atributo seleccionado: " + nombreSel);
	                    /*** CONSTRUCCION DE NUEVAS TABLAS DADO UN CRITERIO DE SELECCION + VALOR ***/
	                    ArrayList<Dato> nuevaTabla = creaNuevaTabla(tabla, max, nombreSel);
	                    //printTabla(creaNuevaTabla(tabla, max, nombreSel));
	                    if(seleccionado.obtenerValor(nombreSel).consultarEntropia() == 0){
	                        System.out.println("Es una Hoja");
	                        List<N_AryTree<String>> leaf = new ArrayList<>();
	                        leaf.add(new N_AryTree<String>(seleccionado.obtenerValor(nombreSel).obtenerClase().obtenerDominante()));
	                        arbolDecision.addChild(new N_AryTree(nombreSel, leaf));
	                    }
	                    else{
	                        /***APLICAMOS EL PROCESO ANTERIOR DE FORMA RECURSIVA ***/
	                        System.out.println("Requiere de mas divisiones");
	                        HashMap<String, Atributo> nuevoMapa = creaNuevoMapa(nuevaTabla, max, mapa);
	                        arbolDecision.addChild(new N_AryTree(nombreSel, particion(nuevaTabla, nuevoMapa)));
	                    }
	                }
	                tree.add(arbolDecision);	
        	}
        	else {
        		Object[] vals = clase.obtenerValores().toArray();
                System.out.println("Clase valor: " + vals[0]);
    	        System.out.println("Clase valor: " + vals[1]);
    	        
    	        double p = (double) clase.obtenerFrecuencia((String)vals[0]);
    	        double n = (double) clase.obtenerFrecuencia((String)vals[1]);
    	        
    	        N_AryTree<String> arbolDecision = new N_AryTree<>();
    	        if(p >= n) {
    	        	/*Agregamos el nodo padre */
	                arbolDecision.setData((String)vals[0]);
    	        }
    	        else {
    	        	arbolDecision.setData((String)vals[1]);
    	        }
    	        tree.add(arbolDecision);
        	}
        }
        return tree;
    }
    
    public HashMap<String, Atributo> creaNuevoMapa(ArrayList<Dato> tabla, String seleccionado, HashMap<String, Atributo> viejoMapa){
        HashMap<String, Atributo> mapa = (HashMap<String, Atributo>) DeepCopy.copy(viejoMapa);
        if(!mapa.isEmpty()) {
	        /* Quitamos del mapa el atributo seleccionado */
	        mapa.remove(seleccionado);
	        
	        /*Reseteamos el total de las frecuencias almacenadas a cero */
	        clase.reseteaFrecuencias();
	        
	        /* Reseteamos todas las frecuencias almacenadas a cero */
	        for(String nomAtributo : mapa.keySet()){
	        	System.out.println("(Mapa) Nombre Atributo: " + nomAtributo);
	            Atributo atributo = mapa.get(nomAtributo);
	            atributo.reseteaFrecuencias();
	        }
	        
	        /* Llenamos el nuevo mapa con las nuevas frecuencias */
	        for(Dato dato: tabla){
	            for(String columna : dato.obtenerColumnas()){
	                if(!columna.equals(clase.obtenerNombre())){
	                        mapa.get(columna).obtenerValor(dato.obtenerValor(columna)).incrementaFrecuencia(dato.obtenerValor(clase.obtenerNombre()));
	                }
	                else{
	                    clase.incrementaFrecuencia(dato.obtenerValor(columna));
	                }
	            }
	        }
	        
	        /* Quitamos todos los valores cuya frecuencia es igual a cero */
	        /* Verificamos que el nuevo mapa se haya llenado exitosamente */
	        System.out.println("==============================================================");
	        System.out.println("\n***Prueba de frecuencias por atributo-valor***");
	        Iterator iterador = mapa.keySet().iterator();
	        ArrayList<String> removerA = new ArrayList<>();
	        ArrayList<String> removerV = new ArrayList<>();
	        int frecuenciaAcum;
	        int frecuencia;
	        
	        for(String tempA : mapa.keySet()){
	            Atributo testAtributo = mapa.get(tempA);
	            HashMap<String, Valor> testValores = testAtributo.obtenerValores();
	            removerV = new ArrayList<>();
	            for(String tempV : testValores.keySet()){         
	                frecuenciaAcum = 0;
	                Valor testValor = testValores.get(tempV);
	                Clase testClase = testValor.obtenerClase();
	                for(String tempC : testClase.obtenerValores()){
	                   frecuencia = testClase.obtenerFrecuencia(tempC);
	                   frecuenciaAcum += frecuencia;
	                   if(frecuencia != 0)
	                    System.out.println("Atributo: " + testAtributo.obtenerNombre() +  "  Valor de Atributo: " + testValor.obtenerNombre() + " Valor de Clase: " + tempC + "  Frecuencia: " + frecuencia);
	                }
	                if(frecuenciaAcum == 0)
	                    removerV.add(tempV);
	            }
	            for(int i=0; i<removerV.size(); i++)
	                testValores.remove(removerV.get(i));
	            
	            if(testAtributo.obtenerValores().isEmpty())
	                removerA.add(tempA);
	        }
	        
	        for(int i=0; i<removerA.size(); i++)
	                mapa.remove(removerV.get(i));
	        
	        System.out.println("\n***Fin de prueba de frecuencias por atributo-valor***");
	        }
        else {
        	System.out.println("Empty Map");
        }
        return mapa;
    }
    
   public void printTabla(ArrayList<Dato> tabla){
       Set<String> columnas;
       Iterator iterador;
       System.out.println("\n ********************************");
       for(Dato dato: tabla){
           columnas = dato.obtenerColumnas();
           iterador = columnas.iterator();
           while(iterador.hasNext()){
               String atributo = (String) iterador.next();
               String valor = dato.obtenerValor(atributo);
               System.out.print(" Atributo: " + atributo + " Dato: " + valor);
           }
           System.out.println("");
       }
       System.out.println(" ********************************");
   }
   
   public ArrayList<Dato> creaNuevaTabla(ArrayList<Dato> viejaTabla, String seleccionado, String valorAtributo){
        /***Hacemos una copia del objeto, para poder modificar los atributos de los elementos del HashMap***/
        ArrayList<Dato> nuevaTabla = new ArrayList<>();
        /*** PASO1: Quitar y seleccionar las tuplas que cumplen con el valorAtributo***/
        for(int i=0;i <viejaTabla.size(); i++){
            Dato dato = (Dato) DeepCopy.copy(viejaTabla.get(i));
            if(dato.obtenerValor(seleccionado).equals(valorAtributo)){
                dato.quitarColumna(seleccionado);
                nuevaTabla.add(dato);
            }
        }
        return nuevaTabla;
	}
   
	public String serialize(N_AryTree<String> tree) {
	   	StringBuilder sb = new StringBuilder();
   		sb.append(tree.getData());
   		sb.append(",");
	   	if(tree.hasChildren()) {   		
	   		for(int i = 0; i < tree.getNumberOfChildren(); i++) {
	       		sb.append(serialize(tree.getChildAt(i)));
	       	}
	   	}
	   	sb.append(")");
	   	return sb.toString();
	}
	
	public Map<Integer, String> getAllJdbcTypeNames() {

	    Map<Integer, String> result = new HashMap<Integer, String>();

	    for (Field field : Types.class.getFields()) {
	        try {
				result.put((Integer)field.get(null), field.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	    }

	    return result;
	}
	
	public N_AryTree<String> getDecisionTree(){
		return this.result;
	}
}

package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Transformacion {
	private ArrayList<ArrayList<String>> originalSet;
	private ArrayList<String> attributes;
	private ArrayList<String> tables;
	private LinkedHashMap<String,Set<String>> elements = new LinkedHashMap<String,Set<String>>();
	
	public Transformacion (ArrayList<ArrayList<String>> originalSet, ArrayList<String> attributes, ArrayList<String> tables) {
		this.originalSet = originalSet;
		this.attributes = attributes;
		this.tables = tables;
		for(int i =0; i<attributes.size(); i++) {
			this.elements.put(tables.get(i) + "." + attributes.get(i), new HashSet<String>());
		}
	}
	
	public ArrayList<ArrayList<String>> transformData() {
		for(int i=0; i < originalSet.size(); i++) {
			for(int j=0; j < originalSet.get(i).size(); j++) {
				switch(tables.get(j)) {
					case "user":
						if(attributes.get(j).equals("idregion")) {
							Integer id = Integer.parseInt(originalSet.get(i).get(j));
							if(id>0 && id<=2) {
								originalSet.get(i).set(j,"African");
							}
							else if(id>3 && id<=7) {
								originalSet.get(i).set(j,"Asian");
							}
							else if(id>7	&& id<=9) {
								originalSet.get(i).set(j,"European");
							}
							else if(id>9 && id<=12) {
								originalSet.get(i).set(j,"American");
							}
							else{
								originalSet.get(i).set(j,"Not defined");
							}
						}
						else if(attributes.get(j).equals("birthday")) {
							String date = originalSet.get(i).get(j);
							java.sql.Date dat = java.sql.Date.valueOf(date);
							Calendar cal = Calendar.getInstance();
							cal.setTime(dat);
							
							int year = cal.get(Calendar.YEAR);
							
							if(year >= 1955 && year <= 1965) {
								originalSet.get(i).set(j, "Baby Boomer");
							}
							else if(year >= 1966 && year <= 1976) {
								originalSet.get(i).set(j, "X Generation");
							}
							else if(year >= 1977 && year <= 1994) {
								originalSet.get(i).set(j, "Millenium");
							}
							else if(year >= 1995) {
								originalSet.get(i).set(j, "Z Generation");
							}
							else {
								originalSet.get(i).set(j, "Older Generation");
							}
						}
						else {
							//System.out.println("It is not possible to apply a transformation to this attribute");
						}
						break;
					case "song":
						if(attributes.get(j).equals("year")) {
							Integer year = Integer.parseInt(originalSet.get(i).get(j));
							if(year < 1960) {
								originalSet.get(i).set(j, "Oldies");
							}
							else if(year >= 1960 && year < 1970) {
								originalSet.get(i).set(j, "60's");
							}
							else if(year >= 1970 && year < 1980) {
								originalSet.get(i).set(j, "70's");
							}
							else if(year >= 1980 && year < 1990) {
								originalSet.get(i).set(j, "80's");
							}
							else if(year >= 1990 && year < 2000) {
								originalSet.get(i).set(j, "90's");
							}
							else if(year >= 2000 && year < 2010) {
								originalSet.get(i).set(j, "00's");
							}
							else {
								originalSet.get(i).set(j, "Newer Ones");
							}
						}
						else if(attributes.get(j).equals("plays")) {
							Integer plays = Integer.parseInt(originalSet.get(i).get(j));
							if(plays >= 0 && plays < 5000) {
								originalSet.get(i).set(j, "Very Low Rating");
							}
							else if(plays >= 5000 && plays < 15000) {
								originalSet.get(i).set(j, "Low Rating");
							}
							else if(plays >= 15000 && plays < 30000) {
								originalSet.get(i).set(j, "Regular Rating");
							}
							else if(plays >= 30000 && plays < 45000) {
								originalSet.get(i).set(j, "High Rating");
							}
							else if (plays >= 45000) {
								originalSet.get(i).set(j, "Turning into new Hit");
							}
							else {
								originalSet.get(i).set(j, "Error");
							}
						}
						else {
							//System.out.println("It is not possible to apply a transformation to this attribute");
						}
						break;
					case "region":
						if(attributes.get(j).equals("idregion")){
							Integer id = Integer.parseInt(originalSet.get(i).get(j));
							if(id>0 && id<=2) {
								originalSet.get(i).set(j,"Africa");
							}
							else if(id == 3) {
								originalSet.get(i).set(j,"Antartic");
							}
							else if(id>3 && id<=7) {
								originalSet.get(i).set(j,"Asia");
							}
							else if(id>7	&& id<=9) {
								originalSet.get(i).set(j,"Europe");
							}
							else if(id>9 && id<=12) {
								originalSet.get(i).set(j,"America");
							}
							else{
								originalSet.get(i).set(j,"Oceania");
							}
						}
						else {
							//System.out.println("It is not necessary to apply a reduction method");
						}
					case "artist":
						if(attributes.get(j).equals("idregion")) {
							Integer id = Integer.parseInt(originalSet.get(i).get(j));
							if(id>0 && id<=2) {
								originalSet.get(i).set(j,"African");
							}
							else if(id>3 && id<=7) {
								originalSet.get(i).set(j,"Asian");
							}
							else if(id>7	&& id<=9) {
								originalSet.get(i).set(j,"European");
							}
							else if(id>9 && id<=12) {
								originalSet.get(i).set(j,"American");
							}
							else{
								originalSet.get(i).set(j,"Not defined");
							}
						}
						else {
							//System.out.println("It is not possible to apply a transformation to this attribute");
						}
						break;
					default:
						//System.out.println("It is not possible to apply a transformation to this attribute");
						break;
				}
				this.elements.get(tables.get(j) + "." + attributes.get(j)).add(originalSet.get(i).get(j));
			}
		}
		return this.originalSet;
	}
	
	public LinkedHashMap<String,Set<String>> getElementSet() {
		return this.elements;
	}
}

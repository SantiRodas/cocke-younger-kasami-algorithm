package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class System {
	
	// --------------------------------------------------------------------------------
	
	// Attributes of the system class

	ArrayList<String> factors;

	String[][] mainMatrix;

	String[][] resultMatrix;
	
	HashMap<String,List<String>> map;
	
	// --------------------------------------------------------------------------------
	
	// Constructor method
	
	/**
	 * Constructor method to create a object into another class.
	 * @param matrix String information with the CYK algorithm
	 * @param stringLength Size of the string
	 */
	
	public System(String[][] matrix, int stringLength) {	
		
		mainMatrix = matrix;
		
		resultMatrix = new String[stringLength][stringLength];
		
		factors = new ArrayList<String>();
		
		map = new HashMap<>();
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Get's method of the system class
	
	/**
	 * Method to get the factor of the system.
	 * @return ArrayList with all the information
	 */
	
	public ArrayList<String> getFactors() {
		return factors;
	}
	
	/**
	 * Method to get the main matrix of the system.
	 * @return String matrix with all the information
	 */
	
	public String[][] getMainMatrix() {
		return mainMatrix;
	}
	
	/**
	 * Method to get the result matrix of the system.
	 * @return String matrix with all the information
	 */
	
	public String[][] getResultMatrix() {
		return resultMatrix;
	}
	
	/**
	 * Method to get the map of the system.
	 * @return String haspMap with all the information
	 */
	
	public HashMap<String, List<String>> getMap() {
		return map;
	}
	
	// --------------------------------------------------------------------------------
	
	// Set's method of the system class
	
	/**
	 * Method to set the information of the factor.
	 * @param factors ArrayList of String's
	 */
	
	public void setFactors(ArrayList<String> factors) {
		this.factors = factors;
	}
	
	/**
	 * Method to set the information of the main matrix.
	 * @param mainMatrix Matrix of string
	 */
	
	public void setMainMatrix(String[][] mainMatrix) {
		this.mainMatrix = mainMatrix;
	}
	
	/**
	 * Method to set the information of the result matrix.
	 * @param resultMatrix Matrix of string
	 */
	
	public void setResultMatrix(String[][] resultMatrix) {
		this.resultMatrix = resultMatrix;
	}
	
	/**
	 * Method to set the information of the map.
	 * @param map HashMap of the string
	 */
	
	public void setMap(HashMap<String, List<String>> map) {
		this.map = map;
	}
	
	// --------------------------------------------------------------------------------
	
	// Add value to the map method
	
	/**
	 * Method to add a value to the map of the system.
	 */

	public void addValueMap() {
		
		String aux = "";
		
		factors = new ArrayList<String>();
		
		for (int a = 0; a < mainMatrix.length; a ++) {
			
			List<String> results = new ArrayList<String>();
			
			for (int b = 0; b < mainMatrix[a].length; b ++) {
				
				if(b == 0) {
					
					factors.add(mainMatrix[a][b]);
					
					aux = mainMatrix[a][b];	
					
				}else {
					
					String[] resultsArray = mainMatrix[a][b].split("\\|");
					
					results = Arrays.asList(resultsArray);	
					
				}
				
			}
			
			addValueAux(aux,results);
			
		}		
		
	}	
	
	// --------------------------------------------------------------------------------
	
	// add value AUX method
	
	/**
	 * Method AUX to add a value into the system.
	 * @param aux String with some information from the graphic interface
	 * @param results List of String's with all the information of the system
	 */

	public void addValueAux(String aux, List<String> results) {
		
		map.put(aux, results);
		
	}
	
	// --------------------------------------------------------------------------------
	
	// CYK algorithm method
	
	/**
	 * Method to use the CYK algorithm into the system (Take the Pseudo code).
	 * @param W String with the information that the user want to validate
	 */

	public void CYKAlgorithm(String W) {
		
		for (int b = 2; b <= W.length(); b ++) {
			
			for (int a = 1; a <= (W.length() - b + 1); a ++) {
				
				List<String> tuples = new ArrayList<String>();
				
				for (int c = 1; c <= b - 1; c ++) {
					
					String[] partition = resultMatrix[a - 1][c - 1].split(",");
					
					String[] partition1 = resultMatrix[(a - 1) + (c)][(b - 1) - (c)].split(",");
					
					String[] resultCartesianProduct = product(partition,partition1);
					
					tuples.addAll(Arrays.asList(resultCartesianProduct));	
					
				}
				
				resultMatrix[a - 1][b - 1] = generate(tuples);		
				
			}
			
		}
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Contains feature method
	
	/**
	 * Method to know if a part of one String it's into the system.
	 * @return boolean data where true represents it's into the system, and false not
	 */

	public boolean containsFeature() {
		
		String[] correctValue = resultMatrix[0][resultMatrix[0].length-1].split(",");
		
		boolean validation = false;
		
		for (int a = 0; a < correctValue.length && !validation; a ++) {
			
			if(correctValue[a].equals(factors.get(0))) {
				
				validation = true;
				
			}
			
		}
		
		return validation;
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Add column method
	
	/**
	 * Method to add a column in the first part of the system.
	 * @param W String with the information that the user want to validate
	 */

	public void addColumn(String W){
		
		for (int b = 1; b <= W.length(); b ++) {
			
			String information = "";
			
			for (int c = 0; c < factors.size(); c ++) {
				
				String aux = Character.toString(W.charAt(b - 1));
				
				String key = factors.get(c);
				
				if (map.get(key).contains(aux)) {
					
					information += key + ",";
					
				}
				
			}
			
			if (information != "" && information.charAt(information.length() - 1) == ',') {
				
				information = information.substring(0, information.length() - 1);
				
			}
			
			resultMatrix[b - 1][0] = information;
			
		}
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Generate method
	
	/**
	 * Method to generate the CYK information with the information from the graphic interface.
	 * @param tuples Couple of string's into a List
	 * @return String the a new information
	 */

	public String generate(List<String> tuples) {
		
		List<String> newTuple = new ArrayList<String>();
		
		String finalInformation = "";

		for (int a = 0; a < tuples.size(); a ++) {
			
			for (int b = 0; b < factors.size(); b ++) {
				
				String key = factors.get(b);

				if (map.get(key).contains(tuples.get(a))) {
					
					if (!newTuple.contains(key)) {
						
						newTuple.add(key);
						
						finalInformation += key + ",";
						
					}

				}
				
			}

		}
		
		if (finalInformation != "" && finalInformation.charAt(finalInformation.length() - 1) == ',') {
			
			finalInformation = finalInformation.substring(0, finalInformation.length() - 1);
			
		}
		
		return finalInformation;
		
	}
	
	// --------------------------------------------------------------------------------
	
	// Product method
	
	/**
	 * Method to calculate the product between the partition 1 and 2.
	 * @param partition1 Array of String with the information of the first partition
	 * @param partition2 Array of String with the information of the second partition
	 * @return Array of String the product calculate between the two param's
	 */

	public String[] product(String[] partition1, String[] partition2) {

		int length = partition1.length * partition2.length;
		
		String[] tuples = new String[length];
		
		int aux = 0;
		
		for (int a = 0; a < partition1.length; a ++) {
			
			for (int b = 0; b < partition2.length; b ++) {
				
				tuples[aux] = partition1[a] + "" + partition2[b];
				
				aux ++;
				
			}
			
		}

		return tuples;
		
	}
	
	// --------------------------------------------------------------------------------

}

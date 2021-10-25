package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class System {
	
	// --------------------------------------------------------------------------------

	ArrayList<String> factors;

	String[][] mainMatrix;

	String[][] resultMatrix;
	
	HashMap<String,List<String>> map;
	
	// --------------------------------------------------------------------------------
	
	public System(String[][] matrix, int stringLength) {	
		
		mainMatrix = matrix;
		
		resultMatrix = new String[stringLength][stringLength];
		
		factors = new ArrayList<String>();
		
		map = new HashMap<>();
		
	}
	
	// --------------------------------------------------------------------------------
	
	public ArrayList<String> getFactors() {
		return factors;
	}
	
	public String[][] getMainMatrix() {
		return mainMatrix;
	}
	
	public String[][] getResultMatrix() {
		return resultMatrix;
	}
	
	public HashMap<String, List<String>> getMap() {
		return map;
	}
	
	// --------------------------------------------------------------------------------
	
	public void setFactors(ArrayList<String> factors) {
		this.factors = factors;
	}
	
	public void setMainMatrix(String[][] mainMatrix) {
		this.mainMatrix = mainMatrix;
	}
	
	public void setResultMatrix(String[][] resultMatrix) {
		this.resultMatrix = resultMatrix;
	}
	
	public void setMap(HashMap<String, List<String>> map) {
		this.map = map;
	}
	
	// --------------------------------------------------------------------------------

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

	public void addValueAux(String aux, List<String> results) {
		
		map.put(aux, results);
		
	}
	
	// --------------------------------------------------------------------------------

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

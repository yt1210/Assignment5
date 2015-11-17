package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		String[] names;
		String[] types;
		String line = "";
		try {
			if (getAttribute) {
				names = tuple.col;
				types = tuple.col1;
				tuple = new Tuple(new ArrayList<Attribute>());
				if ((line = br.readLine()) != null) {
					tuple.col = names;
					tuple.col1 = types;
					tuple.col2 = line.split(",");
					tuple.setAttributeName();
					tuple.setAttributeType();
					tuple.setAttributeValue();
				} else {
					return null;
				}
			} else {
				String nameLine = br.readLine();
				String typeLine = br.readLine();
				String valueLine = br.readLine();
				if (nameLine != null && typeLine != null && valueLine != null) {
					tuple = new Tuple(nameLine, typeLine, valueLine);
					tuple.setAttributeName();
					tuple.setAttributeType();
					tuple.setAttributeValue();
					getAttribute = true;
				} else {
					return null;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tuple;
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}
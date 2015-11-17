package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple leftChildNext;
		do {
			leftChildNext = leftChild.next();
			if (leftChildNext != null) {
				tuples1.add(leftChildNext);
			}
		} while (leftChildNext != null);
		
		Tuple rightChildNext;
		do {
			rightChildNext = rightChild.next();
			if (rightChildNext != null) {
				ArrayList<Attribute> al1 = rightChildNext.getAttributeList();
				for (int i = 0; i < tuples1.size(); i++) {
					ArrayList<Attribute> al2 = tuples1.get(i).getAttributeList();
					for (int j = 0; j < al1.size(); j++) {
						Attribute a1 = al1.get(j);
						for (int k = 0; k < al2.size(); k++) {
							Attribute a2 = al2.get(k);
							if (a1.getAttributeName().equals(a2.getAttributeName()) && a1.getAttributeValue().equals(a2.getAttributeValue())) {
								newAttributeList = new ArrayList<Attribute>();
								newAttributeList.addAll(al1);
								newAttributeList.remove(a1);
								newAttributeList.addAll(al2);
								return new Tuple(newAttributeList);
							}
						}
					}
				}
			}
		} while (rightChildNext != null);
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}
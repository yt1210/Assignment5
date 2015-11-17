package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple next;
		do {
			next = child.next();
			if (next != null) {
				tuplesResult.add(next);
			}
		} while (next != null);
		
		Collections.sort(tuplesResult, new Comparator<Tuple>() {
			@Override
			public int compare(Tuple t1, Tuple t2) {
				int targetIndex = -1;
				for (int i = 0; i < t1.getAttributeList().size(); i++) {
					if (t1.getAttributeName(i).equals(orderPredicate) && t2.getAttributeName(i).equals(orderPredicate)) {
						targetIndex = i;
						break;
					}
				}

				Comparable value1 = (Comparable)t1.getAttributeValue(targetIndex);
				Comparable value2 = (Comparable)t2.getAttributeValue(targetIndex);
				return value1.compareTo(value2);
			}
		});

		if (tuplesResult.size() > 0) {
			newAttributeList = tuplesResult.get(0).getAttributeList();
			tuplesResult.remove(tuplesResult.get(0));
			return new Tuple(newAttributeList);
		} else {
			return null;
		}
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}
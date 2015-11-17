package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple next = child.next();
		if (next != null) {
			ArrayList<Attribute> al = next.getAttributeList();
			ArrayList<Attribute> removeList = new ArrayList<Attribute>();
			for (int i = 0; i < al.size(); i++) {
				Attribute a = al.get(i);
				if (!a.getAttributeName().equals(attributePredicate)) {
					removeList.add(a);
				}
			}
			for (int i = 0; i < removeList.size(); i++) {
				al.remove(removeList.get(i));
			}
		}
		return next;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}

public class Tester {
	public static void main(String[] args){
		
		BSTreeSetTester<Integer> bst = new BSTreeSetTester<Integer>(0);
		for (int i=0; i<10; i++){
			bst.add(i);
		}
		
		bst.displayTree(50);
		
		bst.rebalance();
		bst.displayTree(50);

	}	
}

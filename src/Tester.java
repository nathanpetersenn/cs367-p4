
public class Tester {
	public static void main(String[] args){
		
		BSTreeSetTester<Integer> bst = new BSTreeSetTester<Integer>(-1);
		for (int i=1; i<25; i++){
			bst.add(i);
		}
		
		bst.displayTree(50);
		
		bst.rebalance();
		bst.displayTree(50);

	}	
}

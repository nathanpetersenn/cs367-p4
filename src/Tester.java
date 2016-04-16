
public class Tester {
	public static void main(String[] args){
		
		BSTreeSetTester<Integer> bst = new BSTreeSetTester<Integer>(-1);
		for (int i=1; i<8; i++){
			bst.add(i);
			System.out.println("---------------------------BEFORE REBALANCE");
			bst.displayTree(50);
			bst.rebalance();
			System.out.println("---------------------------AFTER REBALANCE");
			bst.displayTree(50);
		}
		
		//bst.displayTree(50);
		
//		bst.rebalance();
//		bst.displayTree(50);

	}	
}

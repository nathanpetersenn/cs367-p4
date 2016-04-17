
public class Tester {
	public static void main(String[] args){
		
		BSTreeSetTester<Integer> bst = new BSTreeSetTester<Integer>(1);
		
//		int[] arr = new int[]{5, 3, 7, 2, 4, 6, 8};
//		for (int i=0; i<arr.length; i++){
//			bst.add(arr[i]);
//		}
//		bst.rebalance();
//		bst.displayTree(50);
//		System.exit(0);
		
		for (int i=1; i<11; i++){
			bst.add(i);
//			System.out.println("---------------------------BEFORE REBALANCE");
//			bst.displayTree(50);
		}

		//bst.rebalance();
		//System.out.println("---------------------------AFTER REBALANCE");
		bst.displayTree(50);
		//bst.displayTree(50);
		
//		bst.rebalance();
//		bst.displayTree(50);

	}	
}

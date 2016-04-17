
public class Tester {
	public static void main(String[] args){
		
		BSTreeSetTester<Integer> bst = new BSTreeSetTester<Integer>(1);
		
		int[] arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
		for (int i=0; i<arr.length; i++){
			bst.add(arr[i]);
		}
//		bst.rebalance();
//		bst.displayTree(50);
//		System.exit(0);
		
//		for (int i=1; i<8184; i++){
//			bst.add(i);
//			System.out.println("---------------------------BEFORE REBALANCE");
//			bst.displayTree(50);
//		}

		//bst.rebalance();
		//System.out.println("---------------------------AFTER REBALANCE");
		bst.displayTree(50);
		//bst.displayTree(50);
		
//		bst.rebalance();
//		bst.displayTree(50);

	}	
}

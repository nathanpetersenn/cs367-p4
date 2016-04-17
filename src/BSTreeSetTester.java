import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SetTesterADT implementation using a Binary Search Tree (BST) as the data
 * structure.
 *
 * <p>The BST rebalances if a specified threshold is exceeded (explained below).
 * If rebalanceThreshold is non-positive (&lt;=0) then the BST DOES NOT
 * rebalance. It is a basic BStree. If the rebalanceThreshold is positive
 * then the BST does rebalance. It is a BSTreeB where the last B means the
 * tree is balanced.</p>
 *
 * <p>Rebalancing is triggered if the absolute value of the balancedFfactor in
 * any BSTNode is &gt;= to the rebalanceThreshold in its BSTreeSetTester.
 * Rebalancing requires the BST to be completely rebuilt.</p>
 *
 * @author CS367
 */
public class BSTreeSetTester <K extends Comparable<K>> implements SetTesterADT<K>{

    /** Root of this tree */
    BSTNode<K> root;

    /** Number of items in this tree*/
    int numKeys;

    /**
     * rebalanceThreshold &lt;= 0 DOES NOT REBALANCE (BSTree).<br>
     * rebalanceThreshold  &gt;0 rebalances the tree (BSTreeB).
      */
    int rebalanceThreshold;

    /**
     * True iff tree is balanced, i.e., if rebalanceThreshold is NOT exceeded
     * by absolute value of balanceFactor in any of the tree's BSTnodes.Note if
     * rebalanceThreshold is non-positive, isBalanced must be true.
     */
    boolean isBalanced;


    /**
     * Constructs an empty BSTreeSetTester with a given rebalanceThreshold.
     *
     * @param rbt the rebalance threshold
     */
    public BSTreeSetTester(int rbt) {
        root = null;
        numKeys = 0;
        isBalanced = true;
        rebalanceThreshold = rbt;
    }

    /**
     * Add node to binary search tree. Remember to update the height and
     * balancedFactor of each node. Also rebalance as needed based on
     * rebalanceThreshold.
     *
     * @param key the key to add into the BST
     * @throws IllegalArgumentException if the key is null
     * @throws DuplicateKeyException if the key is a duplicate
     */
	public void add(K key) {
		// If key is null, throws an exception
    	if (key == null) throw new IllegalArgumentException();
    	
    	// If root is null, tree was empty, so creates a new root node
    	if (root == null) {
    		root = new BSTNode<K>(key);
    		root.setHeight(1);
    		root.setBalanceFactor(0);
    		numKeys++;
    	// If root exists, adds a new node with key on to the tree
    	} else {
        	add(key, root);
    	}
  
    	// Re-balances the tree if necessary
    	if (!isBalanced && rebalanceThreshold > 0) {
    		rebalance();
    	}
    }
    
	/**
	 * Private companion add method.
	 * 
	 * @param key The key to add into the BST
	 * @param parent The root initially, the parent being looked at.
	 */
    private void add(K key, BSTNode<K> parent) {
    	// If parent's key equals key - duplicate key entry!
    	
    	if (parent.getKey().equals(key)) {
    		throw new DuplicateKeyException();
    		
    	// If parent's key is greater than key, look in left child's subtree
    	} else if (parent.getKey().compareTo(key) > 0) {
    		//System.out.println(key + " is less than " + parent.getKey());
    		parent.setBalanceFactor(parent.getBalanceFactor()+1);
    		if (Math.abs(parent.getBalanceFactor()) >= rebalanceThreshold){
    			isBalanced = false;
    		}
    		// If parent's left child is null, creates a new node and adds it
    		if (parent.getLeftChild() == null) {
    			BSTNode<K> n = new BSTNode<K>(key);
    			n.setHeight(parent.getHeight() + 1);
    			parent.setLeftChild(n);
    			numKeys++;
    		// If parent's left child isn't null,
    		// recursively calls add with the key to the parent's left child 
    		} else {
    			add(key, parent.getLeftChild());
    		}
    	// If parent's key is smaller than key, look in riht child's subtree
    	} else if (parent.getKey().compareTo(key) < 0) {
    		parent.setBalanceFactor(parent.getBalanceFactor()-1);
    		if (Math.abs(parent.getBalanceFactor()) >= rebalanceThreshold){
    			isBalanced = false;
    		}
    		// If parent's right child is null
    		if (parent.getRightChild() == null) {
    			// Create a new node and add it
    			BSTNode<K> n = new BSTNode<K>(key);
    			n.setHeight(parent.getHeight() + 1);
    			parent.setRightChild(n);
    			numKeys++;
    		// If parent's right child isn't null,
    		// recursively calls add with the key to the parent's right child
    		} else {
    			add(key, parent.getRightChild());
    		}
    	}
    }
    
    /**
     * Resets the balance factors for the whole tree. Balance factors are
     * the number of left descendants the curr node has minus the number of
     * right descendants. Starts at the root node and traverses down the
     * entire tree resetting all the balance factors.
     * 
     * @param curr The current node whose balance factor is being reset.
     */
//    private void resetBalanceFactors(BSTNode<K> curr) {
//    	// If curr is null, we're done
//    	if (curr == null) return;
//    	// If both children are null, curr is a leaf node
//    	if (curr.getLeftChild() == null && curr.getRightChild() == null) {
//    		// Sets the balance factor of the leaf to 0 and then we're done
//    		curr.setBalanceFactor(0);
//    		return;
//    	// If the curr node only has left children
//    	} else if (curr.getLeftChild() != null && curr.getRightChild() == null) {
//    		// Sets bf to the tree size of curr's left child
//    		int bf = getTreeSize(curr.getLeftChild());
//    		// If bf is greater or equal to the re-balance threshold,
//    		// the tree isn't balanced
//			if (bf >= rebalanceThreshold) isBalanced = false;
//			// Sets curr's balance factor to bf
//    		curr.setBalanceFactor(bf);
//    		// Resets the balance factors of curr's left descendents
//    		resetBalanceFactors(curr.getLeftChild());
//    	// If curr only has right children
//    	} else if (curr.getLeftChild() == null && curr.getRightChild() != null) {
//    		// Sets bf to the tree size of curr's right child    		
//    		int bf = getTreeSize(curr.getRightChild());
//    		// If bf is greater than or equal to the re-balance threshold,
//    		// the tree isn't 
//			if (bf >= rebalanceThreshold) isBalanced = false;
//    		curr.setBalanceFactor(-1 * bf);
//    		resetBalanceFactors(curr.getRightChild());
//    	} else {
//    		// curr has both children
//    		
//    		int bf = getTreeSize(curr.getLeftChild()) - getTreeSize(curr.getRightChild());
//			if (Math.abs(bf) >= rebalanceThreshold) isBalanced = false;
//    		
//    		curr.setBalanceFactor(bf);
//    		resetBalanceFactors(curr.getLeftChild());
//    		resetBalanceFactors(curr.getRightChild());
//    	}
//    }

    /**
     * A method to return the size of the tree under a certain node, including
     * the node in question itself.
     * 
     * @param curr The node we're currently at to determine its tree size.
     * @return The number of nodes in curr's subtree
     */
//    private int getTreeSize(BSTNode<K> curr){
//    	// If curr isn't at a node, return 0
//    	if (curr == null) return 0;
//    	// Return 1 + the left child's tree size + the right child's tree size
//    	return 1 + 
//    			getTreeSize(curr.getLeftChild()) + 
//    			getTreeSize(curr.getRightChild());    	
//    }
    /**
     * Rebalances the tree by:
     * 1. Copying all keys in the BST in sorted order into an array.
     *    Hint: Use your BSTIterator.
     * 2. Rebuilding the tree from the sorted array of keys.
     */
    public void rebalance() {
    	// Create an array called keys
    	K[] keys = (K[]) new Comparable[numKeys];
        // Make an iterator to start at the root
        BSTIterator<K> itr = new BSTIterator<K>(root);
        int i = 0;
        // While the iterator isn't at the end, sets the next position in the
        // keys array to the iterator's next element
        while (itr.hasNext()) {
        	keys[i++] = itr.next();
        }
        // Sets root to the new BST that's created
        root = sortedArrayToBST(keys, 0, numKeys - 1);
        
        // Resets the balance factors for the tree
    	// resetBalanceFactors(root);
    	// Shows the tree is balanced
    	isBalanced = true;
    }

    /**
     * Recursively rebuilds a binary search tree from a sorted array.
     * Reconstruct the tree in a way similar to how binary search would explore
     * elements in the sorted array. The middle value in the sorted array will
     * become the root, the middles of the two remaining halves become the left
     * and right children of the root, and so on. This can be done recursively.
     * Remember to update the height and balanceFactor of each node.
     *
     * @param keys the sorted array of keys
     * @param start the first index of the part of the array used
     * @param stop the last index of the part of the array used
     * @return root of the new balanced binary search tree
     */
    private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop) {
    	// Returns private companion sortedArrayToBST method
    	return sortedArrayToBST(keys, start, stop, 1);
	}
    
    private BSTNode<K> sortedArrayToBST(K[] keys, int start, int stop, int currHeight) {
    	// If start got to a point where it's less than stop, returns nothing  	
    	if (start > stop) return null;
    	// mid is set to the middle of start and stop
		int mid = (stop + start) / 2;
		// Creates a new node with keys at mid's data
		BSTNode<K> node = new BSTNode<K>(keys[mid]);
		if (mid > start) {
			// node has left children
			node.setBalanceFactor(1);
		}
		// TODO - >=?
		if (stop > mid) {
			// node has right children
			node.setBalanceFactor(node.getBalanceFactor() - 1);
		}
		
		
		
		// Sets the new node's height
		node.setHeight(currHeight);
		// Sets its left and right children using recursion
		node.setLeftChild(sortedArrayToBST(keys, start, mid-1, currHeight+1));
		node.setRightChild(sortedArrayToBST(keys, mid+1, stop, currHeight+1));
		// Returns node
		return node;
    }

    /**
     * Returns true iff the key is in the binary search tree.
     *
     * @param key the key to search
     * @return true if the key is in the binary search tree
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(K key) {        
    	if (key == null) throw new IllegalArgumentException();
    	// Returns the private companion method contains with key and the root
    	return contains(key, root);    	
    }
    
    /**
     * Private companion method that searches the entire tree using
     * recursion to determine if it has a node that contains key
     * 
     * @param key The element we're looking for in the tree
     * @param parent The current node being checked
     * @return True if we found a matching node, false otherwise
     */
    private boolean contains(K key, BSTNode<K> parent) {
    	if (parent == null) return false;
    	// If parent's key equals key, return true
    	if (parent.getKey().equals(key)) {
    		return true;
    	// If parent's key is less than key, look in right child's subtree
    	} else if (parent.getKey().compareTo(key) < 0) {
    		return contains(key, parent.getRightChild());
    	// If parent's key is greater than key, look in left child's subtree
    	} else {
    		return contains(key, parent.getLeftChild());
    	}
    }

    /**
     * Returns the sorted list of keys in the tree that are in the specified
     * range (inclusive of minValue, exclusive of maxValue). This can be done
     * recursively.
     *
     * @param minValue the minimum value of the desired range (inclusive)
     * @param maxValue the maximum value of the desired range (exclusive)
     * @return the sorted list of keys in the specified range
     * @throws IllegalArgumentException if either minValue or maxValue is
     * null, or minValue is larger than maxValue
     */
    public List<K> subSet(K minValue, K maxValue) {
    	
    	if (minValue == null || maxValue == null || 
    			minValue.compareTo(maxValue) > 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	List<K> subSetList = new ArrayList<K>();
        addToList(minValue, maxValue, root, subSetList);
        return subSetList;
    }
    
    /**
     * Resursively adds keys to the list using in-order traversal
     * 
     * @param minValue The minimum value
     * @param maxValue The maximum value
     * @param parent The parent node
     * @param subSetList The subset list
     */
    private void addToList(K minValue, K maxValue, BSTNode<K> parent, List<K> subSetList) {
    	
    	if (parent.getLeftChild() != null && parent.getLeftChild().getKey().compareTo(minValue) >= 0) {
    		addToList(minValue, maxValue, parent.getLeftChild(), subSetList);
    	}
    	
    	subSetList.add(parent.getKey());
    	
    	if (parent.getRightChild() != null && parent.getRightChild().getKey().compareTo(maxValue) < 0) {
    		addToList(minValue, maxValue, parent.getRightChild(), subSetList);
    	}
    }

    /**
     * Return an iterator for the binary search tree.
     * @return the iterator
     */
    public Iterator<K> iterator() {    	
        return new BSTIterator<K>(root);
    }

    /**
     * Clears the tree, i.e., removes all the keys in the tree.
     */
    public void clear() {
    	numKeys = 0;
    	root = null;
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return the number of keys
     */
    public int size() {
        return numKeys;
    }

    /**
     * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
     *
     * @param maxDisplayLevels from the top of the BST that will be displayed
     */
    public void displayTree(int maxDisplayLevels) {
        if (rebalanceThreshold > 0) {
            System.out.println("---------------------------" +
                    "BSTreeBSet Display-------------------------------");
        } else {
            System.out.println("---------------------------" +
                    "BSTreeSet Display--------------------------------");   
        }
        displayTreeHelper(root, 0, maxDisplayLevels);
    }

    private void displayTreeHelper(BSTNode<K> n, int curDepth,
                                   int maxDisplayLevels) {
        if(maxDisplayLevels <= curDepth) return;
        if (n == null)
            return;
        for (int i = 0; i < curDepth; i++) {
            System.out.print("|--");
        }
        System.out.println(n.getKey() + "[" + n.getHeight() + "]{" +
                n.getBalanceFactor() + "}");
        displayTreeHelper(n.getLeftChild(), curDepth + 1, maxDisplayLevels);
        displayTreeHelper(n.getRightChild(), curDepth + 1, maxDisplayLevels);
    }
}

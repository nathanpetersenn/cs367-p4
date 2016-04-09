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
    	// TODO (npetersen): created this constructor
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
        // TODO (npetersen): created this method
    	
    	if (key == null) throw new IllegalArgumentException();
    	
    	if (root == null) {
    		root = new BSTNode<K>(key);
    		root.setHeight(1);
    		root.setBalanceFactor(0);
    	} else {
        	add(key, root);
    	}
    	
    	// rebalance if necessary
    	if (!isBalanced && rebalanceThreshold > 0) {
    		rebalance();
    	}
    }
    
    private void add(K key, BSTNode<K> parent) {
    	// TODO (npetersen): created this method
    	
    	if (parent == null) {
    		// TODO (npetersen): remove this if statement eventually
    		// this in theory will never happen... hopefully
    		throw new RuntimeException("parent was null");
    	}
    	
    	if (parent.getKey().equals(key)) {
    		// duplicate key entry!    		
    		throw new DuplicateKeyException();
    	} else if (parent.getKey().compareTo(key) < 0) {
    		// parent is smaller than key
    		
    		if (parent.getLeftChild() == null) {
    			BSTNode<K> n = new BSTNode<K>(key);
    			n.setHeight(parent.getHeight() + 1);
    			n.setBalanceFactor(parent.getBalanceFactor() + 1);
    			
    			parent.setLeftChild(n);
    			numKeys++;
    			
    			if (n.getBalanceFactor() > rebalanceThreshold) {
    				isBalanced = false;
    			}
    		} else {
    			add(key, parent.getLeftChild());
    		}
    	} else {
    		// parent is larger than key
    		
    		if (parent.getRightChild() == null) {
    			BSTNode<K> n = new BSTNode<K>(key);
    			n.setHeight(parent.getHeight() + 1);
    			n.setBalanceFactor(parent.getBalanceFactor() + 1);
    			
    			parent.setRightChild(n);
    			numKeys++;
    			
    			if (n.getBalanceFactor() > rebalanceThreshold) {
    				isBalanced = false;
    			}
    		} else {
    			add(key, parent.getRightChild());
    		}
    	}
    }
    

    /**
     * Rebalances the tree by:
     * 1. Copying all keys in the BST in sorted order into an array.
     *    Hint: Use your BSTIterator.
     * 2. Rebuilding the tree from the sorted array of keys.
     */
    public void rebalance() {
    	// TODO (npetersen): created this method
    	
    	isBalanced = true;
    	
    	K[] keys = (K[]) new Comparable[numKeys];
        
        BSTIterator<K> itr = new BSTIterator<K>(root);
        int i = 0;
        while (itr.hasNext()) {
        	keys[i++] = itr.next();
        }
        
        root = sortedArrayToBST(keys, 0, numKeys);
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
        // TODO (npetersen): implemented this method!
    	
    	if (stop - start == 0) {
    		// at leaf of new tree
    		BSTNode<K> leaf = new BSTNode<K>(keys[start]);

    		// TODO (npetersen): how to do these statements...?
    		// leaf.setHeight(parent's height + 1);
    		// leaf.setBalanceFactor(parent's bf + 1);

    		return leaf;
    	} else {
    		// at middle of tree
    		int mid = (stop - start) / 2;
    		BSTNode<K> root = new BSTNode<K>(keys[mid]);
    		root.setLeftChild(sortedArrayToBST(keys, start, mid));
    		root.setRightChild(sortedArrayToBST(keys, mid, stop));
    		return root;
    	}
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
    	
    	return contains(key, root);    	
    }
    
    // TODO - Method header
    private boolean contains(K key, BSTNode<K> parent) {
    	if (parent == null) return false;
    	
    	if (parent.getKey().equals(key)) {
    		// found it!
    		return true;
    	} else if (parent.getKey().compareTo(key) < 0) {
    		// parent is less than key
    		return contains(key, parent.getRightChild());
    	} else {
    		// parent is more than key
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
    	// TODO (npetersen): implemented this method
    	
    	if (minValue == null || maxValue == null || 
    			minValue.compareTo(maxValue) > 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	List<K> subSetList = new ArrayList<K>();
        addToList(minValue, maxValue, root, subSetList);
        return subSetList;
    }
    
    // TODO - method header
    private void addToList(K minValue, K maxValue, BSTNode<K> parent, List<K> subSetList) {
    	// TODO (npetersen): implemented this thing
    	
    	if (parent.getLeftChild() != null && parent.getLeftChild().getKey().compareTo(minValue) > 0) {
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

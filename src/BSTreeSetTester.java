import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  SetTesterMain.java
//File:             BSTreeSetTester.java
//Semester:         CS 367 Spring 2016
//
//Author:           Nathan Petersen
//Email:			npetersen2@wisc.edu
//CS Login:         npetersen
//Lecturer's Name:  Deppeler
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//Pair Partner:     Evan Degler
//Email:            edegler@wisc.edu
//CS Login:         degler
//Lecturer's Name:  Skrentny
//
////////////////////////////80 columns wide //////////////////////////////////

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
    	if (parent.getKey().equals(key)) {
    		throw new DuplicateKeyException();
    	// If parent's key is greater than key, look in left child's subtree,
    	// increasing parent's balance factor by 1
    	} else if (parent.getKey().compareTo(key) > 0) {
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
    	// If parent's key is smaller than key, look in right child's subtree,
    	// decreasing parent's balance factor by 1 along the way
    	} else if (parent.getKey().compareTo(key) < 0) {
    		parent.setBalanceFactor(parent.getBalanceFactor()-1);
    		if (Math.abs(parent.getBalanceFactor()) >= rebalanceThreshold){
    			isBalanced = false;
    		}
    		// If parent's right child is null, creates a new node and adds it
    		if (parent.getRightChild() == null) {
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
     * Rebalances the tree by:
     * 1. Copying all keys in the BST in sorted order into an array.
     *    Hint: Use your BSTIterator.
     * 2. Rebuilding the tree from the sorted array of keys.
     */
    public void rebalance() {
    	K[] keys = (K[]) new Comparable[numKeys];
        BSTIterator<K> itr = new BSTIterator<K>(root);
        int i = 0;
        // While the iterator isn't at the end, sets the next position in the
        // keys array to the iterator's next element
        while (itr.hasNext()) {
        	keys[i++] = itr.next();
        }
        // Sets root to the new BST that's created
        root = sortedArrayToBST(keys, 0, numKeys - 1);
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
    	if (start > stop) return null;
    	// Mid is set to the middle of start and stop & creates a node with
    	// keys at mid's data
		int mid = (stop + start) / 2;
		BSTNode<K> node = new BSTNode<K>(keys[mid]);
		// If node has left children
		if (mid > start) {
			node.setBalanceFactor(1);
		}
		// If node has right children
		if (stop > mid) {
			node.setBalanceFactor(node.getBalanceFactor() - 1);
		}
		// Sets the new node's height
		node.setHeight(currHeight);
		// Sets its left and right children using recursion
		node.setLeftChild(sortedArrayToBST(keys, start, mid-1, currHeight+1));
		node.setRightChild(sortedArrayToBST(keys, mid+1, stop, currHeight+1));
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
    	// Creates subSetList and calls companion addToList method with the
    	// min, max, root of the tree, and the new ArrayList
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
    	// Adds the parent node to subSetList in the appropriate position using
    	// in-order traversal (Check left - Visit - Check right)
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
    	// Sets the number of keys in the tree to 0 and points the root to null
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

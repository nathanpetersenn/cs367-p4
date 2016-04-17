import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  SetTesterMain.java
//File:             BSTIterator.java
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
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

    /** LinkedList to track where the iterator is in the BST*/
    LinkedList<BSTNode<K>> list;

    /**
     * Constructs the iterator so that it is initially at the smallest
     * value in the set. Hint: Go to farthest left node and push each node
     * onto the stack while stepping down the BST to get there.
     *
     * @param n the root node of the BST
     */
    public BSTIterator(BSTNode<K> n) {
    	list = new LinkedList<BSTNode<K>>();
    	// Adds n to the stack with a companion method
       	addToQueue(n, list);
    }
    /**
     * Companion method to add nodes to the queue.
     * 
     * @param parent The node being added to the queue.
     * @param subSetList The subtree of nodes under the parent node.
     */
    private void addToQueue(BSTNode<K> parent, LinkedList<BSTNode<K>> subSetList) {
    	// Adds nodes to the subSetList by in-order traversal. First checks
    	// left child, then adds, then checks right child (Left-Visit-Right)
    	if (parent.getLeftChild() != null) {
    		addToQueue(parent.getLeftChild(), subSetList);
    	}
    	subSetList.push(parent);
    	if (parent.getRightChild() != null) {
    		addToQueue(parent.getRightChild(), subSetList);
    	}
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
    	// Returns false if the list is empty, true otherwise
    	return !list.isEmpty();
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return the next item in the iteration
     * @throws NoSuchElementException if the iterator has no more items
     */
    public K next() {
    	if (!hasNext()) throw new NoSuchElementException();
    	// Returns the last element in the list's key
    	return list.removeLast().getKey();
    }
    
    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

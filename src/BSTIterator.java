import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for Binary Search Tree (BST) that is built using Java's Stack
 * class. This iterator steps through the items BST using an INORDER traversal.
 *
 * @author CS367
 */
public class BSTIterator<K> implements Iterator<K> {

    /** Stack to track where the iterator is in the BST*/
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
       	addToStack(n, list);
    }
    
    private void addToStack(BSTNode<K> parent, LinkedList<BSTNode<K>> subSetList) {
    	if (parent.getLeftChild() != null) {
    		addToStack(parent.getLeftChild(), subSetList);
    	}
    	
    	subSetList.push(parent);
    	
    	if (parent.getRightChild() != null) {
    		addToStack(parent.getRightChild(), subSetList);
    	}
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
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
    	
    	return list.removeLast().getKey();
    }
    
    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

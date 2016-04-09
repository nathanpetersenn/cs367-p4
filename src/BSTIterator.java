import java.util.Iterator;
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
    Stack<BSTNode<K>> stack;

    /**
     * Constructs the iterator so that it is initially at the smallest
     * value in the set. Hint: Go to farthest left node and push each node
     * onto the stack while stepping down the BST to get there.
     *
     * @param n the root node of the BST
     */
    public BSTIterator(BSTNode<K> n){
        // TODO (npetersen): perform in-order traversal of tree........
    }

    /**
     * Returns true iff the iterator has more items.
     *
     * @return true iff the iterator has more items
     */
    public boolean hasNext() {
    	// TODO (npetersen): implemented this method
    	
    	return stack.peek() != null;
    }

    /**
     * Returns the next item in the iteration.
     *
     * @return the next item in the iteration
     * @throws NoSuchElementException if the iterator has no more items
     */
    public K next() {
    	// TODO (npetersen): implemented this method
    	
    	if (!hasNext()) throw new NoSuchElementException();
    	
    	return stack.pop().getKey();
    }
    
    /**
     * Not Supported
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

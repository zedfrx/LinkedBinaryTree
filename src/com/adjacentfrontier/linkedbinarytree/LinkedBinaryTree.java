package com.adjacentfrontier.linkedbinarytree;

import java.util.Stack;

import com.adjacentfrontier.linkedbinarytree.BinaryNode;
import com.adjacentfrontier.linkedbinarytree.LinkNode;
import com.adjacentfrontier.linkedbinarytree.Node;

public class LinkedBinaryTree {

	private BinaryNode binaryTree;
	private LinkNode   first;
	private LinkNode   last;
	private int recursiveCount;
	private int treeDepth;

	public LinkedBinaryTree(){
		binaryTree = null;
		first = null;
		last = null;
	}

	
	// TODO recursive insert works but only for the binary tree, linked list functions not implemented
	public void recursiveInsert(int data) {
		binaryTree = recursiveInsert(binaryTree, data);
	}
	
	private BinaryNode recursiveInsert(BinaryNode currentRoot, int data) {
		if (currentRoot == null) {
			// either at root node or end of branch
			return new BinaryNode(new Node(data), new LinkNode(new Node(data)));
		} else if (data < currentRoot.getNode().getNodeData()) {
			currentRoot.leftChild = recursiveInsert(currentRoot.leftChild, data);
		} else if (data > currentRoot.getNode().getNodeData()) {
			currentRoot.rightChild = recursiveInsert(currentRoot.rightChild, data);
		} else {
			System.out.println("Duplicate {" +data+ "} found");
		}
		return currentRoot;
	}

	public void iterativeInsert(int data){

		Node newNode = new Node(data);
		
		BinaryNode treeNode = new BinaryNode(newNode);		// creates a new BinaryNode
		LinkNode   listNode = new LinkNode(newNode);        // creates a new LinkNode
		treeNode.listPosition = listNode;					// associates the LinkNode to the BinaryNode 
		
		
		if (binaryTree == null && first == null){
			// runs first time only
			binaryTree = treeNode;
			first = listNode;
			last = listNode;

		}		
		else if (binaryTree != null && first != null) {
			
			BinaryNode current = binaryTree;	// starts at the root
			BinaryNode parent;					// temp BinaryNode used to store parent
			
			while(true){
				parent = current;
				// checks for duplicates
				if(data != current.getNode().getNodeData()){
					 // move down tree left side
					if(data < current.getNode().getNodeData()) {
						current = current.leftChild;
						if(current == null) {
							// BinaryTree
							parent.leftChild = treeNode;
							// LinkedList
							last.next = listNode;
							listNode.previous = last;
							last = listNode;
							return;
						}
					}
					else
					{
						// move down tree right side
						current = current.rightChild; 
						if(current == null){
							// BinaryTree
							parent.rightChild = treeNode;
							// LinkedList
							last.next = listNode;
							listNode.previous = last;
							last = listNode;
							return;
						}
					}
				}
				else
				{								// if duplicate is found goes to here
					System.out.println("Duplicate {" +data+ "} found");
					return;
				}
			}
		} else {
			// some combination of root being null and first being not null or vice versa
			// i.e. bad
			System.out.println("BinaryTree/LinkList out of sync, abort.");
		}
	}


	public Boolean delete(int value) {
		// finds and deletes a node, rearranging the tree as required
		return delete(binaryTree, null, value);
	}
	
	private Boolean delete(BinaryNode current, BinaryNode previous, int value) {
	
		if (current == null) {
			System.out.println(" X-- value not found");
			return Boolean.FALSE;
		} else if (value < current.getNode().getNodeData()) {
			return delete(current.leftChild, current, value);
		} else  if (value > current.getNode().getNodeData()) {
			return delete(current.rightChild, current, value);
		} else {
			// leaf scenario (no children)
			System.out.print(current.getNode().getNodeData());
			if (current.leftChild == null && current.rightChild == null) {
				if (previous == null) {
					// we must be root, make it null
					deleteLinkNode(current);
					binaryTree = null;
					return Boolean.TRUE;
				} else if (previous.leftChild == current ) {
					// left leaf
					System.out.println(" <-- left leaf found, deleting");
					previous.leftChild = null;
					deleteLinkNode(current);
					return Boolean.TRUE;
				} else {
					// right leaf
					System.out.println(" --> right leaf found, deleting");
					previous.rightChild = null;
					deleteLinkNode(current);
					return Boolean.TRUE;
				}
			} else if (current.leftChild != null && current.rightChild == null) {
				// left branch only (1 child)
				System.out.println(" <-- left branch only, deleting node");
				System.out.println("Previous " + previous.getNode().displayNodeData());
				previous.rightChild = current.leftChild;
				deleteLinkNode(current);
				return Boolean.TRUE;
			} else if (current.leftChild == null && current.rightChild != null) {
				// right branch only (1 child)
				System.out.println(" --> right branch only, deleting node");
				System.out.println("Previous " + previous.getNode().displayNodeData());
				previous.leftChild = current.rightChild;
				deleteLinkNode(current);
				return Boolean.TRUE;
			} else {
				// double child branch
				System.out.println(" <-- --> 2 branches, replacing node (leftmost from right side)");
				
				// find minimum value from right subtree
				BinaryNode replacementNode = findLeftMostRight(current.rightChild);
				
				System.out.println(replacementNode.getNode().displayNodeData() + " --> replacement node found!");
				
				delete(current, null, replacementNode.getNode().getNodeData());
				
				current.setNode(replacementNode.getNode());
				current.listPosition.setNode(replacementNode.listPosition.getNode());
				deleteLinkNode(current);
				
				return Boolean.TRUE;
			}
		}
	}
	
	private BinaryNode findLeftMostRight(BinaryNode current) {
		if (current.leftChild == null) {
			return current;
		} else {
			return findLeftMostRight(current.leftChild);
		}
	}

	private void deleteLinkNode (BinaryNode current) {
		if (first == last) {
			// only 1 node, make it null
			current.listPosition = null;
			first = null;
			last = null;
		} else if (current.listPosition == first) {
			// at the start, assign a new first
			first = current.listPosition.next;
			current.listPosition.next.previous = null;
			current.listPosition.next = null;
		} else if (current.listPosition == last) {
			// at the end, assign a new last
			last = current.listPosition.previous;
			current.listPosition.previous.next = null;
			current.listPosition.previous = null;
		} else {
			// in the middle, chop out the offender
			current.listPosition.previous.next = current.listPosition.next;
			current.listPosition.next.previous = current.listPosition.previous;
		}
	}
	
	public Boolean exists(int value) {
		// determines if a value is in the tree or not
		return exists(binaryTree, value);
	}
	
	private Boolean exists(BinaryNode current, int value) {
		if (current == null) {
			return Boolean.FALSE;
		} else if (value < current.getNode().getNodeData()) {
			return exists(current.leftChild, value);
		} else  if (value > current.getNode().getNodeData()) {
			return exists(current.rightChild, value);
		} else {
			return Boolean.TRUE;
		}
	}
	
	
	public BinaryNode getBinaryTree(){
		// returns the pointer to the root of the BinaryTree
		return binaryTree; 
	}
	
	public LinkNode getLinkedList(){
		// gets Linked List first
		return first;
	}

	public void printRoot() {
		System.out.println("ROOT: "+ binaryTree.getNode().displayNodeData());
	}
	
	public void printFirst() {
		System.out.println("FIRST: "+ first.getNode().displayNodeData());
	}
	
	public void printLast() {
		System.out.println("LAST: "+ last.getNode().displayNodeData());
	}

	public void displayBinaryTreeStructure(){
		Stack<BinaryNode> globalStack = new Stack<BinaryNode>();
		globalStack.push(binaryTree);
		int nBlanks = 256;
		boolean isRowEmpty = false;
		while(isRowEmpty==false)
		{
			Stack<BinaryNode> localStack = new Stack<BinaryNode>();
			isRowEmpty = true;

			for(int j=0; j<nBlanks; j++)
				System.out.print(' ');

			while(globalStack.isEmpty()==false)
			{
				BinaryNode temp = (BinaryNode)globalStack.pop();
				if(temp != null)
				{
					System.out.print(temp.getNode().getNodeData());
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);

					if(temp.leftChild != null ||
							temp.rightChild != null)
						isRowEmpty = false;
				}
				else
				{
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j=0; j<nBlanks*2-2; j++)
					System.out.print(' ');
			}  // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		}  // end while isRowEmpty is false
		System.out.println("--------------------------------------------------------------------");
	}  // end displayTree()


	public void printBinaryTreeInorder() {
		// recursive function that prints the data in the tree
		// effectively an ascending sort
		StringBuffer output = new StringBuffer();
		output.append("[");
		printBinaryTreeInorder(binaryTree, output);
		output.append("]");
		System.out.println(output.toString());
	}
	
	private void printBinaryTreeInorder(BinaryNode newroot, StringBuffer output){
		if(newroot != null){
			printBinaryTreeInorder(newroot.leftChild, output);	   // move left, once left node is null current recursion ends and next line is executed
			output.append(newroot.getNode().displayNodeData() + " "); // print data in BinaryNode
			printBinaryTreeInorder(newroot.rightChild, output);    // move right, once right node is null go back up a level
		}
	}
	
	
	public void printBinaryTreeInorderSearchPath() {
		// recursive function that prints the data in the tree
		// effectively an ascending sort
		printBinaryTreeInorderSearchPath(binaryTree);
	}
	
	private void printBinaryTreeInorderSearchPath(BinaryNode newroot) {
		if (newroot != null) {
			System.out.println("<-- left");
			printBinaryTreeInorderSearchPath(newroot.leftChild); // move left, once left
														// node is null current
														// recursion ends and
														// next line is executed
			System.out
					.println("BN "
							+ newroot.getNode().displayNodeData()
							+ " LN "
							+ (newroot.listPosition == null ? "{null}" : newroot.listPosition.getNode().displayNodeData()) 
							+ " LN left "
							+ (newroot.listPosition.previous == null ? "{null}" : newroot.listPosition.previous.getNode().displayNodeData())
							+ " LN right"
							+ (newroot.listPosition.next == null ? "{null}" : newroot.listPosition.next.getNode().displayNodeData()));
			System.out.println("--> Right");
			printBinaryTreeInorderSearchPath(newroot.rightChild); // move right, once
															// right node is
															// null go back up a
															// level
		}
	}
	
	public void displayLinkedList(Boolean order) {
		// recursive function that traverses the linked list left to right.
		// true = forward
		// false = backward
		if (order == null || order == Boolean.TRUE) {
			displayLinkedListForward(first);
		} else {
			displayLinkedListBackward(last);
		}
	}
	
	private void displayLinkedListForward(LinkNode current) {
		if (current != null) {
			System.out.print(current.getNode().displayNodeData() + " --> ");
			displayLinkedListForward(current.next);
		} else {
			System.out.println();
		}
	}

	private void displayLinkedListBackward(LinkNode current) {
		if (current != null) {
			System.out.print(current.getNode().displayNodeData() + " --> ");
			displayLinkedListBackward(current.previous);
		} else {
			System.out.println();
		}
		
	}

	public int getNodeCount(){			
		// recursive BinaryNode count method
		this.recursiveCount = 0;
		recursiveCount(binaryTree);
		return recursiveCount;
	}

	private void recursiveCount(BinaryNode currentRoot) {
		if(currentRoot != null){
			recursiveCount(currentRoot.leftChild);
			recursiveCount++;
			recursiveCount(currentRoot.rightChild);
		}
	}
	
	
	public int getTreeDepth(){			
		// recursive tree depth
		// TODO doesn't return correct number, for 1 high it returns 2, for all others it returns height -1  
		this.treeDepth = 1;
		recursiveTreeDepth(binaryTree, treeDepth);
		return treeDepth;
	}
	
	private void recursiveTreeDepth(BinaryNode currentRoot, int count) {
		if(currentRoot != null){
			recursiveTreeDepth(currentRoot.leftChild, count++);
			if (count > treeDepth) {
				treeDepth = count; 
			}
			recursiveTreeDepth(currentRoot.rightChild, count++);
		}
	}
}

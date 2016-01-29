package com.adjacentfrontier.linkedbinarytree;

public class BinaryNode implements NodeInterface {

	public BinaryNode leftChild; 	// pointer to left BinaryNode
	public BinaryNode rightChild; 	// pointer to right BinaryNode
	public LinkNode listPosition;	// pointer to link list position
			
	private Node node;
	
	public BinaryNode(Node newNode) {
		this.node = newNode;
	}
	
	public BinaryNode(Node newNode, LinkNode newLinkNode) {
		this.node = newNode;
		this.listPosition = newLinkNode;
	}

	public Node getNode() {
		return this.node;
	}

	public void setNode(Node replacementNode) {
		this.node = replacementNode;
	}
}

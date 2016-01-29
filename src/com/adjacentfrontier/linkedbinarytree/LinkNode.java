package com.adjacentfrontier.linkedbinarytree;

public class LinkNode implements NodeInterface {

	public LinkNode next; 		// links to the next LinkNode, creating a linked list
	public LinkNode previous; 	// traversable forwards and backwards

	private Node node;

	public LinkNode(Node newNode) {
		this.node = newNode;
	}

	public Node getNode() {
		return this.node;
	}

	public void setNode(Node replacementNode) {
		this.node = replacementNode;
	}
}

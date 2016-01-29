package com.adjacentfrontier.linkedbinarytree;

public interface NodeInterface {
	
	// define our Node interface
	public Node getNode();
	
	// set node will primarily be used in deletion scenarios to replace 2-child parent nodes
	public void setNode(Node replacementNode);
}

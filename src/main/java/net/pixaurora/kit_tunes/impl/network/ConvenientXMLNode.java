package net.pixaurora.kit_tunes.impl.network;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ConvenientXMLNode {
	private final Node identity;
	private final Document document;

	public ConvenientXMLNode(Node identity, Document document) {
		this.identity = identity;
		this.document = document;
	}

	public ConvenientXMLNode(Document document) {
		this(document, document);
	}

	public ConvenientXMLNode hasChild(String name) {
		Node child = this.document.createElement(name);
		this.identity.appendChild(child);

		return new ConvenientXMLNode(child, this.document);
	}

	public void hasText(String text) {
		this.identity.appendChild(this.document.createTextNode(text));
	}
}

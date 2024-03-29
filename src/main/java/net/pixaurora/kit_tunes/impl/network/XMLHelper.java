package net.pixaurora.kit_tunes.impl.network;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHelper {
	private static final DocumentBuilder BUILDER = createBuilder();

	public static DocumentBuilder createBuilder() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();

		factory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Failed to create DocumentBuilder");
		}
	}

	public static Document getDocument(InputStream input) throws ParsingException {
		try {
			return BUILDER.parse(input);
		} catch (SAXException | IOException e) {
			throw new ParsingException("Failed to parse initial document.");
		}
	}

	public static Node requireChild(String name, Node parent) throws ParsingException {
		NodeList nodes = parent.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeName() == name) {
				return node;
			}
		}

		throw new ParsingException("Child node `" + name + "` not found.");
	}

	public static String requireString(String name, Node parent) throws ParsingException {
		Node child = requireChild(name, parent);
		return child.getTextContent();
	}

	public static int requireInt(String name, Node parent) throws ParsingException {
		String textContent = requireString(name, parent);

		try {
			return Integer.valueOf(textContent);
		} catch (NumberFormatException e) {
			throw new ParsingException("Child node `" + name + "` cannot be parsed as a string with content `" + textContent + "`!");
		}
	}
}

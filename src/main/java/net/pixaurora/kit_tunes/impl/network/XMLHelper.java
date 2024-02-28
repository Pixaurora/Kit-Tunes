package net.pixaurora.kit_tunes.impl.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.pixaurora.kit_tunes.impl.KitTunes;

public class XMLHelper {
	private static final DocumentBuilder BUILDER = createBuilder();
	private static final Transformer TRANSFORMER = createTransformer();

	public static DocumentBuilder createBuilder() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();

		factory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Failed to create DocumentBuilder");
		}
	}

	public static Transformer createTransformer() {
		TransformerFactory factory = TransformerFactory.newInstance();

		Transformer transformer;
		try {
			transformer = factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException("Failed to create Transformer for XML encoding.");
		}

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		return transformer;
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

	public static Document newDocument() {
		return BUILDER.newDocument();
	}

	public static String convertToString(Document document) throws ParsingException {
		DOMSource dom = new DOMSource(document);

		var output = new ByteArrayOutputStream();

		try {
			TRANSFORMER.transform(dom, new StreamResult(output));
		} catch (TransformerException e) {
			KitTunes.LOGGER.error("Failed to transform document!", e);
			throw new ParsingException("Somehow corrupt document could not be converted to string.");
		}

		return new String(output.toByteArray());
	}
}

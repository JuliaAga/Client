package com.client;
/*Класс разбирает сообщение сервера, при необходимости сохраняет сообщение в файл.
 * Для работы с xml используется DOM */

import com.common.XmlFileSaver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;

public class ServerMessageParser {
    private static final Logger logger = LogManager.getLogger(ServerMessageParser.class);
    private static DocumentBuilder dBuilder;
    private static Document doc;

    /*Единственный доступный извне для вызова метод, возращает извлеченное из xml сообщение сервера*/
    public static String parseServerMessage(String xmlMessage, Boolean needAdditionallyFile) {
        makeBuilder();
        parseMessageToDoc(xmlMessage);
        if (needAdditionallyFile) {
            XmlFileSaver.makeXmlMessageInFile("ServerMessage.xml", doc);
        }
        return extractServerMessage();
    }

    private static void makeBuilder() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException pEx) {
            logger.error("Невозможно построить билдер: " + pEx.getMessage());
        }
    }

    private static void parseMessageToDoc(String messageXml) {
        try {
            doc = dBuilder.parse(new InputSource(new StringReader(messageXml)));
            doc.getDocumentElement().normalize();
        } catch (Exception iEx) {
            logger.error("Невозможно разобрать xml сообщение: " + iEx.getMessage());
        }

    }

    private static String extractServerMessage() {
        NodeList nodeList = doc.getElementsByTagName("message");
        if (nodeList.getLength() != 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        } else {
            return "";
        }
    }
}

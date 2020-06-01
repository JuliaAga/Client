package com.common;
/*Класс сохраняет документ xml в файл*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlFileSaver {
    private static final Logger logger = LogManager.getLogger(XmlFileSaver.class);

    public static void makeXmlMessageInFile(String xmlFileName, Document doc) {

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult file = new StreamResult(new File(xmlFileName));
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, file);
            logger.info("Сообщение записано в файл");
        } catch (TransformerException tEx) {
            logger.info("Не удалось записать сообщение в файл. " + tEx);
        }
    }
}

package com.example.sber.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Controller
public class CreateXml {
    @Value("${source.file}")
    private String inputFile;

    @PostConstruct
    public void createXmlFile() {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Transformer t = null;
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(inputFile));

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

    }
}

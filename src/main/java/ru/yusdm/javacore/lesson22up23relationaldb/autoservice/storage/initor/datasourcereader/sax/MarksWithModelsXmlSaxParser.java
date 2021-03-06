package ru.yusdm.javacore.lesson22up23relationaldb.autoservice.storage.initor.datasourcereader.sax;

import ru.yusdm.javacore.lesson22up23relationaldb.autoservice.common.solutions.parser.FileParser;
import ru.yusdm.javacore.lesson22up23relationaldb.autoservice.common.solutions.xml.sax.XmlSaxUtils;
import ru.yusdm.javacore.lesson22up23relationaldb.autoservice.mark.domain.Mark;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.util.List;

public class MarksWithModelsXmlSaxParser implements FileParser<List<Mark>> {


    @Override
    public List<Mark> parseFile(String file) throws Exception {
        SAXParser saxParser = XmlSaxUtils.getParser();

        MarksWithModelSaxHandler saxHandler = new MarksWithModelSaxHandler();
        saxParser.parse(new File(file), saxHandler);
        return saxHandler.getMarks();
    }
}

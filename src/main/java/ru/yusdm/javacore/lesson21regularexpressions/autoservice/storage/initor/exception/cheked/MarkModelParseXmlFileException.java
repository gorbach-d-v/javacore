package ru.yusdm.javacore.lesson21regularexpressions.autoservice.storage.initor.exception.cheked;

import ru.yusdm.javacore.lesson21regularexpressions.autoservice.common.business.exception.AutoServiceCheckedException;

public class MarkModelParseXmlFileException extends AutoServiceCheckedException {

    public MarkModelParseXmlFileException(int code, String message, Exception cause) {
        super(code, message);
        initCause(cause);
    }
}

package ru.yusdm.javacore.lesson22up23relationaldb.autoservice.common.business.exception;

import static ru.yusdm.javacore.lesson22up23relationaldb.autoservice.common.business.exception.CommonExceptionMeta.JDBC_CONNECTION_ACHIVE_ERROR;

public class ConnectionAchiveError extends AutoServiceUncheckedException {

    public ConnectionAchiveError(Exception cause) {
        super(JDBC_CONNECTION_ACHIVE_ERROR.getCode(), JDBC_CONNECTION_ACHIVE_ERROR.getDescription(), cause);
    }
}

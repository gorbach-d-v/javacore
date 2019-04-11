package ru.yusdm.javacore.lesson22relationaldb.autoservice.common.business.repo.jdbc;

import ru.yusdm.javacore.lesson22relationaldb.autoservice.common.solutions.repo.jdbc.PreparedStatementConsumer;

import java.util.List;

public class SqlPreparedStatemntConsumerHolder {
    private String sql;
    private List<PreparedStatementConsumer> preparedStatementConsumers;

    public SqlPreparedStatemntConsumerHolder(String sql, List<PreparedStatementConsumer> preparedStatementConsumers) {
        this.sql = sql;
        this.preparedStatementConsumers = preparedStatementConsumers;
    }

    public String getSql() {
        return sql;
    }

    public List<PreparedStatementConsumer> getPreparedStatementConsumers() {
        return preparedStatementConsumers;
    }
}

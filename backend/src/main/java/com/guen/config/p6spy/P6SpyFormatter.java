package com.guen.config.p6spy;

import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.sql.SQLException;
import java.util.Locale;

public class P6SpyFormatter implements MessageFormattingStrategy {

    private static final String NEW_LINE = "\n";
    private static final String TAP = "\t";
    private static final String CREATE = "create";
    private static final String ALTER = "alter";
    private static final String DROP = "drop";
    private static final String COMMENT = "comment";

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql.trim().isEmpty()) {
            return formatByCommand(category);
        }
        return  highlight(formatBySql(sql, category,elapsed));
    }

    private static String formatByCommand(String category) {
        return NEW_LINE
                + "Execute Command : "
                + TAP
                + category;
   }

    private String formatBySql(String sql, String category,Long elapsed) {
        if (isStatementDDL(sql, category)) {
            return NEW_LINE
                    + "Execute DDL("+String.format(" %d ms", elapsed)+") : "
                    + NEW_LINE
                    + FormatStyle.DDL
                    .getFormatter()
                    .format(sql);
        }
        return NEW_LINE
                + "Execute DML("+String.format(" %d ms", elapsed)+") : "
                + NEW_LINE
                + FormatStyle.BASIC
                .getFormatter()
                .format(sql);
    }

    private String highlight(String sql) {
        return FormatStyle.HIGHLIGHT.getFormatter().format(sql);
    }

    private boolean isStatementDDL(String sql, String category) {
        return isStatement(category) && isDDL(sql.trim().toLowerCase(Locale.ROOT));
    }

    private boolean isStatement(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private boolean isDDL(String lowerSql) {
        return lowerSql.startsWith(CREATE)
                || lowerSql.startsWith(ALTER)
                || lowerSql.startsWith(DROP)
                || lowerSql.startsWith(COMMENT);
    }
}
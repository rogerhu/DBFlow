package com.grosner.dbflow.sql.builder;

import android.text.TextUtils;

import com.grosner.dbflow.structure.Column;
import com.grosner.dbflow.structure.ColumnType;

import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Wraps around creating the tablecreation in a simple way.
 */
public class TableCreationQueryBuilder extends QueryBuilder {

    public QueryBuilder appendColumn(Column column) {
        if (column.length() > -1) {
            mQuery.append("(");
            mQuery.append(column.length());
            mQuery.append(")");
        }

        if (column.value().value() == ColumnType.PRIMARY_KEY_AUTO_INCREMENT) {
            mQuery.append(" PRIMARY KEY AUTOINCREMENT");
        }

        if (column.notNull()) {
            mQuery.append(" NOT NULL ON CONFLICT ");
            mQuery.append(column.onNullConflict().toString());
        }

        if (column.unique()) {
            mQuery.append(" UNIQUE ON CONFLICT ");
            mQuery.append(column.onUniqueConflict().toString());
        }
        return this;
    }

    public QueryBuilder appendCreateTableIfNotExists(String tableName) {
        mQuery.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");
        return this;
    }

    /**
     * Appends all of the column definitions here
     *
     * @param columnDefinitions
     * @return
     */
    public QueryBuilder appendColumnDefinitions(List<QueryBuilder> columnDefinitions) {
        mQuery.append(TextUtils.join(", ", columnDefinitions)).append(");");
        return this;
    }
}
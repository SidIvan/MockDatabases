package DatabaseManager.SQLUtils;

import DatabaseManager.exceptions.TableInitializationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TableSQLConstructor {
    public static String constructCreate(JSONObject json) throws TableInitializationException {
        StringBuilder query = new StringBuilder("CREATE TABLE ");
        if (!json.containsKey("tableName")) {
            throw new TableInitializationException(1);
        }
        query.append(json.get("tableName"));
        query.append("(");
        if (!json.containsKey("columnInfos") ||
                ((JSONArray) json.get("columnInfos")).size() == 0) {
            throw new TableInitializationException(2);
        }
        if (!json.containsKey("primaryKey")) {
            throw new TableInitializationException(3);
        }
        String primaryKeyTitle = "";
        for (Object columnInfo : (JSONArray) json.get("columnInfos")) {
            JSONObject columnJSON = (JSONObject) columnInfo;
            if (!columnJSON.containsKey("title")) {
                throw new TableInitializationException(4);
            }
            if (!columnJSON.containsKey("type")) {
                throw new TableInitializationException(5);
            }
            String columnName = columnJSON.get("title").toString(),
                    columnType = columnJSON.get("type").toString();
            if (columnName.equals(json.get("primaryKey"))) {
                primaryKeyTitle = columnName;
            }
            query.append(columnName);
            query.append(" ");
            query.append(columnType);
            query.append(", ");
        }
        if (primaryKeyTitle.equals("")) {
            throw new TableInitializationException(3);
        }
        query.append("PRIMARY KEY(");
        query.append(primaryKeyTitle);
        query.append("));");
        return query.toString();
    }

    public static String constructGetByName(String name) {
        String query = "SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + name + "'";
        return query;
    }

    public static String constructCheckExistence(String name) {
        String query = "SELECT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = '" + name + "');";
        return query;
    }

    public static String constructGetPrimaryKeyName(String name) {
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME = '" + name + "'";
        return query;
    }

    public static String constructDelete(String name) {
        String query = "DROP TABLE " + name;
        return query;
    }
}

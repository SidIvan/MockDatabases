package DatabaseManager.sqlConstructors;

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
}

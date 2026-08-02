package org.task.tracker;

import java.util.*;

public class JsonHandling {
    private String json;
    private int index;

    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (obj instanceof String) {
            return "\"" + escape((String) obj) + "\"";
        }

        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }

        if (obj instanceof Map) {
            return generator((Map<?, ?>) obj);
        }

        return "\"" + escape(obj.toString()) + "\"";
    }

    private String generator(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");

        Iterator<? extends Map.Entry<?, ?>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<?, ?> entry = iterator.next();

            json.append("\"")
                    .append(escape(String.valueOf(entry.getKey())))
                    .append("\":");

            json.append(toJson(entry.getValue()));

            if (iterator.hasNext()) {
                json.append(",");
            }
        }

        json.append("}");
        return json.toString();
    }

    private static String escape(String str) {
        return str
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}

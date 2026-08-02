package org.task.tracker;

import java.io.IOException;
import java.util.Map;

public interface Task {
    void Update() throws IOException;
    void deletetask() throws IOException;
    void add() throws IOException;
    void preview(String name) throws IOException;
}

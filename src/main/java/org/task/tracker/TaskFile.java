package org.task.tracker;

import java.io.IOException;
import java.util.Set;

public interface TaskFile {
    public void addFile() throws IOException;
    public void deleteFile(String name) throws IOException;
    public Object filereader(String name) throws IOException;
    public void fileWriter(String name,String content,Boolean appender) throws IOException;
    public void updateFile() throws IOException;
    public void Fileupdater(String name,String content) throws IOException;
    public void overwriter(String name, Set<String> contents) throws IOException;
}

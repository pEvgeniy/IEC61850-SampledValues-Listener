package service.managers;

import java.util.List;

public interface HistoryManager {

    void add(String msg);

    List<String> getHistory();
}

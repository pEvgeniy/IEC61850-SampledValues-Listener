package service.managers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    List<String> msgHistory = new ArrayList<>(11);

    @Override
    public void add(String msg) {
        if (msgHistory.size() == 10) {
            msgHistory.remove(0);
            msgHistory.add(msgHistory.size(), msg);
            return;
        }
        msgHistory.add(msg);
    }

    @Override
    public List<String> getHistory() {
        return msgHistory;
    }
}

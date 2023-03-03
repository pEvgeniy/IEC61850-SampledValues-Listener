package service;

import service.managers.HistoryManager;
import service.managers.InMemoryHistoryManager;

public class Managers {

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }
}

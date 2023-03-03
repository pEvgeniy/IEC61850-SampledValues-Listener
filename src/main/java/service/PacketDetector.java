package service;

import lombok.extern.slf4j.Slf4j;
import service.helper.PcapHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Slf4j
public class PacketDetector {
    private final ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
    private final PcapHelper pcapHelper;
    private boolean discovering;
    private ScheduledFuture<?> discoveringTask;

    public PacketDetector(String interfaceName, int t) {
        pcapHelper = new PcapHelper(interfaceName, t);
    }

    public void startDiscovering() {
        Listener listener = new Listener();
        if (!discovering) {
            discoveringTask = pcapHelper.startPacketsCapturing(2505, listener, ses);
            discovering = true;
        } else {
            log.trace("Can not discover: already discovering");
        }
    }

    public void stopDiscovering() {
        if (discovering) {
            discoveringTask.cancel(true);
            discovering = false;
        }
    }
}

import lombok.extern.slf4j.Slf4j;
import service.PacketDetector;

@Slf4j
public class Main {

    public static void main(String[] args) {
        PacketDetector agentDetector = new PacketDetector("\\Device\\NPF_{003D106A-B450-4AE1-B005-546BBB3C552C}", 1000);
        agentDetector.startDiscovering();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        agentDetector.stopDiscovering();
    }
}

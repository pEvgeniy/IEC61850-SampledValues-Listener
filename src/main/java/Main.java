import lombok.extern.slf4j.Slf4j;
import service.PacketDetector;

@Slf4j
public class Main {

    public static void main(String[] args) {
        PacketDetector agentDetector = new PacketDetector("\\Device\\NPF_Loopback", 1000);
        agentDetector.startDiscovering();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        agentDetector.stopDiscovering();
    }
}

package service;

import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import service.managers.InMemoryHistoryManager;
import service.parser.SampledValuesParser;

@Slf4j
public class Listener implements PacketListener {
    SampledValuesParser msgParser = new SampledValuesParser();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public void gotPacket(Packet packet) {
        byte[] rawData = packet.getRawData();
        String receivedMsg = msgParser.parse(rawData);
        Packet payload = packet.getPayload();
        log.info("received msg = {}\n" +
                "payload = {}", receivedMsg, payload);
        historyManager.add(receivedMsg);
    }
}

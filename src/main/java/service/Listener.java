package service;

import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import service.managers.InMemoryHistoryManager;
import service.parser.MsgParser;

@Slf4j
public class Listener implements PacketListener {
    MsgParser msgParser = new MsgParser();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public void gotPacket(Packet packet) {
        String receivedMsg = msgParser.parse(packet.getRawData());
        Packet payload = packet.getPayload();
        log.debug("received msg = {}\npayload = {}", receivedMsg, payload);
        historyManager.add(receivedMsg);
    }
}

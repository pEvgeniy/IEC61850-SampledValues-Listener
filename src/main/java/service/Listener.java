package service;

import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PacketListener;
import org.pcap4j.packet.Packet;
import service.managers.InMemoryHistoryManager;
import service.parser.SampledValuesParser;

@Slf4j
public class Listener implements PacketListener {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public void gotPacket(Packet packet) {
        byte[] rawData = packet.getRawData();
        SampledValuesParser msgParser = new SampledValuesParser(rawData);
        msgParser.parse();

//        historyManager.add();
    }
}

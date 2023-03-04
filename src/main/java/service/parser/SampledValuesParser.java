package service.parser;

public class SampledValuesParser {

    public String parse(byte[] packetData){
        if (packetData.length < 14) return null;
        int offset = 4 /*local*/ + 20 /*ipv4*/ + 8 /* udp */;

        byte[] dataByte = new byte[packetData.length-offset];
        System.arraycopy(packetData,offset,dataByte,0,dataByte.length);
        return new String(dataByte);
    }
}

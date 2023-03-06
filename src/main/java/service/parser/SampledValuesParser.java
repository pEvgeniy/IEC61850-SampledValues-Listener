package service.parser;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

@Slf4j
public class SampledValuesParser {

    byte[] packetData;
    int left = 0;

    byte svIDLength;

    public SampledValuesParser(byte[] packetData) {
        this.packetData = packetData;
    }

    public void parse(){

//        MAC addresses
        log.info("MAC address of destination: " + getMACDestination());
        log.info("MAC address of source: " + getMACSource());

//        Protocol
        checkProtocol();

//        svID
        log.info("svID = {}", getSvID());

//        Something
        left = left + 15;

//        Current and voltage
        int[] measurements = new int[8];

        for (int i = 0; i < measurements.length; i++) {
            measurements[i] = getValue();
            if (i < 4) {
                log.info("phsMeas I = {}", measurements[i]);
            } else {
                log.info("phsMeas U = {}", measurements[i]);
            }
        }
    }

    private StringBuilder getMACDestination() {
        StringBuilder destination = new StringBuilder();
        // First 6 bytes is MAC address of destination
        for (int i = 0; i < 6; i++) {
            destination.append(String.format("%02X%s", packetData[i], (i < 6 - 1) ? ":" : ""));
        }
        return destination;
    }

    private StringBuilder getMACSource() {
        StringBuilder source = new StringBuilder();
        // From 6 byte to 12 byte is MAC address of source
        for (int i = 6; i < 12; i++) {
            source.append(String.format("%02X%s", packetData[i], (i < 12 - 1) ? ":" : ""));
        }
        return source;
    }

    private void checkProtocol() {
        byte[] header = new byte[14];
        System.arraycopy(packetData, left, header, 0, 14);
        String protocol = bytesToHex(new byte[]{header[12], header[13]});
        if (!"88ba".equals(protocol)) {
            log.error("Wrong protocol, should be 0x88BA");
//            throw new RuntimeException("Wrong protocol, should be 0x88BA");
        }
        left += 14;
    }

    private String getSvID() {
        left = 33;
        svIDLength = packetData[32];
        byte[] svID = new byte[svIDLength];
        System.arraycopy(packetData, left, svID, 0, svIDLength);
        left += svIDLength;
        return new String(svID);
    }

    private int getValue() {
        byte[] phsMeas = new byte[4];
        System.arraycopy(packetData, left, phsMeas, 0, phsMeas.length);
        left += 8;
        return rawDataToDec(phsMeas);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static int rawDataToDec(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }
}

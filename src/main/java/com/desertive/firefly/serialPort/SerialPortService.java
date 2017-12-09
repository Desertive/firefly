package com.desertive.firefly.serialPort;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;

public class SerialPortService {
    
    private SerialPort comPort;
    private static final int STARTING_BYTES_LENGTH = 6;
    
    public SerialPortService(String name) {
        comPort = SerialPort.getCommPort(name);
        comPort.setBaudRate(115200);
        comPort.setParity(SerialPort.NO_PARITY);
        comPort.openPort();
    }
    
    public void send(List<Color> colors) {
        final int byteArrayLenght = Integer.BYTES * STARTING_BYTES_LENGTH + Integer.BYTES * colors.size() * 3;
        
        ByteBuffer buffer = ByteBuffer.allocate(byteArrayLenght);
        buffer.put(generateStartingBytes(colors.size()));
        
        colors.stream()
                .map(color -> new ArrayList<Integer>() {{
                    add(color.getRed());
                    add(color.getGreen());
                    add(color.getBlue());
                }})
                .flatMap(list -> list.stream())
                .forEach(color -> buffer.putInt(color));
        
        byte[] bytes = buffer.array();
        comPort.writeBytes(bytes, (long) byteArrayLenght);
    }
    
    ByteBuffer generateStartingBytes(int colorListSize) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * STARTING_BYTES_LENGTH);
        buffer.putInt(0x41); // A
        buffer.putInt(0x64); // d
        buffer.putInt(0x61); // a
        buffer.putInt(0x0);
        buffer.putInt(0x9F);
        buffer.putInt(0xCA);
        return buffer;
    }

}

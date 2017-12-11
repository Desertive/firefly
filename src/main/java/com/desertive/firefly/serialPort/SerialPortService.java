package com.desertive.firefly.serialPort;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fazecast.jSerialComm.SerialPort;

public class SerialPortService {

    private static final Logger LOG = LoggerFactory.getLogger(SerialPortService.class);

    private SerialPort comPort;
    private static final int STARTING_BYTES_LENGTH = 6;

    public SerialPortService(String name) {
        comPort = SerialPort.getCommPort(name);
        comPort.setBaudRate(115200);
        comPort.setParity(SerialPort.NO_PARITY);
        if (!comPort.openPort()) {
            LOG.warn(String.format("Serialport connection establishment failed with port %s", name));
        } else {
            LOG.info(String.format("Serialport connection established with port %s", name));
        }
    }

    public void write(List<Color> colors) {
        final int byteArrayLength = STARTING_BYTES_LENGTH + colors.size() * 3;
        ByteBuffer buffer = ByteBuffer.allocate(byteArrayLength);

        buffer.put(generateStartingBuffer(colors.size()));
        buffer.put(generateColorBuffer(colors));

        // Print first color
        LOG.debug(
                String.format("r: %d, g: %d, b: %d", buffer.get(6) & 0xFF, buffer.get(7) & 0xFF, buffer.get(8) & 0xFF));

        if (comPort.writeBytes(buffer.array(), (long) byteArrayLength) == -1) {
            LOG.debug(String.format("Serialport write failed"));
        }
    }

    ByteBuffer generateStartingBuffer(int colorListSize) {
        ByteBuffer buffer = ByteBuffer.allocate(STARTING_BYTES_LENGTH);

        // Predefined bytes
        buffer.put(0, Integer.valueOf(0x41).byteValue()); // A
        buffer.put(1, Integer.valueOf(0x64).byteValue()); // d
        buffer.put(2, Integer.valueOf(0x61).byteValue()); // a

        // Led count
        int colorIndexCount = colorListSize - 1;
        int high = (colorIndexCount - colorIndexCount % 256) / 256;
        int low = colorIndexCount % 256;

        buffer.put(3, Integer.valueOf(high).byteValue());
        buffer.put(4, Integer.valueOf(low).byteValue());

        // Checksum
        buffer.put(5, Integer.valueOf(high ^ low ^ 85).byteValue());

        return buffer;
    }

    ByteBuffer generateColorBuffer(List<Color> colors) {
        ByteBuffer buffer = ByteBuffer.allocate(colors.size() * 3);

        List<Integer> flattenedColors = colors.stream().map(color -> new ArrayList<Integer>() {
            {
                add(color.getRed());
                add(color.getGreen());
                add(color.getBlue());
            }
        }).flatMap(list -> list.stream()).collect(Collectors.toList());

        IntStream.range(0, flattenedColors.size()).forEach(index -> {
            buffer.put(index, flattenedColors.get(index).byteValue());
        });

        return buffer;
    }

}

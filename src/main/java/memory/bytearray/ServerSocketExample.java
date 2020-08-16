package memory.bytearray;

import utils.MemoryUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExample {

    public static void main(String[] args) {
        System.out.println("Starting server");
        int port = 7777;
        int messageSizeBytes = 12;
        byte[] buffer = new byte[messageSizeBytes];
        SampleDtoSerializationUtils serializationUtils = new SampleDtoSerializationUtils();
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream()) {
            long memoryUsed = MemoryUtils.memoryUsed();
            while (true) {
                if (inputStream.read(buffer) != messageSizeBytes) {
                    throw new RuntimeException("Invalid message");
                }
                SampleDto sampleDto = serializationUtils.allocationFreeDeserialize(buffer);
                if (sampleDto.getIntValue() == 1 && sampleDto.getLongValue() == 2) {
                    System.out.println("!!!!");
                }
                if (MemoryUtils.memoryUsed() > memoryUsed + 1000000) {
                    throw new RuntimeException("memory allocation happened");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
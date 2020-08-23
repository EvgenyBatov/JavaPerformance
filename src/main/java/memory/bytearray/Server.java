package memory.bytearray;

import utils.MemoryUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        System.out.println("Starting server");
        int port = 7777;
        byte[] buffer = new byte[Request.BYTES];
        SerializationHelper serializationUtils = new SerializationHelper();
        Response response = new Response();
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            long memoryUsed = MemoryUtils.memoryUsed();
            while (true) {
                if (inputStream.read(buffer) != Request.BYTES) {
                    throw new RuntimeException("Invalid request");
                }
                Request request = serializationUtils.requestDeserialize(buffer);
                long result = (long) request.getA() * (long) request.getB();
                response.setResult(result);
                outputStream.write(serializationUtils.serialize(response));
                if (MemoryUtils.memoryUsed() > memoryUsed + 1000000) {
                    throw new RuntimeException("memory allocation happened");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
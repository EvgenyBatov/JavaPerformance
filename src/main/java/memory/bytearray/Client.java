package memory.bytearray;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class Client {

    public static void main(String[] args) {
        System.out.println("Starting client");
        SerializationHelper serializationUtils = new SerializationHelper();
        Random random = new Random();
        int responseSizeBytes = 8;
        byte[] buffer = new byte[responseSizeBytes];
        try (Socket socket = new Socket("localhost", 7777);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            Request request = new Request();
            while (true) {
                int a = random.nextInt();
                System.out.println("a = " + a);
                int b = random.nextInt();
                System.out.println("b = " + b);
                request.setA(a);
                request.setB(b);
                outputStream.write(serializationUtils.serialize(request));
                if (inputStream.read(buffer) != responseSizeBytes) {
                    throw new RuntimeException("Invalid response");
                }
                Response response = serializationUtils.responseDeserialize(buffer);
                System.out.println("result = " + response.getResult());
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
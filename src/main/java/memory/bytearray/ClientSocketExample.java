package memory.bytearray;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ClientSocketExample {

    public static void main(String[] args) {
        System.out.println("Starting client");
        SampleDtoSerializationUtils serializationUtils = new SampleDtoSerializationUtils();
        Random random = new Random();
        try (Socket socket = new Socket("localhost", 7777);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            SampleDto sampleDto = new SampleDto();
            while (true) {
                sampleDto.setIntValue(random.nextInt());
                sampleDto.setLongValue(random.nextLong());
                outputStream.write(serializationUtils.serialize(sampleDto));
                //System.out.println("123");
                //inputStream.readAllBytes();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
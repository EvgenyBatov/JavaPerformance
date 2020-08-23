package memory.bytearray;

import java.nio.ByteBuffer;

public class SerializationHelper {

    private final ByteArrayReader byteArrayReader;
    private final Request request;
    private final Response response;
    private final ByteBuffer requestByteBuffer;
    private final ByteBuffer responseByteBuffer;

    public SerializationHelper() {
        this.byteArrayReader = new ByteArrayReader();
        this.request = new Request();
        this.response = new Response();
        this.requestByteBuffer = ByteBuffer.allocate(Request.BYTES);
        this.responseByteBuffer = ByteBuffer.allocate(Response.BYTES);
    }

    public byte[] serialize(Request request) {
        requestByteBuffer.clear();
        requestByteBuffer.putInt(request.getA());
        requestByteBuffer.putInt(request.getB());
        return requestByteBuffer.array();
    }

    public byte[] serialize(Response response) {
        responseByteBuffer.clear();
        responseByteBuffer.putLong(response.getResult());
        return responseByteBuffer.array();
    }

    public Request requestDeserialize(byte[] byteArray) {
        byteArrayReader.reset(byteArray);
        request.setA(byteArrayReader.readNextInt());
        request.setB(byteArrayReader.readNextInt());
        return request;
    }

    public Response responseDeserialize(byte[] byteArray) {
        byteArrayReader.reset(byteArray);
        response.setResult(byteArrayReader.readNextLong());
        return response;
    }

    public static void main(String[] args) {
        SerializationHelper serializationUtils = new SerializationHelper();
        Request request = new Request();
        request.setA(5);
        request.setB(10);
        byte[] bytes = serializationUtils.serialize(request);
        request = serializationUtils.requestDeserialize(bytes);
        System.out.println(request.getA());
        System.out.println(request.getB());
    }

}

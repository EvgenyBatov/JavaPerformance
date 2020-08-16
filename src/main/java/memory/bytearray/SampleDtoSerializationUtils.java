package memory.bytearray;

import java.nio.ByteBuffer;

public class SampleDtoSerializationUtils {

    private final ByteArrayReader byteArrayReader;
    private final SampleDto sampleDto;

    public SampleDtoSerializationUtils() {
        this.byteArrayReader = new ByteArrayReader();
        this.sampleDto = new SampleDto();
    }

    public byte[] serialize(SampleDto sampleDto) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putInt(sampleDto.getIntValue());
        byteBuffer.putLong(sampleDto.getLongValue());
        return byteBuffer.array();
    }

    public SampleDto threadSafeDeserialize(byte[] byteArray) {
        byteArrayReader.reset(byteArray);
        SampleDto sampleDto = new SampleDto();
        sampleDto.setIntValue(byteArrayReader.readNextInt());
        sampleDto.setLongValue(byteArrayReader.readNextLong());
        return sampleDto;
    }

    public SampleDto allocationFreeDeserialize(byte[] byteArray) {
        byteArrayReader.reset(byteArray);
        sampleDto.setIntValue(byteArrayReader.readNextInt());
        sampleDto.setLongValue(byteArrayReader.readNextLong());
        return sampleDto;
    }

    public static void main(String[] args) {
        SampleDtoSerializationUtils serializationUtils = new SampleDtoSerializationUtils();
        SampleDto sampleDto = new SampleDto();
        sampleDto.setIntValue(1);
        sampleDto.setLongValue(10000000000L);
        byte[] bytes = serializationUtils.serialize(sampleDto);
        sampleDto = serializationUtils.allocationFreeDeserialize(bytes);
        System.out.println(sampleDto.getIntValue());
        System.out.println(sampleDto.getLongValue());
    }

}

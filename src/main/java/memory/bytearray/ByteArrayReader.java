package memory.bytearray;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class ByteArrayReader {

    private static final int INT_SIZE_BYTES = 4;
    private static final int LONG_SIZE_BYTES = 8;
    private static final int BOOLEAN_SIZE_BYTES = 1;

    private byte[] bytes;
    private int index;

    public void reset(byte[] bytes) {
        this.bytes = bytes;
        this.index = 0;
    }

    public int readNextInt() {
        int nextIndex = index + INT_SIZE_BYTES;
        int result = Ints.fromBytes(bytes[index], bytes[index + 1], bytes[index + 2], bytes[index + 3]);
        index = nextIndex;
        return result;
    }

    public long readNextLong() {
        int nextIndex = index + LONG_SIZE_BYTES;
        long result = Longs.fromBytes(
                bytes[index],
                bytes[index + 1],
                bytes[index + 2],
                bytes[index + 3],
                bytes[index + 4],
                bytes[index + 5],
                bytes[index + 6],
                bytes[index + 7]);
        index = nextIndex;
        return result;
    }

    public boolean readNextBoolean() {
        int nextIndex = index + BOOLEAN_SIZE_BYTES;
        boolean result = bytes[index] == 0 ? false : true;
        index = nextIndex;
        return result;
    }

}

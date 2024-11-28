package jobs4u.base.network.data;

import eapli.framework.validations.Preconditions;
import jobs4u.base.network.SerializationUtil;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataDTO implements Serializable {

    private static final int VERSION = 1;
    private static final long serialVersionUID = 1L;
    final int code;
    List<DataBlock> dataBlockList;
    
    public DataDTO(Integer code) {
        Preconditions.noneNull(code);
        Preconditions.isPositive(code);
        this.code = code;
        this.dataBlockList = new ArrayList<>();
    }

    public int version(){
        return VERSION;
    }
    
    public int code(){
        return this.code;
    }

    public List<DataBlock> dataBlockList(){
        return Collections.unmodifiableList(this.dataBlockList);
    }
    
    public void addDataBlock(int length, byte[] data) {
        dataBlockList.add(new DataBlock(length, data));
    }

    public static <T> DataDTO turnListIntoDataDTO(int code, Iterable<T> list) {
        DataDTO dataDTO = new DataDTO(code);
        for (T t : list) {
            byte[] serialized = SerializationUtil.serialize(t);
            dataDTO.addDataBlock(serialized.length, serialized);
        }
        return dataDTO;
    }

    public byte[] toByteArray() {
        //version and code bytes
        int totalLength = 2;

        //if dataDTO is empty then it has only the version, the code, and the 2 ending 0 value bytes
        if (!dataBlockList.isEmpty()) {
            for (DataBlock block : dataBlockList) {
                totalLength += 2 + block.length(); // 2 bytes for length + block data
            }
        }

        // 2 zero bytes to state the end of the dataDTO
        totalLength += 2;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        buffer.put((byte) VERSION);
        buffer.put((byte) code);

        if (!dataBlockList.isEmpty()) {
            for (DataBlock block : dataBlockList) {
                buffer.put(block.lengthL());
                buffer.put(block.lengthM());
                buffer.put(block.data());
            }
        }

        //every dataDTO ends with 2 bytes of value 0 that state that the next chunk of date does
        //not exist, and the dataDTO has ended.
        buffer.put((byte) 0);
        buffer.put((byte) 0);

        return buffer.array();
    }

    public static DataDTO fromByteArray(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int version = Byte.toUnsignedInt(buffer.get());
        if (version != VERSION) {
            throw new IllegalArgumentException("Unsupported version: " + version);
        }
        int code = Byte.toUnsignedInt(buffer.get());

        DataDTO dataDTO = new DataDTO(code);

        // if there are more than 2 bytes left (for the ending zeros)
        while (buffer.remaining() > 2) {
            int lenL = Byte.toUnsignedInt(buffer.get());
            int lenM = Byte.toUnsignedInt(buffer.get());
            int length = lenL + (lenM << 8);
            if (length == 0) {
                break;
            }
            byte[] data = new byte[length];
            buffer.get(data);
            dataDTO.addDataBlock(length, data);
        }

        return dataDTO;
    }

}

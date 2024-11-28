package jobs4u.base.network.data;

import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class DataDTOTest {

    @Test
    public void ensureMessageCodeIsNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new DataDTO(null));
    }

    @Test
    public void ensureMessageCodeIsNotANegative() {
        assertThrows(IllegalArgumentException.class, () -> new DataDTO(-1));
    }

    @Test
    public void ensureMessageHasDefinedStructure() {
        DataDTO dto = new DataDTO(5);
        byte[] bytesRaaaa = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        DataBlock dataBlock = new DataBlock(bytesRaaaa.length, bytesRaaaa);

        assertEquals(1, dto.version());
        assertEquals(5, dto.code);
        dto.addDataBlock(bytesRaaaa.length, bytesRaaaa);

        byte[] bytes = dto.toByteArray();
        assertEquals(1, bytes[0]);
        assertEquals(5, bytes[1]);
        int byteLength = bytes.length;

        int dataLengthL = dataBlock.lengthL();
        int dataLengthM = dataBlock.lengthM();

        assertEquals(dataLengthL, bytes[2]);
        assertEquals(dataLengthM, bytes[3]);

        assertEquals(0, bytes[byteLength-1]);
        assertEquals(0, bytes[byteLength-2]);
    }

}
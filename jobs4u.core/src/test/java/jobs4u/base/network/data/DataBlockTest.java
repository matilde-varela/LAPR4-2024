package jobs4u.base.network.data;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class DataBlockTest {

    @Test
    public void ensureDataBlockLengthIsNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new DataBlock(null, new byte[4]));
    }

    @Test
    public void ensureDataBlockLengthIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DataBlock(-1, new byte[4]));
    }

    @Test
    public void ensureDataBlockLengthIsNotZero() {
        assertThrows(IllegalArgumentException.class, () -> new DataBlock(0, new byte[4]));
    }

    @Test
    public void ensureDataBlockDataIsNotNull() {
        assertThrows(IllegalArgumentException.class, () -> new DataBlock(5, null));
    }

}
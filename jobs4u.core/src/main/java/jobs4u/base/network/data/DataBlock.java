package jobs4u.base.network.data;

import eapli.framework.validations.Preconditions;

import java.io.Serializable;

public class DataBlock implements Serializable {

    private final Integer length;
    private final byte[] data;

    public DataBlock(Integer length, byte[] data) {
        Preconditions.noneNull(length, data);
        Preconditions.isPositive(length);
        this.length = length;
        this.data = data;
    }

    public int length(){
        return this.length;
    }

    public byte[] data() {
        return this.data;
    }

    public byte lengthL() {
        return (byte) (this.length & 0xFF);
    }

    public byte lengthM() {
        return (byte) ((this.length >> 8) & 0xFF);
    }


}

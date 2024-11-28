package jobs4u.base.network;

import java.io.*;

public class SerializationUtil {

    public static byte[] serialize(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            return bos.toByteArray();
        }catch (IOException e){
            throw new RuntimeException("Serialization error", e);
        }
    }

    public static Object deserialize(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("Deserialization error", e);
        }
    }
}

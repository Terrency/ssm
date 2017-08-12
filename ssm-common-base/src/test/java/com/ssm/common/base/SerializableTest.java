package com.ssm.common.base;

import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.PageImpl;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class SerializableTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializableTest.class);

    private Serializer<Page> serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new Serializer<>();
    }

    @Test
    public void test() throws Exception {
        Page page = new PageImpl<>(1, 10, Collections.emptyList(), 100);
        byte[] bytes = serializer.serialize(page);
        page = serializer.deserialize(bytes);
        LOGGER.info("Serialize => {}", Arrays.toString(bytes));
        LOGGER.info("Deserialize => {}", page);
    }

    static class Serializer<T> {

        public byte[] serialize(T t) throws IOException {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(t);
                return baos.toByteArray();
            } finally {
                IOUtils.closeQuietly(baos, oos);
            }
        }

        @SuppressWarnings("unchecked")
        public T deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
            ByteArrayInputStream bais = null;
            ObjectInputStream ois = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                return (T) ois.readObject();
            } finally {
                IOUtils.closeQuietly(bais, ois);
            }
        }
    }


}

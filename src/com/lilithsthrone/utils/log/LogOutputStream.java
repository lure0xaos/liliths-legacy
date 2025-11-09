package com.lilithsthrone.utils.log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class LogOutputStream extends OutputStream {
    private static final int CAPACITY = 65536;
    private final ByteBuffer buf = ByteBuffer.allocate(CAPACITY);
    private final Charset charset;
    private final Level level;

    public LogOutputStream(Level level, Charset charset) {
        this.level = level;
        this.charset = charset;
    }

    @Override
    public void write(int b) throws IOException {
        synchronized (buf) {
            buf.put((byte) b);
        }
        if (b == (int) '\n') flushBuffer();
    }

    private void flushBuffer() {
        synchronized (buf) {
            final String text = new String(Arrays.copyOf(buf.array(), buf.position()), charset);
            Log.log(level, () -> text);
            buf.clear();
        }
    }

    @Override
    public void flush() throws IOException {
        flushBuffer();
    }
}

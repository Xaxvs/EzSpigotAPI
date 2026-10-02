package com.github.xaxvs.file;

import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class AtomicFileSaver {

    private AtomicFileSaver() {
    }

    /**
     * Saves UTF-8 text by atomically moving a temporary file to the target.
     * Atomic replacement of an existing target depends on the filesystem provider.
     * This method does not fall back to a non-atomic move or guarantee power-loss durability.
     *
     * @param file the target file
     * @param data the text to save
     * @throws IOException if preparation, encoding, writing, or the atomic move fails
     * @throws NullPointerException if file or data is null
     */
    public static void save(@NonNull Path file, @NonNull String data) throws IOException {
        Path target = file.toAbsolutePath();
        Path parent = target.getParent();

        if (parent == null) {
            throw new IOException("Target path has no parent directory: " + target);
        }

        Files.createDirectories(parent);

        Path temp = Files.createTempFile(
                parent,
                target.getFileName().toString() + ".",
                ".tmp"
        );

        try {
            writeDataToTemp(temp, data);
            Files.move(temp, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException | RuntimeException e) {
            try {
                Files.deleteIfExists(temp);
            } catch (IOException | RuntimeException cleanupException) {
                e.addSuppressed(cleanupException);
            }

            throw e;
        }
    }

    private static void writeDataToTemp(Path temp, String data) throws IOException {
        ByteBuffer buffer = StandardCharsets.UTF_8.newEncoder()
                .encode(CharBuffer.wrap(data));

        try (FileChannel channel = FileChannel.open(
                temp,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            channel.force(true);
        }
    }
}
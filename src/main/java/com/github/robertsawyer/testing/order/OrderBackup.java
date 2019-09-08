package com.github.robertsawyer.testing.order;

import com.github.robertsawyer.testing.order.Order;

import java.io.*;

public class OrderBackup {

    private Writer writer;

    public Writer getWriter() {
        return writer;
    }

    public void createFile() throws FileNotFoundException {
        File file = new File("orderBackup.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        writer = new BufferedWriter(outputStreamWriter);
    }

    public void backup(Order order) throws IOException {
        if (writer == null) throw new IOException("Plik nie istnieje");
        writer.append(order.toString());
    }

    public void closeFile() throws IOException {
        writer.close();
    }
}

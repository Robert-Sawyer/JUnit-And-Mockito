package com.github.robertsawyer.testing.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExecutionOrderTest {

    @Test
    void callingBackupWithoutCreatingFileShouldThrowException() throws IOException{
        //given
        OrderBackup orderBackup = new OrderBackup();

        //then
        assertThrows(IOException.class, () -> orderBackup.backup(new Order()));
    }
}

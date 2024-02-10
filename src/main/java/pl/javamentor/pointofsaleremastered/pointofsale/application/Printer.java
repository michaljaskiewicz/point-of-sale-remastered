package pl.javamentor.pointofsaleremastered.pointofsale.application;

import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptClosed;

public interface Printer {

    void print(ReceiptClosed receiptClosed);

}

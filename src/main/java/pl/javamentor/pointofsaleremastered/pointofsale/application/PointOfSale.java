package pl.javamentor.pointofsaleremastered.pointofsale.application;

import lombok.RequiredArgsConstructor;
import pl.javamentor.pointofsaleremastered.receipt.domain.ReceiptFacade;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptClosed;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.command.AddProductToReceipt;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.command.CloseReceipt;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.exception.ProductNotFoundInCatalogException;

import static com.google.common.base.Preconditions.checkState;

@RequiredArgsConstructor
public class PointOfSale {

    private final ReceiptFacade receiptFacade;
    private final LcdDisplay lcdDisplay;
    private final Printer printer;

    private ReceiptId currentReceipt = null;

    public void onBarcodeScan(final ProductBarcode productBarcode) {
        final ReceiptId receiptId = getOpenedReceipt();
        final AddProductToReceipt addProductToReceipt = new AddProductToReceipt(receiptId, productBarcode);
        try {
            final ProductAddedToReceipt productAddedToReceipt = receiptFacade.addProductToReceipt(addProductToReceipt);
            lcdDisplay.show(productAddedToReceipt);
        } catch (final ProductNotFoundInCatalogException e) {
            lcdDisplay.show("Product not found");
        }
    }

    public void closeReceipt() {
        checkState(currentReceipt != null, "Cannot close not existent receipt");
        final ReceiptClosed receiptClosed = receiptFacade.closeReceipt(new CloseReceipt(currentReceipt));
        currentReceipt = null;
        lcdDisplay.show(receiptClosed.getTotalSum());
        printer.print(receiptClosed);
    }

    private ReceiptId getOpenedReceipt() {
        if (currentReceipt == null) {
            currentReceipt = receiptFacade.openReceipt();
        }
        return currentReceipt;
    }
}

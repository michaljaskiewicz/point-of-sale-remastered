package pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.incoming.console;

import lombok.Value;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleInputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleInput waitForInput() {
        System.out.println("Input scanned barcode or `exit`");
        final String input = scanner.nextLine();
        if ("exit".equals(input)) {
            return ConsoleInput.closeReceipt();
        }
        try {
            return ConsoleInput.scannedBarcode(new ProductBarcode(input));
        } catch (final IllegalArgumentException e) {
            return ConsoleInput.error("Invalid bar-code");
        }
    }

    @Value
    public static class ConsoleInput {
        ProductBarcode scannedBarcode;
        Object closeReceipt;
        String error;

        public static ConsoleInput scannedBarcode(final ProductBarcode scannedBarcode) {
            return new ConsoleInput(scannedBarcode, null, null);
        }

        public static ConsoleInput closeReceipt() {
            return new ConsoleInput(null, new Object(), null);
        }

        public static ConsoleInput error(final String error) {
            return new ConsoleInput(null, null, error);
        }

        public Optional<ProductBarcode> getScannedBarcode() {
            return Optional.ofNullable(scannedBarcode);
        }

		public Optional getCloseReceipt() {
            return Optional.ofNullable(closeReceipt);
        }

		public Optional<String> getError() {
            return Optional.ofNullable(error);
        }

    }
}

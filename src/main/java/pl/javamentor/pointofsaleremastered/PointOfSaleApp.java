package pl.javamentor.pointofsaleremastered;

import pl.javamentor.pointofsaleremastered.ioc.IocContainer;
import pl.javamentor.pointofsaleremastered.pointofsale.application.LcdDisplay;
import pl.javamentor.pointofsaleremastered.pointofsale.application.PointOfSale;
import pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.incoming.console.ConsoleInputHandler;
import pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.incoming.console.ConsoleInputHandler.ConsoleInput;

public class PointOfSaleApp {

    public static void main(String[] args) {
        new PointOfSaleApp().run();
    }

    private void run() {
        final IocContainer iocContainer = new IocContainer();

        final PointOfSale pointOfSale = iocContainer.getPointOfSale();
        final ConsoleInputHandler consoleInputHandler = iocContainer.getConsoleInputHandler();
        final LcdDisplay lcdDisplay = iocContainer.getLcdDisplay();

        while (true) { // application main loop
            final ConsoleInput consoleInput = consoleInputHandler.waitForInput();
            if (consoleInput.getScannedBarcode().isPresent()) {
                pointOfSale.onBarcodeScan(consoleInput.getScannedBarcode().get());
            } else if (consoleInput.getCloseReceipt().isPresent()) {
                pointOfSale.closeReceipt();
            } else if (consoleInput.getError().isPresent()) {
                lcdDisplay.show(consoleInput.getError().get());
            } else {
                throw new IllegalArgumentException("There was an error with console input interpretation, input = " + consoleInput);
            }
        }
    }


}

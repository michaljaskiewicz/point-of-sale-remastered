package pl.javamentor.pointofsaleremastered.ioc;

import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.pointofsale.application.LcdDisplay;
import pl.javamentor.pointofsaleremastered.pointofsale.application.PointOfSale;
import pl.javamentor.pointofsaleremastered.pointofsale.application.Printer;
import pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.incoming.console.ConsoleInputHandler;
import pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.outgoing.console.ConsoleOutputHandler;
import pl.javamentor.pointofsaleremastered.receipt.domain.ProductsCatalog;
import pl.javamentor.pointofsaleremastered.receipt.domain.ReceiptFacade;
import pl.javamentor.pointofsaleremastered.receipt.domain.ReceiptFacadeConfiguration;
import pl.javamentor.pointofsaleremastered.product.domain.model.Product;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductName;

import java.util.List;
import java.util.Optional;

public class IocContainer {

    private PointOfSale pointOfSale = null;
    private ReceiptFacade receiptFacade;
    private ProductsCatalog productsCatalog;
    private ConsoleInputHandler consoleInputHandler;
    private ConsoleOutputHandler consoleOutputHandler;

    public PointOfSale getPointOfSale() {
        if (pointOfSale == null) {
            pointOfSale = new PointOfSale(getReceiptFacade(), getLcdDisplay(), getPrinter());
        }
        return pointOfSale;
    }

    public ReceiptFacade getReceiptFacade() {
        if (receiptFacade == null) {
            receiptFacade = new ReceiptFacadeConfiguration().receiptFacade(getProductsCatalog());
        }
        return receiptFacade;
    }

    public ProductsCatalog getProductsCatalog() {
        if (productsCatalog == null) {
            productsCatalog = new ProductsCatalog() {

                private final List<Product> products = List.of(
                        new Product(new ProductBarcode("A-0010-Z"), new ProductName("Solar Charger"), new Money("86.43")),
                        new Product(new ProductBarcode("A-0020-Z"), new ProductName("Bluetooth Headphones"), new Money("92.73")),
                        new Product(new ProductBarcode("A-0030-Z"), new ProductName("Eco-Friendly Water Bottle"), new Money("91.51")),
                        new Product(new ProductBarcode("A-0040-Z"), new ProductName("Wireless Mouse"), new Money("64.55")),
                        new Product(new ProductBarcode("A-0050-Z"), new ProductName("Portable SSD 1TB"), new Money("14.56")),
                        new Product(new ProductBarcode("A-0060-Z"), new ProductName("Smartwatch"), new Money("20.71")),
                        new Product(new ProductBarcode("A-0070-Z"), new ProductName("Noise-Cancelling Earbuds"), new Money("8.82")),
                        new Product(new ProductBarcode("A-0080-Z"), new ProductName("Yoga Mat"), new Money("26.93")),
                        new Product(new ProductBarcode("A-0090-Z"), new ProductName("Electric Toothbrush"), new Money("44.00")),
                        new Product(new ProductBarcode("A-00100-Z"), new ProductName("LED Desk Lamp"), new Money("45.73"))
                );

                @Override
                public Optional<Product> findBy(final ProductBarcode barcode) {
                    return products.stream().filter(product -> product.getBarcode().equals(barcode)).findFirst();
                }
            };
        }
        return productsCatalog;
    }

    public ConsoleInputHandler getConsoleInputHandler() {
        if (consoleInputHandler == null) {
            consoleInputHandler = new ConsoleInputHandler();
        }
        return consoleInputHandler;
    }

    public ConsoleOutputHandler getConsoleOutputHandler() {
        if (consoleOutputHandler == null) {
            consoleOutputHandler = new ConsoleOutputHandler();
        }
        return consoleOutputHandler;
    }

    public Printer getPrinter() {
        return getConsoleOutputHandler();
    }

    public LcdDisplay getLcdDisplay() {
        return getConsoleOutputHandler();
    }

}

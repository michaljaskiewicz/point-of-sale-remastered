package pl.javamentor.pointofsaleremastered.pointofsale.application;

import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.receipt.domain.ProductsCatalog;
import pl.javamentor.pointofsaleremastered.receipt.domain.ReceiptFacadeAssembler;
import pl.javamentor.pointofsaleremastered.product.domain.model.Product;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductBarcodeSample;
import pl.javamentor.pointofsaleremastered.product.domain.model.ProductName;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptClosed;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PointOfSaleIntTest {

    private PointOfSale pos;
    private FakeLcdDisplay lcdDisplay;
    private FakePrinter printer;

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

    @BeforeEach
    void setUp() {
        lcdDisplay = new FakeLcdDisplay();
        printer = new FakePrinter();

        final ProductsCatalog productsCatalog = barcode -> products.stream().filter(product -> product.getBarcode().equals(barcode)).findFirst();
        pos = new PointOfSale(ReceiptFacadeAssembler.receiptFacade(productsCatalog), lcdDisplay, printer);
    }

    @Test
    void should_handle_single_customer_on_point_of_sale() {
        // given
        final Product productToBuy = products.get(1);
        final Product productToBuy2 = products.get(2);
        final Product productToBuy3 = products.get(5);

        // when
        pos.onBarcodeScan(productToBuy.getBarcode());

        // then
        assertThat(lcdDisplay.currentlyOnDisplay().getProduct()).contains(addedToReceipt(productToBuy));

        // when
        pos.onBarcodeScan(productToBuy2.getBarcode());

        // then
        assertThat(lcdDisplay.currentlyOnDisplay().getProduct()).contains(addedToReceipt(productToBuy2));

        // when
        pos.onBarcodeScan(productToBuy3.getBarcode());

        // then
        assertThat(lcdDisplay.currentlyOnDisplay().getProduct()).contains(addedToReceipt(productToBuy3));

        // when
        pos.closeReceipt();

        // then
        final Money expectedTotalSum = sumPrices(productToBuy, productToBuy2, productToBuy3);
        assertThat(lcdDisplay.currentlyOnDisplay().getTotalSum()).contains(expectedTotalSum);
        assertThat(printer.lastPrinted()).contains(expectedReceipt(expectedTotalSum, productToBuy, productToBuy2, productToBuy3));
    }

    @Test
    void should_show_error_message_on_lcd_display_when_scanned_barcode_product_cannot_be_found() {
        // given
        final ProductBarcode notExistingProduct = ProductBarcodeSample.create();

        // when
        pos.onBarcodeScan(notExistingProduct);

        // then
        assertThat(lcdDisplay.currentlyOnDisplay().getErrorMessage()).contains("Product not found");
        assertThat(printer.lastPrinted()).isNotPresent();
    }

    @Test
    void should_be_able_to_serve_next_customer_after_first_receipt_is_closed() {
        // given
        pos.onBarcodeScan(products.get(1).getBarcode());
        pos.closeReceipt();

        assertThat(lcdDisplay.currentlyOnDisplay()).isNotEqualTo(FakeLcdDisplay.OnDisplay.nothing());
        assertThat(printer.lastPrinted()).isPresent();

        // when
        pos.onBarcodeScan(products.get(2).getBarcode());
        pos.onBarcodeScan(products.get(2).getBarcode());
        pos.onBarcodeScan(products.get(4).getBarcode());

        // then
        assertThat(lcdDisplay.currentlyOnDisplay().getProduct()).contains(addedToReceipt(products.get(4)));

        // when
        pos.closeReceipt();

        // then
        final Money expectedTotalSum = sumPrices(products.get(2), products.get(2), products.get(4));
        assertThat(lcdDisplay.currentlyOnDisplay().getTotalSum()).contains(expectedTotalSum);
        assertThat(printer.lastPrinted()).contains(expectedReceipt(expectedTotalSum, products.get(2), products.get(2), products.get(4)));
    }

    private static ProductAddedToReceipt addedToReceipt(Product productToBuy) {
        return new ProductAddedToReceipt(productToBuy.getName(), productToBuy.getPrice());
    }

    private static Money sumPrices(Product productToBuy, Product productToBuy2, Product productToBuy3) {
        return productToBuy.getPrice().plus(productToBuy2.getPrice()).plus(productToBuy3.getPrice());
    }

    private static ReceiptClosed expectedReceipt(Money expectedTotalSum, Product productToBuy, Product... moreProducts) {
        return new ReceiptClosed(
                toSingleStream(productToBuy, moreProducts)
                        .map(product -> new ReceiptClosed.ProductOnReceipt(product.getName(), product.getPrice()))
                        .toList(),
                expectedTotalSum
        );
    }

    private static Stream<Product> toSingleStream(Product productToBuy, Product[] moreProducts) {
        return Stream.concat(Stream.of(productToBuy), Stream.of(moreProducts));
    }

    private static class FakeLcdDisplay implements LcdDisplay {

        private OnDisplay currentlyOnDisplay = OnDisplay.nothing();

        @Override
        public void show(ProductAddedToReceipt product) {
            currentlyOnDisplay = OnDisplay.product(product);
        }

        @Override
        public void show(Money totalSum) {
            currentlyOnDisplay = OnDisplay.totalSum(totalSum);
        }

        @Override
        public void show(String errorMessage) {
            currentlyOnDisplay = OnDisplay.errorMessage(errorMessage);
        }

        OnDisplay currentlyOnDisplay() {
            return currentlyOnDisplay;
        }

        @Value
        static class OnDisplay {
            ProductAddedToReceipt product;
            Money totalSum;
            String errorMessage;

            public static OnDisplay nothing() {
                return new OnDisplay(null, null, null);
            }

            public static OnDisplay product(final ProductAddedToReceipt product) {
                return new OnDisplay(product, null, null);
            }

            public static OnDisplay totalSum(final Money totalSum) {
                return new OnDisplay(null, totalSum, null);
            }

            public static OnDisplay errorMessage(final String errorMessage) {
                return new OnDisplay(null, null, errorMessage);
            }

            Optional<ProductAddedToReceipt> getProduct() {
                return Optional.ofNullable(product);
            }

            Optional<Money> getTotalSum() {
                return Optional.ofNullable(totalSum);
            }

            Optional<String> getErrorMessage() {
                return Optional.ofNullable(errorMessage);
            }

        }
    }

    private static class FakePrinter implements Printer {

        private ReceiptClosed receiptClosed;

        public Optional<ReceiptClosed> lastPrinted() {
            return Optional.ofNullable(receiptClosed);
        }

        @Override
        public void print(ReceiptClosed receiptClosed) {
            this.receiptClosed = receiptClosed;
        }
    }

}

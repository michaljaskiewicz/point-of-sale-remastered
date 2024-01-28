package pl.javamentor.pointofsaleremastered;

import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.PointOfSaleFacade;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.PointOfSaleFacadeConfiguration;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.ProductsCatalog;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.Product;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductBarcode;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductName;
import pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.console.ConsoleInputOutputHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class PointOfSaleApp {

	public static void main(String[] args) {
		new PointOfSaleApp().run();
	}

	private void run() {
		final PointOfSaleFacade pointOfSaleFacade = new PointOfSaleFacadeConfiguration().pointOfSaleFacade(getProductsCatalog());
		final ConsoleInputOutputHandler consoleHandler = new ConsoleInputOutputHandler();
		consoleHandler.registerBarcodeScannedListeners();
		// TODO

	}

	private static ProductsCatalog getProductsCatalog() {
		return new ProductsCatalog() {

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
}

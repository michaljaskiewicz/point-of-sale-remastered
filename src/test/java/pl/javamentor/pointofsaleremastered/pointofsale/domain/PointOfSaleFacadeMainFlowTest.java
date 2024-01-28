package pl.javamentor.pointofsaleremastered.pointofsale.domain;

import org.junit.jupiter.api.Test;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.Product;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductName;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed.ProductOnReceipt;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command.AddProductToReceiptSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command.CloseReceiptSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception.ProductNotFoundInCatalogException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.tuple;

class PointOfSaleFacadeMainFlowTest extends PointOfSaleFacadeTestBase {

	@Test
	void should_open_receipt_and_add_product() {
		// given
		final Product productInCatalog = productsCatalog.addProduct(ProductSample.builder()
				.name(new ProductName("Water bottle 2L"))
				.price(new Money("2.99"))
				.build());

		// when
		final ReceiptId receiptId = tut.openReceipt();
		final ProductAddedToReceipt productAddedToReceipt = tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(productInCatalog.getBarcode()).receiptId(receiptId).build());

		// then
		assertThat(productAddedToReceipt.getProductName()).isEqualTo("Water bottle 2L");
		assertThat(productAddedToReceipt.getProductPrice()).isEqualTo("2.99");
	}

	@Test
	void should_add_multiple_products_to_receipt_and_close_receipt() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().price(new Money("9.99")).build());
		final Product product_2 = productsCatalog.addProduct(ProductSample.builder().price(new Money("19.99")).build());
		final Product product_3 = productsCatalog.addProduct(ProductSample.builder().price(new Money("52.79")).build());

		final ReceiptId receiptId = tut.openReceipt();

		// when
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(receiptId).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(receiptId).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_2.getBarcode()).receiptId(receiptId).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_3.getBarcode()).receiptId(receiptId).build());

		final ReceiptClosed receiptClosed = tut.closeReceipt(CloseReceiptSample.builder().receiptId(receiptId).build());

		// then
		assertThat(receiptClosed.getProducts())
				.extracting(ProductOnReceipt::getProductName, ProductOnReceipt::getProductPrice)
				.containsExactly(
						tuple(product_1.getName().getValue(), product_1.getPrice().getValue()),
						tuple(product_1.getName().getValue(), product_1.getPrice().getValue()),
						tuple(product_2.getName().getValue(), product_2.getPrice().getValue()),
						tuple(product_3.getName().getValue(), product_3.getPrice().getValue())
				);
		assertThat(receiptClosed.getTotalSum()).isEqualTo("92.76");
	}

	@Test
	void should_ignore_not_existing_products_when_adding_to_receipt() {
		// given
		final Product fromCatalog = productsCatalog.addProduct(ProductSample.builder().build());
		final Product notInCatalog = ProductSample.builder().build();

		final ReceiptId receiptId = tut.openReceipt();

		// when trying to add product not existing in catalog
		final Throwable thrown = catchThrowable(() -> tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(notInCatalog.getBarcode()).receiptId(receiptId).build()));

		// then
		assertThat(thrown).isInstanceOf(ProductNotFoundInCatalogException.class);

		// and when trying to add product from catalog
		final ProductAddedToReceipt productAddedToReceipt = tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(fromCatalog.getBarcode()).receiptId(receiptId).build());

		// then
		assertThatSameProducts(productAddedToReceipt, fromCatalog);
	}

	@Test
	void should_be_able_to_open_new_receipt_after_previous_one_is_closed() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().price(new Money("9.99")).build());
		final Product product_2 = productsCatalog.addProduct(ProductSample.builder().price(new Money("52.79")).build());

		final ReceiptId previousReceipt = tut.openReceipt();
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(previousReceipt).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(previousReceipt).build());

		final ReceiptClosed previousReceiptClosed = tut.closeReceipt(CloseReceiptSample.builder().receiptId(previousReceipt).build());
		assertThat(previousReceiptClosed.getTotalSum()).isEqualTo("19.98");

		// when
		final ReceiptId newReceipt = tut.openReceipt();

		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(newReceipt).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_2.getBarcode()).receiptId(newReceipt).build());

		final ReceiptClosed newReceiptClosed = tut.closeReceipt(CloseReceiptSample.builder().receiptId(newReceipt).build());

		// then
		assertThat(newReceiptClosed.getTotalSum()).isEqualTo("62.78");
		assertThat(newReceiptClosed.getProducts())
				.extracting(ProductOnReceipt::getProductName, ProductOnReceipt::getProductPrice)
				.containsExactly(
						tuple(product_1.getName().getValue(), product_1.getPrice().getValue()),
						tuple(product_2.getName().getValue(), product_2.getPrice().getValue())
				);
	}
}

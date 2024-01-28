package pl.javamentor.pointofsaleremastered.pointofsale.domain;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.Product;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed.ProductOnReceipt;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptIdSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command.AddProductToReceiptSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.command.CloseReceiptSample;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception.ReceiptAlreadyClosedException;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception.ReceiptNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.groups.Tuple.tuple;

class PointOfSaleFacadeOtherTest extends PointOfSaleFacadeTestBase {

	@Test
	void should_add_many_products_to_opened_receipt() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().build());
		final Product product_2 = productsCatalog.addProduct(ProductSample.builder().build());
		final Product product_3 = productsCatalog.addProduct(ProductSample.builder().build());

		final ReceiptId receiptId = tut.openReceipt();

		// expect
		assertThat(tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product_1.getBarcode()).receiptId(receiptId).build()))
				.satisfies(productAddedToReceipt -> assertThatSameProducts(productAddedToReceipt, product_1));

		assertThat(tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product_2.getBarcode()).receiptId(receiptId).build()))
				.satisfies(productAddedToReceipt -> assertThatSameProducts(productAddedToReceipt, product_2));

		assertThat(tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product_3.getBarcode()).receiptId(receiptId).build()))
				.satisfies(productAddedToReceipt -> assertThatSameProducts(productAddedToReceipt, product_3));
	}

	@Test
	void should_be_able_to_add_same_product_multiple_times() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().build());

		final ReceiptId receiptId = tut.openReceipt();

		// when
		tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product_1.getBarcode()).receiptId(receiptId).build());

		// then first product can be added second time
		assertThat(tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product_1.getBarcode()).receiptId(receiptId).build()))
				.satisfies(productAddedToReceipt -> assertThatSameProducts(productAddedToReceipt, product_1));
	}

	@Test
	void should_not_add_products_to_not_existing_receipt() {
		// given
		final Product product = productsCatalog.addProduct(ProductSample.builder().build());
		final ReceiptId notExistingReceipt = ReceiptIdSample.create();

		// when
		final Throwable thrown = catchThrowable(() -> tut.addProductToReceipt(AddProductToReceiptSample.builder()
				.productBarcode(product.getBarcode()).receiptId(notExistingReceipt).build()));

		// then
		assertThat(thrown).isInstanceOf(ReceiptNotFoundException.class);
	}

	@Test
	void should_not_add_products_to_already_closed_receipt() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().build());

		final ReceiptId receiptId = tut.openReceipt();
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(receiptId).build());

		// when
		tut.closeReceipt(CloseReceiptSample.builder().receiptId(receiptId).build());

		// then
		assertThatThrownBy(() -> tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(receiptId).build()))
				.isInstanceOf(ReceiptAlreadyClosedException.class);
	}

	@Test // this test does not satisfy any requirement, but serves as current implementation documentation. Could be easily removed in the future when no longer suitable
	void all_receipts_should_be_managed_separately() {
		// given
		final Product product_1 = productsCatalog.addProduct(ProductSample.builder().build());
		final Product product_2 = productsCatalog.addProduct(ProductSample.builder().build());

		// when
		final ReceiptId receipt_1 = tut.openReceipt();
		final ReceiptId receipt_2 = tut.openReceipt();

		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_1.getBarcode()).receiptId(receipt_1).build());
		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_2.getBarcode()).receiptId(receipt_1).build());

		tut.addProductToReceipt(AddProductToReceiptSample.builder().productBarcode(product_2.getBarcode()).receiptId(receipt_2).build());

		final ReceiptClosed receiptClosed_1 = tut.closeReceipt(CloseReceiptSample.builder().receiptId(receipt_1).build());
		final ReceiptClosed receiptClosed_2 = tut.closeReceipt(CloseReceiptSample.builder().receiptId(receipt_2).build());

		// then
		assertThat(receiptClosed_1.getTotalSum()).isEqualTo(product_1.getPrice().plus(product_2.getPrice()).getValue());
		assertThat(receiptClosed_2.getTotalSum()).isEqualTo(product_2.getPrice().getValue());

		assertThat(receiptClosed_1.getProducts())
				.extracting(ProductOnReceipt::getProductName, ProductOnReceipt::getProductPrice)
				.containsExactly(
						tuple(product_1.getName().getValue(), product_1.getPrice().getValue()),
						tuple(product_2.getName().getValue(), product_2.getPrice().getValue())
				);

		assertThat(receiptClosed_2.getProducts())
				.extracting(ProductOnReceipt::getProductName, ProductOnReceipt::getProductPrice)
				.containsExactly(tuple(product_2.getName().getValue(), product_2.getPrice().getValue()));
	}

}

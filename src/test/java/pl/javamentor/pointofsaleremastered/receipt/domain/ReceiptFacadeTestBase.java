package pl.javamentor.pointofsaleremastered.receipt.domain;

import org.junit.jupiter.api.BeforeEach;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.Product;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptFacadeTestBase {

	protected ReceiptFacade tut;
	protected InMemoryProductsCatalog productsCatalog;

	@BeforeEach
	void setUp() {
		productsCatalog = new InMemoryProductsCatalog();
		tut = ReceiptFacadeAssembler.pointOfSaleFacade(productsCatalog);
	}

	protected static void assertThatSameProducts(final ProductAddedToReceipt actual, final Product expected) {
		assertThat(actual.getProductName()).isEqualTo(expected.getName().getValue());
		assertThat(actual.getProductPrice()).isEqualTo(expected.getPrice().getValue());
	}

}

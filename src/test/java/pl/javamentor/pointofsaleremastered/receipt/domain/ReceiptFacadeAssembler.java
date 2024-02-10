package pl.javamentor.pointofsaleremastered.receipt.domain;

public class ReceiptFacadeAssembler {

	public static ReceiptFacade receiptFacade(final ProductsCatalog productsCatalog) {
		return new ReceiptFacadeConfiguration().receiptFacade(productsCatalog, new InMemoryReceiptRepository());
	}

}

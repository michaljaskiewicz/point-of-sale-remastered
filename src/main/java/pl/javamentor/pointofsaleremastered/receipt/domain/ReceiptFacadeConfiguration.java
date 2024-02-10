package pl.javamentor.pointofsaleremastered.receipt.domain;

public class ReceiptFacadeConfiguration {

	public ReceiptFacade receiptFacade(final ProductsCatalog productsCatalog, final ReceiptRepository receiptRepository) {
		return new ReceiptFacade(productsCatalog, receiptRepository);
	}

	// I only need this method because I'm not using reflection based IOC container and I don't want to make ReceiptRepository public
	public ReceiptFacade receiptFacade(final ProductsCatalog productsCatalog) {
		return receiptFacade(productsCatalog, new InMemoryReceiptRepository());
	}

}

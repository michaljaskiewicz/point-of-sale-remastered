package pl.javamentor.pointofsaleremastered.receipt.domain;

class ReceiptFacadeAssembler {

	static ReceiptFacade pointOfSaleFacade(final ProductsCatalog productsCatalog) {
		return new ReceiptFacadeConfiguration().receiptFacade(productsCatalog, new InMemoryReceiptRepository());
	}

}

package pl.javamentor.pointofsaleremastered.pointofsale.domain;

public class PointOfSaleFacadeConfiguration {

	public PointOfSaleFacade pointOfSaleFacade(final ProductsCatalog productsCatalog, final ReceiptRepository receiptRepository) {
		return new PointOfSaleFacade(productsCatalog, receiptRepository);
	}

	// I only need this method because I'm not using any IOC container and I don't want to make ReceiptRepository public
	public PointOfSaleFacade pointOfSaleFacade(final ProductsCatalog productsCatalog) {
		return pointOfSaleFacade(productsCatalog, new InMemoryReceiptRepository());
	}

}

package pl.javamentor.pointofsaleremastered.pointofsale.domain;

class PointOfSaleFacadeAssembler {

	static PointOfSaleFacade pointOfSaleFacade(final ProductsCatalog productsCatalog) {
		return new PointOfSaleFacadeConfiguration().pointOfSaleFacade(productsCatalog, new InMemoryReceiptRepository());
	}

}

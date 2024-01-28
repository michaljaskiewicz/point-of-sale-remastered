package pl.javamentor.pointofsaleremastered.pointofsale.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.Product;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptClosed.ProductOnReceipt;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.ReceiptId;
import pl.javamentor.pointofsaleremastered.pointofsale.domain.model.exception.ReceiptAlreadyClosedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Receipt {

	@Getter
	private final ReceiptId receiptId;

	private final List<Product> products;

	private Money totalSum;

	private boolean closed;

	static Receipt create() {
		return new Receipt(new ReceiptId(UUID.randomUUID().toString()), new ArrayList<>(), Money.ZERO, false);
	}

	ProductAddedToReceipt addProduct(final Product product) {
		if (closed) {
			throw new ReceiptAlreadyClosedException(format(
					"Cannot add product to already closed receipt, receiptId = {0}, product = {1}", receiptId, product));
		}
		products.add(requireNonNull(product));
		totalSum = totalSum.plus(product.getPrice());
		return new ProductAddedToReceipt(product.getName().getValue(), product.getPrice().getValue());
	}

	ReceiptClosed close() {
		final List<ProductOnReceipt> productsOnReceipt = products.stream().map(this::toProductOnReceipt).toList();
		final ReceiptClosed receiptClosed = new ReceiptClosed(productsOnReceipt, totalSum.getValue());
		closed = true;
		return receiptClosed;
	}

	private ProductOnReceipt toProductOnReceipt(final Product product) {
		return new ProductOnReceipt(product.getName().getValue(), product.getPrice().getValue());
	}
}

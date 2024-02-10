package pl.javamentor.pointofsaleremastered.pointofsale.infrastructure.adapters.outgoing.console;

import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.pointofsale.application.LcdDisplay;
import pl.javamentor.pointofsaleremastered.pointofsale.application.Printer;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ReceiptClosed;

// naive console implementation
public class ConsoleOutputHandler implements LcdDisplay, Printer {

	@Override
	public void show(ProductAddedToReceipt product) {
		System.out.println(product);
	}

	@Override
	public void show(Money totalSum) {
		System.out.println("Total sum " + totalSum);
	}

	@Override
	public void show(String errorMessage) {
		System.out.println(errorMessage);
	}

	@Override
	public void print(ReceiptClosed receiptClosed) {
		System.out.println("Receipt summary" + receiptClosed);
	}
}

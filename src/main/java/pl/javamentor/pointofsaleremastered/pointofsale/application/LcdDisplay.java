package pl.javamentor.pointofsaleremastered.pointofsale.application;

import pl.javamentor.pointofsaleremastered.money.domain.model.Money;
import pl.javamentor.pointofsaleremastered.receipt.domain.model.ProductAddedToReceipt;

public interface LcdDisplay {

    void show(ProductAddedToReceipt product);

    void show(Money totalSum);

    void show(String errorMessage);

}

package pl.javamentor.pointofsaleremastered.money.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneySample {

	private static final BigDecimal MAXIMUM = new BigDecimal(1000);

	public static Money create() {
		return new Money(randomBigDecimal());
	}

	private static BigDecimal randomBigDecimal() {
		final BigDecimal random_0_to_1 = BigDecimal.valueOf(Math.random());
		final BigDecimal random_0_to_MAX = random_0_to_1.multiply(MAXIMUM);
		return random_0_to_MAX.setScale(2, RoundingMode.DOWN);
	}

}

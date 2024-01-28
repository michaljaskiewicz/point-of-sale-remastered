package pl.javamentor.pointofsaleremastered.money.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
public class Money {

	public static final Money ZERO = new Money("0");

	private final @NonNull BigDecimal value;

	// TODO: factory method
	public Money(final @NonNull String value) {
		checkArgument(isNotBlank(value), "Cannot create money from blank string");
		this.value = new BigDecimal(value);
		checkArgument(this.value.compareTo(BigDecimal.ZERO) >= 0, "Cannot be less than zero");
		checkArgument(hasNoMoreThan2DigitsAfterDecimalPoint(), "Cannot have more digits after decimal point than 2");
	}

	private boolean hasNoMoreThan2DigitsAfterDecimalPoint() {
		return this.value.scale() <= 2;
	}

	public Money plus(final Money other) {
		final BigDecimal sum = value.add(other.value);
		return new Money(sum);
	}

	public String getValue() {
		return value.toPlainString();
	}
}

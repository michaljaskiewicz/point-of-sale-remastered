package pl.javamentor.pointofsaleremastered.money.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

	@Test
	void should_throw_exception_when_invalid_money() {
		assertThatThrownBy(() -> new Money("")).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Money("  ")).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Money("-1.32")).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Money("5.322")).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> new Money((String) null)).isInstanceOf(NullPointerException.class);
		assertThatThrownBy(() -> new Money((BigDecimal) null)).isInstanceOf(NullPointerException.class);
	}

	@Test
	void should_sum() {
		// given
		final Money a = new Money("2.43");
		final Money b = new Money("7.89");
		final Money c = new Money("12.78");

		// expect
		assertThat(a.plus(b)).isEqualTo(new Money("10.32"));
		assertThat(b.plus(a)).isEqualTo(new Money("10.32"));

		assertThat(a.plus(c)).isEqualTo(new Money("15.21"));
		assertThat(c.plus(a)).isEqualTo(new Money("15.21"));

		assertThat(a.plus(b).plus(c)).isEqualTo(new Money("23.10"));
	}

}

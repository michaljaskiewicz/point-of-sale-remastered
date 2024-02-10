package pl.javamentor.pointofsaleremastered.product.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@ToString
@EqualsAndHashCode
public class ProductBarcode {

	private final String value;

	public ProductBarcode(final String value) {
		checkArgument(isNotBlank(value), "ProductBarcode cannot be null or blank");
		this.value = value;
	}
}

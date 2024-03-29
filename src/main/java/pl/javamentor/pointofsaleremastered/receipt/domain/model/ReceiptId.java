package pl.javamentor.pointofsaleremastered.receipt.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@ToString
@EqualsAndHashCode
public class ReceiptId {

	private final String value;

	public ReceiptId(final String value) {
		checkArgument(isNotBlank(value), "ReceiptId cannot be null or blank");
		this.value = value;
	}
}

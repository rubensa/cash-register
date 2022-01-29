package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eu.rubensa.cashregister.model.Product;

/**
 * {@link PricingRule} that aplies special price for the {@link Product}s when
 * the quantity brought is equal or greater than a given quantity.
 */
public class BulkPricingRule extends DefaultPricingRule {
  public static final String BULK_PRICING_RULE_DESCRIPTION = "Bulk product price";

  /** Min Bulk Quantity **/
  private BigInteger quantity;
  /** Bulk Price **/
  private BigDecimal price;

  public BulkPricingRule(BigInteger quantity, BigDecimal price) {
    this(quantity, price, BULK_PRICING_RULE_DESCRIPTION);
  }

  public BulkPricingRule(BigInteger quantity, BigDecimal price,
      String description) {
    super(description);
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public BigDecimal apply(Product product, BigInteger quantity) {
    if (quantity.compareTo(this.quantity) >= 0) {
      // apply special rule price
      return price.multiply(new BigDecimal(quantity));
    }
    // apply default Product price
    return super.apply(product, quantity);
  }

}

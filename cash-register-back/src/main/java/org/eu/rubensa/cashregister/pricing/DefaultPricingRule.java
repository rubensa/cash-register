package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eu.rubensa.cashregister.model.Product;

/**
 * {@link PricingRule} that multiplies the {@link Product} price by the quantity
 * of {@link Product}s.
 */
public class DefaultPricingRule implements PricingRule {
  public static final String DEFAULT_PRICING_RULE_DESCRIPTION = "Default product price";

  private String description;

  public DefaultPricingRule() {
    this(DEFAULT_PRICING_RULE_DESCRIPTION);
  }

  public DefaultPricingRule(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public BigDecimal apply(Product product, BigInteger quantity) {
    return product.getPrice().multiply(new BigDecimal(quantity));
  }

}

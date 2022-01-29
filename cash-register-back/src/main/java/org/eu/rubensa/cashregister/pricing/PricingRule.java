package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eu.rubensa.cashregister.model.Product;

/**
 * Pricing Rule
 */
public interface PricingRule {
  /**
   * The description of the rule.
   * 
   * @return the description
   */
  String getDescription();

  /**
   * The total amount to be paid for the given quantity of {@link Product}s.
   * 
   * @param product  the Product to apply the Pricing Rule
   * @param quantity the quantity of {@link Product}s
   * @return the total amount
   */
  BigDecimal apply(Product product, BigInteger quantity);
}

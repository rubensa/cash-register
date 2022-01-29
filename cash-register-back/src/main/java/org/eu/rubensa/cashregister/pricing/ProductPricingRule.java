package org.eu.rubensa.cashregister.pricing;

/**
 * A {@link PricingRule} that can be applied to a {@link Product} with specific
 * code only.
 */
public interface ProductPricingRule extends PricingRule {
  /**
   * The code of {@link Product} this rule applies to.
   * 
   * @return the code of the {@link Product}
   */
  String getCode();
}

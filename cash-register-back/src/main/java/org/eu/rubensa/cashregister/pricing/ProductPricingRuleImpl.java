package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eu.rubensa.cashregister.model.Product;

/**
 * Build a {@link ProductPricingRule} from a {@link PricingRule}
 * restricting its application to the specified {@link Product} code.
 */
public class ProductPricingRuleImpl implements ProductPricingRule {
  private String code;
  private PricingRule wrappedPricingRule;
  private String description;

  public ProductPricingRuleImpl(String code, PricingRule pricingRule) {
    this(code, pricingRule, pricingRule.getDescription());
  }

  public ProductPricingRuleImpl(String code, PricingRule pricingRule, String description) {
    this.code = code;
    this.wrappedPricingRule = pricingRule;
    this.description = description;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public BigDecimal apply(Product product, BigInteger quantity) {
    if (code.equals(product.getCode())) {
      return wrappedPricingRule.apply(product, quantity);
    }
    throw new IllegalArgumentException(
        String.format("Invalid product code '%s'.  This rule can only be applied to '%s'", product.getCode(), code));
  }

}

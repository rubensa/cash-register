package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.assertj.core.api.Assertions;
import org.eu.rubensa.cashregister.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PackPricingRuleTest {
  @ParameterizedTest
  @CsvSource(value = {// @formatter:off
      "7.5, 7.5, 2, 0,  0.0",
      "7.5, 7.5, 2, 1,  7.5",
      "7.5, 7.5, 2, 2,  7.5",
      "7.5, 7.5, 2, 3,   15"
      // @formatter:on
  })
  public void result_should_match_the_precalculated_total(BigDecimal price, BigDecimal packPrice,
      BigInteger packQuantity, BigInteger quantity, BigDecimal total) {
    // given: a Product and a PackPrincingRule definition
    Product product = Product.builder().code("PROD").name("Product").price(price).build();
    PricingRule pricingRule = new PackPricingRule(packQuantity, packPrice);
    // when: PackPricingRule applied
    BigDecimal returnedTotal = pricingRule.apply(product, quantity);
    // then: the result matches the precalculated total value
    Assertions.assertThat(returnedTotal.compareTo(total)).isEqualTo(0);
  }
}

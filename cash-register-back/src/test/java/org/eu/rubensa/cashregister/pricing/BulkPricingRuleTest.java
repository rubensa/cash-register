package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.assertj.core.api.Assertions;
import org.eu.rubensa.cashregister.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BulkPricingRuleTest {
  @ParameterizedTest
  @CsvSource(value = {// @formatter:off
      "7.5, 7, 2, 0,  0.0",
      "7.5, 7, 2, 1,  7.5",
      "7.5, 7, 2, 2, 14.0",
      "7.5, 7, 2, 3,   21"
      // @formatter:on
  })
  public void result_should_match_the_precalculated_total(BigDecimal price, BigDecimal packPrice,
      BigInteger packQuantity, BigInteger quantity, BigDecimal total) {
    // given: a Product and a BulkPricingRule definition
    Product product = Product.builder().code("PROD").name("Product").price(price).build();
    PricingRule pricingRule = new BulkPricingRule(packQuantity, packPrice);
    // when: BulkPrincingRule applied
    BigDecimal returnedTotal = pricingRule.apply(product, quantity);
    // then: the result matches the precalculated total value
    Assertions.assertThat(returnedTotal.compareTo(total)).isEqualTo(0);
  }
}

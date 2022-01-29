package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.assertj.core.api.Assertions;
import org.eu.rubensa.cashregister.model.Product;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DefaultPricingRuleTest {
  PricingRule pricingRule = new DefaultPricingRule();

  @ParameterizedTest
  @CsvSource(value = {// @formatter:off
      "7.5, 0",
      "7.5, 1",
      "7.5, 2"
      // @formatter:on
  })
  public void result_should_match_the_product_price_multiplied_by_the_quantity(BigDecimal price,
      BigInteger quantity) {
    // given: a Product
    Product product = Product.builder().code("PROD").name("Product").price(price).build();
    // when: DefaultPricingRule applied
    BigDecimal returnedTotal = pricingRule.apply(product, quantity);
    // then: the result matches the product price multiplied by the quantity
    Assertions.assertThat(returnedTotal.compareTo(price.multiply(new BigDecimal(quantity)))).isEqualTo(0);
  }
}

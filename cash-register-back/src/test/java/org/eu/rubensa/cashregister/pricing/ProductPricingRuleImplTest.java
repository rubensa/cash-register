package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.assertj.core.api.Assertions;
import org.eu.rubensa.cashregister.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductPricingRuleImplTest {
  @Mock
  PricingRule pricingRule;

  @Test
  void should_throw_illegal_argument_exception() {
    // given: a ProductPricingRuleImpl and product not matching the code
    ProductPricingRuleImpl ProductPricingRuleImpl = new ProductPricingRuleImpl("CODE#1",
        pricingRule);
    Product product = Product.builder().code("PROD").name("Product").price(new BigDecimal(1)).build();

    Assertions.assertThatThrownBy(() -> {
      // when: ProductPricingRuleImpl applied to a product with the wrong code
      ProductPricingRuleImpl.apply(product, BigInteger.valueOf(1));
      // then: IllegalArgumentException is thrown
    }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("PROD").hasMessageContaining("CODE#1");
  }

  @ParameterizedTest
  @CsvSource(value = {// @formatter:off
      "7.5, 0,  0.0",
      "7.5, 1,  7.5",
      "7.5, 2, 14.0",
      "7.5, 3,   21"
      // @formatter:on
  })
  void should_match_the_wrapped_pricing_rule_result(BigDecimal price, BigInteger quantity, BigDecimal total) {
    // given: a ProductPricingRuleImpl and product matching the code
    ProductPricingRuleImpl ProductPricingRuleImpl = new ProductPricingRuleImpl("CODE#1",
        pricingRule);
    Product product = Product.builder().code("CODE#1").name("Product").price(price).build();
    BDDMockito.given(pricingRule.apply(ArgumentMatchers.<Product>any(), ArgumentMatchers.<BigInteger>eq(quantity)))
        .willReturn(total);

    // when: ProductPricingRuleImpl applied to a product with the code
    BigDecimal returnedTotal = ProductPricingRuleImpl.apply(product, quantity);
    // then: the result matches the expected value
    Assertions.assertThat(returnedTotal.compareTo(total)).isEqualTo(0);
  }
}

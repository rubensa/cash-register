package org.eu.rubensa.cashregister.checkout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.eu.rubensa.cashregister.model.Product;
import org.eu.rubensa.cashregister.pricing.BulkPricingRule;
import org.eu.rubensa.cashregister.pricing.PackPricingRule;
import org.eu.rubensa.cashregister.pricing.ProductPricingRule;
import org.eu.rubensa.cashregister.pricing.ProductPricingRuleImpl;
import org.eu.rubensa.cashregister.repository.ProductRepository;
import org.eu.rubensa.test.util.StringArrayConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {
  @Mock
  ProductRepository productRepository;

  Product VOUCHER = Product.builder().code("VOUCHER").name("Gift Card").price(new BigDecimal(5)).build();
  Product TSHIRT = Product.builder().code("TSHIRT").name("Summer T-Shirt").price(new BigDecimal(20)).build();
  Product PANTS = Product.builder().code("PANTS").name("Summer Pants").price(new BigDecimal(7.5)).build();
  List<ProductPricingRule> pricingRules = Arrays.asList(
      new ProductPricingRuleImpl(VOUCHER.getCode(), new PackPricingRule(BigInteger.valueOf(2), VOUCHER.getPrice()),
          "A 2-for-1 special on VOUCHER items"),
      new ProductPricingRuleImpl(TSHIRT.getCode(), new BulkPricingRule(BigInteger.valueOf(3), new BigDecimal(19)),
          "If you buy 3 or more TSHIRT items, the price per unit should be 19.00€"));

  @Test
  void should_throw_illegal_argument_exception() {
    // given: a no existing product code
    String unknownProductCode = "unknown";
    BDDMockito.given(productRepository.existsById(ArgumentMatchers.<String>any()))
        .willReturn(false);
    Checkout checkout = new CheckoutService(productRepository, null);

    Assertions.assertThatThrownBy(() -> {
      // when: scan method invoqued
      checkout.scan(unknownProductCode);
      // then: no ProductNotFoundException is thrown
    }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(unknownProductCode);
  }

  @ParameterizedTest
  @CsvSource(value = {// @formatter:off
      "VOUCHER|TSHIRT|PANTS, 32.5",
      "VOUCHER|TSHIRT|VOUCHER, 25",
      "TSHIRT|TSHIRT|TSHIRT|VOUCHER|TSHIRT, 81",
      "VOUCHER|TSHIRT|VOUCHER|VOUCHER|PANTS|TSHIRT|TSHIRT, 74.5"
      // @formatter:on
  })
  void should_match_the_expected_total(@ConvertWith(StringArrayConverter.class) String[] codes, BigDecimal total) {
    // given: a no existing product code
    BDDMockito.given(productRepository.existsById(ArgumentMatchers.<String>any()))
        .willReturn(true);
    BDDMockito.given(productRepository.findById(ArgumentMatchers.<String>any()))
        .willAnswer(new Answer<Optional<Product>>() {
          @Override
          public Optional<Product> answer(InvocationOnMock invocation) throws Throwable {
            String code = invocation.getArgument(0, String.class);
            if (VOUCHER.getCode().equals(code)) {
              return Optional.of(VOUCHER);
            }
            if (TSHIRT.getCode().equals(code)) {
              return Optional.of(TSHIRT);
            }
            if (PANTS.getCode().equals(code)) {
              return Optional.of(PANTS);
            }
            return Optional.empty();
          }
        });

    Checkout checkout = new CheckoutService(productRepository, pricingRules);

    // when: given codes are scaned and getTotal method invoqued
    for (String code : codes) {
      checkout.scan(code);
    }
    BigDecimal returnedTotal = checkout.getTotal();
    // then: the total has the correct value
    Assertions.assertThat(returnedTotal.compareTo(total)).isEqualTo(0);
  }
}

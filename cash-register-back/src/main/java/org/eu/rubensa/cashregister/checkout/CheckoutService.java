package org.eu.rubensa.cashregister.checkout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eu.rubensa.cashregister.model.Product;
import org.eu.rubensa.cashregister.pricing.DefaultPricingRule;
import org.eu.rubensa.cashregister.pricing.PricingRule;
import org.eu.rubensa.cashregister.pricing.ProductPricingRule;
import org.eu.rubensa.cashregister.repository.ProductRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckoutService implements Checkout {
  private static final DefaultPricingRule DEFAULT_PRICING_RULE = new DefaultPricingRule();
  private ProductRepository productRepository;
  private List<String> codes;
  private Map<String, BigInteger> productQuantity;
  private Map<String, ProductPricingRule> pricingRules;

  public CheckoutService(ProductRepository productRepository, List<ProductPricingRule> pricingRules) {
    this.productRepository = productRepository;
    this.pricingRules = new HashMap<>();
    if (!CollectionUtils.isEmpty(pricingRules)) {
      for (ProductPricingRule pricingRule : pricingRules) {
        addPricingRule(pricingRule);
      }
    }
    codes = new ArrayList<>();
    productQuantity = new HashMap<>();
  }

  @Override
  public void scan(String code) {
    // check product existence
    if (!productRepository.existsById(code)) {
      throw new IllegalArgumentException(String.format("Invalid product code '%s'.", code));
    }
    codes.add(code);
    BigInteger quantity = productQuantity.get(code);
    if (ObjectUtils.isEmpty(quantity)) {
      quantity = BigInteger.valueOf(0);
    }
    productQuantity.put(code, quantity.add(BigInteger.valueOf(1)));
  }

  @Override
  public List<String> getScanned() {
    return codes;
  }

  @Override
  public BigDecimal getTotal() {
    BigDecimal total = new BigDecimal(0);
    for (String code : productQuantity.keySet()) {
      BigDecimal productTotal = getTotal(code);
      total = total.add(productTotal);
    }
    return total;
  }

  private BigDecimal getTotal(String code) {
    Product product = productRepository.findById(code)
        .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid product code '%s'.", code)));
    BigInteger quantity = productQuantity.get(code);
    if (ObjectUtils.isEmpty(quantity) || quantity.equals(BigInteger.valueOf(0))) {
      // shortcut
      return BigDecimal.valueOf(0);
    }
    PricingRule pricingRule = this.pricingRules.get(code);
    if (ObjectUtils.isEmpty(pricingRule)) {
      pricingRule = DEFAULT_PRICING_RULE;
    }
    return pricingRule.apply(product, quantity);
  }

  private void addPricingRule(ProductPricingRule pricingRule) {
    if (pricingRules.containsKey(pricingRule.getCode())) {
      log.warn(String.format("%s pricing rule updated!", pricingRule));
    }
    pricingRules.put(pricingRule.getCode(), pricingRule);
  }
}

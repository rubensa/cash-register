package org.eu.rubensa.cashregister;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.eu.rubensa.cashregister.pricing.BulkPricingRule;
import org.eu.rubensa.cashregister.pricing.PackPricingRule;
import org.eu.rubensa.cashregister.pricing.ProductPricingRule;
import org.eu.rubensa.cashregister.pricing.ProductPricingRuleImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CashRegisterCliApplication {

  public static void main(String[] args) {
    SpringApplication.run(CashRegisterCliApplication.class, args);
  }

  @Bean
  public List<ProductPricingRule> getPricingRules() {
    List<ProductPricingRule> pricingRules = Arrays.asList(
        new ProductPricingRuleImpl("VOUCHER", new PackPricingRule(BigInteger.valueOf(2), new BigDecimal(
            5)),
            "A 2-for-1 special on VOUCHER items"),
        new ProductPricingRuleImpl("TSHIRT", new BulkPricingRule(BigInteger.valueOf(3), new BigDecimal(19)),
            "If you buy 3 or more TSHIRT items, the price per unit should be 19.00€"));
    return pricingRules;
  }
}

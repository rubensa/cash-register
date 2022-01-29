package org.eu.rubensa.cashregister.command;

import java.util.List;
import java.util.stream.Collectors;

import org.eu.rubensa.cashregister.pricing.ProductPricingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DiscountCommands {
  private List<ProductPricingRule> pricingRules;

  @Autowired
  public DiscountCommands(List<ProductPricingRule> pricingRules) {
    this.pricingRules = pricingRules;
  }

  @ShellMethod("Availabe discounts")
  public String discounts() {
    List<String> pricingRulesInfo = pricingRules.stream().map(pricingRule -> pricingRule.getDescription())
        .collect(Collectors.toList());

    return String.format("Available discounts: \n%s", String.join("\n", pricingRulesInfo));
  }
}
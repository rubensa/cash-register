package org.eu.rubensa.cashregister.command;

import java.math.BigDecimal;
import java.util.List;

import org.eu.rubensa.cashregister.checkout.Checkout;
import org.eu.rubensa.cashregister.checkout.CheckoutService;
import org.eu.rubensa.cashregister.pricing.ProductPricingRule;
import org.eu.rubensa.cashregister.provider.ProductCodeValueProvider;
import org.eu.rubensa.cashregister.repository.ProductRepository;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CheckoutCommands {
  private ProductRepository productRepository;
  private List<ProductPricingRule> pricingRules;
  private Checkout checkout;

  @Autowired
  public CheckoutCommands(ProductRepository productRepository, List<ProductPricingRule> pricingRules) {
    this.productRepository = productRepository;
    this.pricingRules = pricingRules;
    reset();
  }

  @ShellMethod("Scan Item code")
  public String scan(@ShellOption(help = "Product code", valueProvider = ProductCodeValueProvider.class) String code) {
    try {
      checkout.scan(code.toUpperCase());
    } catch (IllegalArgumentException e) {
      String message = String.format("Item: %s NOT FOUND!", code);
      return (new AttributedStringBuilder())
          .append(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi();
    }
    List<String> codes = checkout.getScanned();
    BigDecimal total = checkout.getTotal();
    return String.format("Items: %s - Total: %.2f€", String.join(",", codes), total);
  }

  @ShellMethod("Checkout current products")
  public String checkout() {
    List<String> codes = checkout.getScanned();
    BigDecimal total = checkout.getTotal();
    reset();
    return String.format("Items: %s - Total: %.2f€\nCHECKED OUT!", String.join(",", codes), total);
  }

  @ShellMethod("Rest Checkout")
  public String reset() {
    checkout = new CheckoutService(productRepository, pricingRules);
    List<String> codes = checkout.getScanned();
    BigDecimal total = checkout.getTotal();
    return String.format("Items: %s - Total: %.2f€", String.join(",", codes), total);
  }

}
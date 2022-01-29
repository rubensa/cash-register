package org.eu.rubensa.cashregister.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eu.rubensa.cashregister.model.Product;
import org.eu.rubensa.cashregister.provider.ProductCodeValueProvider;
import org.eu.rubensa.cashregister.repository.ProductRepository;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.CollectionUtils;

@ShellComponent
public class ProductCommands {
  private ProductRepository productRepository;
  List<String> codes;

  public ProductCommands(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @ShellMethod("Availabe product codes")
  public String products() {
    return String.format("Available product codes: %s", String.join(",", getCodes()));
  }

  @ShellMethod("Product detail")
  public String product(
      @ShellOption(help = "Product code", valueProvider = ProductCodeValueProvider.class) String code) {
    Optional<Product> product = productRepository.findById(code.toUpperCase());
    if (product.isEmpty()) {
      String message = String.format("Product: %s NOT FOUND!", code);
      return (new AttributedStringBuilder())
          .append(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi();
    }
    return String.format("Product details: %s", product.get().toString());
  }

  private List<String> getCodes() {
    // Load Product codes only once then use cached codes
    if (CollectionUtils.isEmpty(codes)) {
      codes = new ArrayList<>();
      Iterable<Product> products = productRepository.findAll();
      for (Product product : products) {
        codes.add(product.getCode());
      }
    }
    return codes;
  }
}
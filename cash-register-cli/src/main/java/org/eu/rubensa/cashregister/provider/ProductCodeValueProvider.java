package org.eu.rubensa.cashregister.provider;

import java.util.ArrayList;
import java.util.List;

import org.eu.rubensa.cashregister.model.Product;
import org.eu.rubensa.cashregister.repository.ProductRepository;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ProductCodeValueProvider implements ValueProvider {
  private ProductRepository productRepository;
  List<String> codes;

  public ProductCodeValueProvider(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
    return true;
  }

  @Override
  public List<CompletionProposal> complete(MethodParameter parameter, CompletionContext completionContext,
      String[] hints) {
    List<CompletionProposal> result = new ArrayList<>();

    List<String> codes = getCodes();
    String userInput = completionContext.currentWordUpToCursor();
    codes.stream()
        .filter(t -> t.startsWith(userInput))
        .forEach(t -> result.add(new CompletionProposal(t)));

    return result;
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

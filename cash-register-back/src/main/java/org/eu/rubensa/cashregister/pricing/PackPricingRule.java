package org.eu.rubensa.cashregister.pricing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eu.rubensa.cashregister.model.Product;

/**
 * {@link PricingRule} that aplies special price for packs of {@link Product}s.
 * <p>
 * Each pack is compounded by an specified number of {@link Product}s.
 * <p>
 * Remaining {@link Product}s after aplying the packaging are charged using
 * the {@link DefaultPricingRule}.
 */
public class PackPricingRule extends DefaultPricingRule {
  public static final String PACK_PRICING_RULE_DESCRIPTION = "Pack product price";

  /** Pack Quantity **/
  private BigInteger quantity;
  /** Pack Price **/
  private BigDecimal price;

  public PackPricingRule(BigInteger quantity, BigDecimal price) {
    this(quantity, price, PACK_PRICING_RULE_DESCRIPTION);
  }

  public PackPricingRule(BigInteger quantity, BigDecimal price,
      String description) {
    super(description);
    this.quantity = quantity;
    this.price = price;
  }

  @Override
  public BigDecimal apply(Product product, BigInteger quantity) {
    BigInteger[] division = quantity.divideAndRemainder(this.quantity);

    BigInteger packs = division[0];
    BigInteger notPackedItems = division[1];

    BigDecimal packedItemsTotal = price.multiply(new BigDecimal(packs));
    BigDecimal notPackedItemsTotal = super.apply(product,
        notPackedItems);

    return packedItemsTotal.add(notPackedItemsTotal);
  }

}

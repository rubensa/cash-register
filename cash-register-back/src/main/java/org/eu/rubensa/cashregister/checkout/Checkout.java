package org.eu.rubensa.cashregister.checkout;

import java.math.BigDecimal;
import java.util.List;

public interface Checkout {
  /**
   * Scans a {@link Product} code and adds it to the current checkout process
   * 
   * @param code a {@link Product} code
   */
  void scan(String code);

  /**
   * Return the list of all scanned product codes (keeping the scan
   * order).
   * 
   * @return the list of scanned product codes
   */
  List<String> getScanned();

  /**
   * Gets current total ammount for the checout process.
   * 
   * @return the total ammount
   */
  BigDecimal getTotal();
}

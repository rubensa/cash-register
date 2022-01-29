package org.eu.rubensa.cashregister.history;

import java.io.IOException;

import org.jline.reader.impl.history.DefaultHistory;
import org.springframework.stereotype.Component;

/**
 * Disables logging of history commands
 */
@Component
public class NoSaveHistory extends DefaultHistory {
  @Override
  public void save() throws IOException {

  }
}
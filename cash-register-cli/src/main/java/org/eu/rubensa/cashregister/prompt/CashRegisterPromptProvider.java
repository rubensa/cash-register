package org.eu.rubensa.cashregister.prompt;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CashRegisterPromptProvider implements PromptProvider {

  @Override
  public AttributedString getPrompt() {
    return new AttributedString("cash-register:>");
  }
}
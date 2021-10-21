package dev.henko.message.api.impl;

import dev.henko.message.api.MessageReplacer;
import org.jetbrains.annotations.NotNull;


public class DefaultMessageReplacer
  implements MessageReplacer<String> {

  @Override
  public @NotNull String replace(String message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }

    for(int i = 0; i < replacements.length; i+=2) {
      message = message.replace(
        replacements[i].toString(),
        replacements[i+1].toString()
      );
    }

    return message;
  }

}

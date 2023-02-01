package dev.henko.message.api.format.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StringReplacer
    implements Replacer {

  @Override
  public @NotNull String replace(String message, Object... replacements) {
    if (replacements.length % 2 != 0) {
      return message;
    }

    for (int i = 0; i < replacements.length; i += 2) {
      message = message.replace(
          replacements[i].toString(),
          replacements[i + 1].toString()
      );
    }

    return message;
  }

  @Override
  public @NotNull List<String> replace(List<String> message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }
    message.replaceAll(s -> replace(s, replacements));
    return message;
  }
}

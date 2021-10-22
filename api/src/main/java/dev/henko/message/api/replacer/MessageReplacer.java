package dev.henko.message.api.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MessageReplacer<T>
  extends Replacer<T> {

  /**
   * {@inheritDoc}
   */
  @NotNull
  default List<T> replace(List<T> message, Object... replacements) {
    if(replacements.length % 2 != 0) {
      return message;
    }
    message.replaceAll(s -> replace(s, replacements));

    return message;
  }
}

package dev.henko.message.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MessageReplacer<T> {

  /**
   * Replace placeholders in a message
   *
   * @param message Message to be replaced
   * @param replacements Placeholders that will be applied
   * @return The message replaced
   */
  @NotNull
  T replace(T message, Object... replacements);

  /**
   * Replace placeholders in a message list
   *
   * @param message Message list to be replaced
   * @param replacements Placeholders that will be applied
   * @return The message list replaced
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

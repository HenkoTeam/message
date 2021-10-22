package dev.henko.message.api.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Replacer<T> {

  /**
   * Replace placeholders in a message
   *
   * @param message Message to be replaced
   * @param objects Placeholders that will be applied
   * @return The message replaced
   */
  @NotNull
  T replace(T message, Object... objects);

  /**
   * Replace placeholders in a message list
   *
   * @param message Message list to be replaced
   * @param objects Placeholders that will be applied
   * @return The message list replaced
   */
  @NotNull
  List<T> replace(List<T> message, Object... objects);

}

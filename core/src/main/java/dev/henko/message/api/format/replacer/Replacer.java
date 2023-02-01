package dev.henko.message.api.format.replacer;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Replacer {

  /**
   * Replace placeholders in a message
   *
   * @param message Message to be replaced
   * @param params Placeholders that will be applied
   * @return The message replaced
   */
  @NotNull
  String replace(String message, Object... params);

  /**
   * Replace placeholders in a message list
   *
   * @param message Message list to be replaced
   * @param params Placeholders that will be applied
   * @return The message list replaced
   */
  @NotNull
  List<String> replace(List<String> message, Object... params);

}
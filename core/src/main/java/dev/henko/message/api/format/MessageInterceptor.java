package dev.henko.message.api.format;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@FunctionalInterface
public interface MessageInterceptor {

  /**
   * Intercepts a message before it is sent
   *
   * @param message Message to be intercepted
   */
  @NotNull String intercept(String message);

  /**
   * Intercepts a message list before it is sent
   *
   * @param list Message list to be intercepted
   */
  default void intercept(List<String> list) {
    list.replaceAll(this::intercept);
  }
}
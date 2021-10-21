package dev.henko.message.api;

import java.util.List;

/**
 * Represents a modifier for messages before they are sent
 *
 * @param <T> The message type
 */
@FunctionalInterface
public interface MessageInterceptor<T> {

  /**
   * Intercepts a message before it is sent
   *
   * @param message Message to be intercepted
   */
  void intercept(T message);

  /**
   * Intercepts a message list before it is sent
   *
   * @param list Message list to be intercepted
   */
  default void intercept(List<T> list) {
    list.replaceAll(s -> {
      intercept(list);
      return s;
    });
  }
}

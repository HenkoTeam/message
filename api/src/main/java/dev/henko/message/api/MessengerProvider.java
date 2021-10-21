package dev.henko.message.api;

/**
 * A interface to create and provide
 * default messengers
 *
 * @param <T> The message type
 */
public interface MessengerProvider<T> {

  /**
   * Get the configured messenger
   *
   * @return A new messenger
   */
  Messenger<T> get();
}

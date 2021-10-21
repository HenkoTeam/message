package dev.henko.message.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Send messages to entities and get messages from source
 *
 * @param <T> The message type
 */
public interface Messenger<T> {

  /**
   * Sends a message to entity
   *
   * @param entity Entity that will receive the message
   * @param path Message source path
   * @param replacements Placeholders that will be applied
   * @param <E> Entity type
   */
  <E> void send(E entity, String path, Object... replacements);

  /**
   * Sends a message list to entity
   *
   * @param entity Entity that will receive the message
   * @param path Message list source path
   * @param replacements Placeholders that will be applied
   * @param <E> Entity type
   */
  <E> void sendMany(E entity, String path, Object... replacements);

  /**
   * Get a message from the source
   *
   * @param path Message path
   * @param replacements Placeholders that will be applied
   * @return The message
   */
  @NotNull
  T get(String path, Object... replacements);

  /**
   * Gets a message list from the source
   *
   * @param path Message path
   * @param replacements Placeholders that will be applied
   * @return The message list
   */
  @NotNull
  List<T> getMany(String path, Object... replacements);

  /**
   * Gets the messenger config
   *
   * @return Messenger config
   */
  @NotNull
  MessengerConfig<T> getConfig();

}

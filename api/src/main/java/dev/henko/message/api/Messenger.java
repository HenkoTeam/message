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
  <E> void sendReplacing(E entity, String path, Object... replacements);

  <E> void send(E entity, String path, Object... entities);

  /**
   * Sends a message list to entity
   *
   * @param entity Entity that will receive the message
   * @param path Message list source path
   * @param replacements Placeholders that will be applied
   * @param <E> Entity type
   */
  <E> void sendManyReplacing(E entity, String path, Object... replacements);

  <E> void sendMany(E entity, String path, Object... entities);

  /**
   * Get a message from the source
   *
   * @param path Message path
   * @param replacements Placeholders that will be applied
   * @return The message
   */
  @NotNull
  T getReplacing(String path, Object... replacements);

  @NotNull
  T get(String path, Object... entities);

  /**
   * Gets a message list from the source
   *
   * @param path Message path
   * @param replacements Placeholders that will be applied
   * @return The message list
   */
  @NotNull
  List<T> getManyReplacing(String path, Object... replacements);

  @NotNull
  List<T> getMany(String path, Object... entities);

  /**
   * Gets the messenger config
   *
   * @return Messenger config
   */
  @NotNull
  MessengerConfig<T> getConfig();

}

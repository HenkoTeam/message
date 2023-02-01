package dev.henko.message.api;

import dev.henko.message.api.config.ConfigurationHandle;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface MessageHandler {

  static MessageHandler create(ConfigurationHandle configurationHandle) {
    return new MessageHandlerImpl(configurationHandle);
  }

  /**
   * Sends a message to entity using placeholders
   *
   * @param entity       Entity that will receive the message
   * @param path         Message source path
   * @param replacements Placeholders that will be applied
   * @param <E>          Entity type
   */
  <E> void sendReplacing(E entity, String path, Object... replacements);

  /**
   * Sends a message to entity using entities placeholders
   *
   * @param entity   Entity that will receive the message
   * @param path     Message source path
   * @param entities Entities whose placeholders will be used
   * @param <E>      Entity type
   */
  <E> void send(E entity, String path, Object... entities);

  /**
   * Sends a message list to entity using placeholders
   *
   * @param entity       Entity that will receive the message
   * @param path         Message list source path
   * @param replacements Placeholders that will be applied
   * @param <E>          Entity type
   */
  <E> void sendManyReplacing(E entity, String path, Object... replacements);

  /**
   * Sends a message list to entity using entities placeholders
   *
   * @param entity   Entity that will receive the message
   * @param path     Message source path
   * @param entities Entities whose placeholders will be used
   * @param <E>      Entity type
   */
  <E> void sendMany(E entity, String path, Object... entities);

  /**
   * Get a message from the source using placeholders
   *
   * @param path         Message path
   * @param replacements Placeholders that will be applied
   * @return The message
   */
  @NotNull String getReplacing(String path, Object... replacements);

  /**
   * Get a message from the source using entities placeholders
   *
   * @param path     Message path
   * @param entities Entities whose placeholders will be used
   * @return The message
   */
  @NotNull String get(String path, Object... entities);

  /**
   * Gets a message list from the source
   *
   * @param path         Message path
   * @param replacements Placeholders that will be applied
   * @return The message list
   */
  @NotNull List<String> getManyReplacing(String path, Object... replacements);

  /**
   * Get a message list from the source using entities placeholders
   *
   * @param path     Message path
   * @param entities Entities whose placeholders will be used
   * @return The message
   */
  @NotNull List<String> getMany(String path, Object... entities);

}
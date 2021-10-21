package dev.henko.message.api.entity;

/**
 * Represents a entity method to send a message
 *
 * @param <E> The entity type
 * @param <T> The message type
 */
@FunctionalInterface
public interface EntitySender<E, T> {

  /**
   * Sends a message to entity.
   *
   * @param entity Entity that will receive the message
   * @param message Message sent
   */
  void send(E entity, T message);
}

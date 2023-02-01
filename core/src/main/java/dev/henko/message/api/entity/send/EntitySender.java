package dev.henko.message.api.entity.send;

/**
 * Represents an entity method to send a message
 *
 * @param <E> The entity type
 */
@FunctionalInterface
public interface EntitySender<E> {

  /**
   * Sends a message to entity.
   *
   * @param entity  Entity that will receive the message
   * @param message Message sent
   */
  void send(E entity, String message);

}
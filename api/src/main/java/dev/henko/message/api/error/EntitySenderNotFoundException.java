package dev.henko.message.api.error;

/**
 * An exception that can be thrown when sends
 * a message to entity.
 */
public class EntitySenderNotFoundException
  extends RuntimeException {

  public EntitySenderNotFoundException(Class<?> type) {
    super("An error occurred while getting the sender for type " + type.getTypeName());
  }

}

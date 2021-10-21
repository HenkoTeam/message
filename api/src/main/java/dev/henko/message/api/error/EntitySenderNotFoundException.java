package dev.henko.message.api.error;

public class EntitySenderNotFoundException
  extends RuntimeException {

  public EntitySenderNotFoundException(Class<?> type) {
    super("An error occurred while getting the sender for type " + type.getTypeName());
  }

}

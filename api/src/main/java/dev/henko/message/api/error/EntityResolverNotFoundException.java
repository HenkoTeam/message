package dev.henko.message.api.error;

public class EntityResolverNotFoundException
  extends RuntimeException {

  public EntityResolverNotFoundException(Class<?> type) {
    super("An error occurred while getting the entity resolver for type " + type.getTypeName());
  }
}

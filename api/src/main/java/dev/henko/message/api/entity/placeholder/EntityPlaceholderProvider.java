package dev.henko.message.api.entity.placeholder;

/**
 * Resolve entity placeholders
 *
 * @param <E> The entity type
 */
public interface EntityPlaceholderProvider<E> {

  /**
   * Get the translate of any entity placeholder
   *
   * @param entity Entity from which the value will be obtained
   * @param placeholder Placeholder to get
   * @return The placeholder value
   */
  Object replace(E entity, String placeholder);
}

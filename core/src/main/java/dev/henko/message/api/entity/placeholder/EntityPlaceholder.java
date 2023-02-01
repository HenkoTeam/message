package dev.henko.message.api.entity.placeholder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Contains the entity placeholder properties
 *
 * @param <E>
 */
public final class EntityPlaceholder<E> {

  // Common regex for entity placeholders
  private static final String PLACEHOLDER_REGEX = "%<prefix>(.+?)%";

  private final String prefix;
  private final Pattern pattern;
  private final EntityPlaceholderProvider<E> provider;

  public EntityPlaceholder(
      String prefix,
      EntityPlaceholderProvider<E> provider
  ) {
    this.prefix = prefix;
    this.pattern = Pattern.compile(
        PLACEHOLDER_REGEX.replace("<prefix>", prefix)
    );
    this.provider = provider;
  }

  /**
   * @see EntityPlaceholderProvider#replace(Object, String);
   */
  @SuppressWarnings("unchecked")
  public @Nullable Object replace(Object entity, String placeholder) {
    return provider.replace((E) entity, placeholder);
  }

  /**
   * Get the placeholders prefix
   *
   * @return The prefix
   */
  public @NotNull String getPrefix() {
    return prefix;
  }

  /**
   * Get the pattern for the placeholders
   *
   * @return The pattern
   */
  public @NotNull Pattern getPattern() {
    return pattern;
  }

  /**
   * Get the placeholder provider
   *
   * @return The placeholder provider
   */
  public @NotNull EntityPlaceholderProvider<E> getProvider() {
    return provider;
  }
}
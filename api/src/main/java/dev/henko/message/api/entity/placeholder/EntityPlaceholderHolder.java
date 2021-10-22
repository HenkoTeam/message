package dev.henko.message.api.entity.placeholder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public final class EntityPlaceholderHolder<E> {

  private static final String PLACEHOLDER_REGEX = "%<prefix>(.+?)%";


  private final String prefix;
  private final Pattern pattern;
  private final EntityPlaceholderProvider<E> provider;


  public EntityPlaceholderHolder(
    String prefix,
    EntityPlaceholderProvider<E> provider
  ) {
    this.prefix = prefix;
    this.pattern = Pattern.compile(
      PLACEHOLDER_REGEX.replace("<prefix>", prefix)
    );
    this.provider = provider;
  }

  @SuppressWarnings("unchecked")
  public @Nullable Object replace(Object o, String placeholder) {
    return getProvider().replace((E) o, placeholder);
  }

  public @NotNull String getPrefix() {
    return prefix;
  }

  public @NotNull Pattern getPattern() {
    return pattern;
  }

  public @NotNull EntityPlaceholderProvider<E> getProvider() {
    return provider;
  }
}

package dev.henko.message.api.entity.placeholder;

/**
 * Utility to to remove the common placeholder
 * regex from string
 */
public final class StringPlaceholderObtainer {

  public static String from(
    String text,
    String prefix
  ) {
    return text
      .replace("%", "")
      .replace(prefix + "_", "");
  }

}

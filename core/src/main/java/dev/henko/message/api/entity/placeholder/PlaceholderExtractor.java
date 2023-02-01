package dev.henko.message.api.entity.placeholder;

/**
 * Utility to remove the common placeholder
 * regex from string
 */
public final class PlaceholderExtractor {

  public static String from(
      String text,
      String prefix
  ) {
    return text
        .replace("%", "")
        .replace(prefix + "_", "");
  }

}
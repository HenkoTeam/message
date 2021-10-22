package dev.henko.message.bungee.source;

import dev.henko.message.api.source.Source;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

public class YamlSource
  implements Source {

  private final String fileName;

  private final Plugin plugin;

  private final File folder;

  private File file;

  private Configuration bungeeConfig;

  public YamlSource(Plugin plugin, String fileName, File folder) {
    this.folder = folder;
    this.plugin = plugin;
    this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
    createFile();

  }

  public YamlSource(Plugin plugin, String fileName) {
    this(plugin, fileName, plugin.getDataFolder());
  }

  private void createFile() {
    if (!folder.exists()) folder.mkdir();
    file = new File(folder, fileName);
    try {
      if (!file.exists())
        Files.copy(plugin.getResourceAsStream(fileName), file.toPath());
      bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void save() {
    try {
      ConfigurationProvider.getProvider(YamlConfiguration.class).save(bungeeConfig, file);
    } catch (IOException e) {
      this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
    }
  }

  public void reload() {
    try {
      bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    } catch (IOException e) {
      this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
    }
  }

  @Override
  public @Nullable String getString(String path) {
    return bungeeConfig.getString(path);
  }

  @Override
  public @Nullable List<String> getStringList(String path) {
    return bungeeConfig.getStringList(path);
  }
  public Configuration getConfig() {
    return bungeeConfig;
  }
}

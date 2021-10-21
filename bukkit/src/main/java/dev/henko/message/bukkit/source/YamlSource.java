package dev.henko.message.bukkit.source;

import dev.henko.message.api.source.Source;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class YamlSource
  extends YamlConfiguration
  implements Source {

  private final String fileName;
  private final Plugin plugin;
  private final File folder;
  private File file;

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
    try {
      file = new File(this.folder, this.fileName);
      if (file.exists()) {
        load(file);
        save(file);
        return;
      }
      if (this.plugin.getResource(this.fileName) != null) {
        this.plugin.saveResource(this.fileName, false);
      } else {
        save(file);
      }
      load(file);
    } catch (InvalidConfigurationException | IOException e) {
      plugin.getLogger().log(Level.SEVERE, "Creation of Configuration '" + this.fileName + "' failed.");
    }
  }

  public void save() {
    try {
      save(file);
    } catch (IOException e) {
      plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.");
    }
  }

  public void reload() {
    try {
      load(file);
    } catch (IOException | InvalidConfigurationException e) {
      plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.");
    }
  }

}

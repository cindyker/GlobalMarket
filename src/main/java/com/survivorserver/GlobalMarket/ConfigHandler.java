package com.survivorserver.GlobalMarket;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.survivorserver.GlobalMarket.tasks.SaveTask;

public class ConfigHandler {
	
	Market market;
	private FileConfiguration listingsConfig;
	private File listingsFile;
	private FileConfiguration mailConfig;
	private File mailFile;
	private FileConfiguration historyConfig;
	private File historyFile;
	private FileConfiguration localeConfig;
	private File localeFile;
	private FileConfiguration queueConfig;
	private File queueFile;
	
	public ConfigHandler(Market market) {
		this.market = market;
		// Listings
		listingsFile = new File(market.getDataFolder(), "listings.yml");
		listingsConfig = YamlConfiguration.loadConfiguration(listingsFile);
		// Mail
		mailFile = new File(market.getDataFolder(), "mail.yml");
		mailConfig = YamlConfiguration.loadConfiguration(mailFile);
		// History
		historyFile = new File(market.getDataFolder(), "history.yml");
		historyConfig = YamlConfiguration.loadConfiguration(historyFile);
		// Queue
		queueFile = new File(market.getDataFolder(), "queue.yml");
		queueConfig = YamlConfiguration.loadConfiguration(queueFile);
		
		market.getServer().getScheduler().scheduleSyncRepeatingTask(market, new Runnable() {
			public void run() {
				save(true);
			}
		}, 0, 1200);
	}
	
	public void save(boolean async) {
		String listings = listingsConfig.saveToString();
		String mail = mailConfig.saveToString();
		String history = historyConfig.saveToString();
		String queue = queueConfig.saveToString();
		SaveTask save = new SaveTask(this, listings, mail, history, queue);
		if (async) {
			save.runTaskAsynchronously(market);
		} else {
			save.run();
		}
	}
	
	/*public void reloadListingsYML() {
		if (listingsFile == null) {
			listingsFile = new File(market.getDataFolder(), "listings.yml");
		}
		listingsConfig = YamlConfiguration.loadConfiguration(listingsFile);
	}*/
	
	public FileConfiguration getListingsYML() {
		/*if (listingsConfig == null) {
			reloadListingsYML();
		}*/
		return listingsConfig;
	}
	
	/*public void saveListingsYML() {
		if (listingsConfig == null) {
			return;
		}
		try {
			getListingsYML().save(listingsFile);
		} catch(Exception e) {
			market.getLogger().log(Level.SEVERE, "Coult not save listings: ", e);
		}
	}*/
	
	/*public void reloadMailYML() {
		if (mailFile == null) {
			mailFile = new File(market.getDataFolder(), "mail.yml");
		}
		mailConfig = YamlConfiguration.loadConfiguration(mailFile);
	}*/
	
	public FileConfiguration getMailYML() {
		/*if (mailConfig == null) {
			reloadMailYML();
		}*/
		return mailConfig;
	}
	
	/*public void saveMailYML() {
		if (mailConfig == null) {
			return;
		}
		try {
			getMailYML().save(mailFile);
		} catch(Exception e) {
			market.getLogger().log(Level.SEVERE, "Coult not save mail: ", e);
		}
	}*/
	
	/*public void reloadHistoryYML() {
		if (historyFile == null) {
			historyFile = new File(market.getDataFolder(), "history.yml");
		}
		historyConfig = YamlConfiguration.loadConfiguration(historyFile);
	}*/
	
	public FileConfiguration getHistoryYML() {
		/*if (historyConfig == null) {
			reloadHistoryYML();
		}*/
		return historyConfig;
	}
	
	/*public void saveHistoryYML() {
		if (historyConfig == null) {
			return;
		}
		try {
			getHistoryYML().save(historyFile);
		} catch(Exception e) {
			market.getLogger().log(Level.SEVERE, "Coult not save history: ", e);
		}
	}*/
	
	public void reloadLocaleYML() {
		if (localeFile == null) {
			localeFile = new File(market.getDataFolder(), "locale.yml");
			if (!localeFile.exists()) {
				market.saveResource("locale.yml", false);
			}
		}
		
		localeConfig = YamlConfiguration.loadConfiguration(localeFile);
		if (!localeConfig.getString("version").equalsIgnoreCase(market.getDescription().getVersion())) {
			File oldLocale = new File(market.getDataFolder().getName() + "/locale_old.yml");
			if (oldLocale.exists()) {
				oldLocale.delete();
			}
			localeFile.renameTo(new File(market.getDataFolder(), "locale_old.yml"));
			market.saveResource("locale.yml", false);
			localeConfig = YamlConfiguration.loadConfiguration(localeFile);
			market.log.warning("Locale version didn't match, loaded new file and moved the old one to \"local_old.yml\"");
		}
	}
	
	public FileConfiguration getLocaleYML() {
		if (localeConfig == null) {
			reloadLocaleYML();
		}
		return localeConfig;
	}
	
	public void saveLocaleYML() {
		if (localeConfig == null) {
			return;
		}
		try {
			getLocaleYML().save(localeFile);
		} catch(Exception e) {
			market.getLogger().log(Level.SEVERE, "Coult not save locale: ", e);
		}
	}
	
	/*public void reloadQueueYML() {
		if (queueFile == null) {
			queueFile = new File(market.getDataFolder(), "queue.yml");
		}
		queueConfig = YamlConfiguration.loadConfiguration(queueFile);
	}*/
	
	public FileConfiguration getQueueYML() {
		/*if (queueConfig == null) {
			reloadQueueYML();
		}*/
		return queueConfig;
	}
	
	/*public void saveQueueYML() {
		if (queueConfig == null) {
			return;
		}
		try {
			getQueueYML().save(queueFile);
		} catch(Exception e) {
			market.getLogger().log(Level.SEVERE, "Coult not save queue: ", e);
		}
	}*/
	
	public File getListingsFile() {
		return listingsFile;
	}
	
	public File getMailFile() {
		return mailFile;
	}
	
	public File getHistoryFile() {
		return historyFile;
	}
	
	public File getQueueFile() {
		return queueFile;
	}
}

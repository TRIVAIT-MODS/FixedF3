package org.trivait.fixedf3;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixedF3 implements ClientModInitializer {
	public static Config CONFIG;
	public static final String MOD_ID = "fixedf3";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		AutoConfig.register(Config.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(Config.class).getConfig();
	}
}
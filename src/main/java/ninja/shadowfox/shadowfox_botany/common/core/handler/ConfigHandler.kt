package ninja.shadowfox.shadowfox_botany.common.core.handler

import cpw.mods.fml.client.event.ConfigChangedEvent
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.common.config.Configuration
import java.io.File
import kotlin.properties.Delegates


class ConfigHandler {

    class ChangeListener {constructor()

        @SubscribeEvent
        fun onConfigChanged(eventArgs: ConfigChangedEvent.OnConfigChangedEvent) {
            if (eventArgs.modID == "shadowfox_botany") {
                load()
            }
        }
    }

    companion object {
        var config: Configuration by Delegates.notNull()

        var realLightning = false
        var uberCreepers = false

        fun loadConfig(configFile: File) {
            config = Configuration(configFile)
            config.load()
            load()
            FMLCommonHandler.instance().bus().register(ChangeListener())
        }

        fun load() {
            var desc = "Lightning rod creates real lightning."
            realLightning = loadPropBool("realLightning.enabled", desc, realLightning)

            desc = "Uber Creepers can be spawned by OP or Creative players"
            uberCreepers = loadPropBool("uberCreepers.enabled", desc, uberCreepers)

            if (config.hasChanged()) {
                config.save()
            }

        }

        fun loadPostInit() {
            if (config.hasChanged()) {
                config.save()
            }
        }

        fun loadPropInt(propName: String, desc: String, default_: Int): Int {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.getInt(default_)
        }

        fun loadPropDouble(propName: String, desc: String, default_: Double): Double {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.getDouble(default_)
        }

        fun loadPropBool(propName: String, desc: String, default_: Boolean): Boolean {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.getBoolean(default_)
        }
    }
}
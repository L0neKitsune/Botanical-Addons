package ninja.shadowfox.shadowfox_stuff.common.core.handler

import net.minecraft.potion.Potion
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import ninja.shadowfox.shadowfox_stuff.ShadowFox
import java.io.File
import kotlin.properties.Delegates


class ConfigHandler {

    class ChangeListener {
        constructor()

        @SubscribeEvent
        fun onConfigChanged(eventArgs: ConfigChangedEvent.OnConfigChangedEvent) {
            if (eventArgs.modID == ShadowFox.MODID) {
                load()
            }
        }
    }

    companion object {
        var config: Configuration by Delegates.notNull()

        private var potionArrayLimit = 0
        private var verifiedPotionArray = false

        fun loadConfig(configFile: File) {
            config = Configuration(configFile)
            config.load()
            load()
            FMLCommonHandler.instance().bus().register(ChangeListener())
        }

        fun load() {
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

        fun loadPropPotionId(propName: String, default_: Int): Int {
            if (!verifiedPotionArray) {
                verifyPotionArray()
            }

            val prop = config.get("potions", propName, default_)
            var `val` = prop.getInt(default_)
            if (`val` > potionArrayLimit) {
                `val` = default_
                prop.set(default_)
            }

            return `val`
        }

        private fun verifyPotionArray() {
            if (Loader.isModLoaded("DragonAPI")) {
                potionArrayLimit = Potion.potionTypes.size
            } else {
                potionArrayLimit = 127
            }

            verifiedPotionArray = true
        }

        fun loadPropDouble(propName: String, desc: String, default_: Double): Double {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.getDouble(default_)
        }

        fun loadPropIntArray(propName: String, desc: String, default_: IntArray): IntArray {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.intList
        }

        fun loadPropBool(propName: String, desc: String, default_: Boolean): Boolean {
            val prop = config.get("general", propName, default_)
            prop.comment = desc
            return prop.getBoolean(default_)
        }
    }
}

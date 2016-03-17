package ninja.shadowfox.shadowfox_botany.common.core.handler


import net.minecraft.potion.Potion
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.File
import kotlin.properties.Delegates


class ConfigHandler {

    class ChangeListener {
        constructor()

        @SubscribeEvent
        fun onConfigChanged(eventArgs: ConfigChangedEvent.OnConfigChangedEvent) {
            if (eventArgs.modID == "shadowfox_botany") {
                load()
            }
        }
    }

    companion object {
        var config: Configuration by Delegates.notNull()

        var realLightning: Boolean by Delegates.notNull()
        var uberCreepers: Boolean by Delegates.notNull()
        var passiveLightning: Boolean by Delegates.notNull()
        var blackLotusDropRate: Double by Delegates.notNull()
        var addTincturemAspect: Boolean by Delegates.notNull()
        var addAspectsToBotania: Boolean by Delegates.notNull()
        var addThaumcraftTreeSuffusion: Boolean by Delegates.notNull()
        var potionIDManaVoid: Int by Delegates.notNull()
        var schemaArray: IntArray by Delegates.notNull()
        var grantWireUnlimitedPower: Boolean by Delegates.notNull()


        private var potionArrayLimit = 0
        private var verifiedPotionArray = false

        fun loadConfig(configFile: File) {
            config = Configuration(configFile)
            config.load()
            load()
            MinecraftForge.EVENT_BUS.register(ChangeListener())
        }

        fun load() {
            var desc = "Lightning rod creates real lightning."
            realLightning = loadPropBool("realLightning.enabled", desc, false)

            desc = "Uber Creepers can be spawned by OP or Creative players"
            uberCreepers = loadPropBool("uberCreepers.enabled", desc, false)

            desc = "Lightning rod can hit passive mobs"
            passiveLightning = loadPropBool("passiveLightning.enabled", desc, true)

            desc = "Add a Color aspect to thaumcraft"
            addTincturemAspect = loadPropBool("tincturem.enabled", desc, true)

            desc = "Add aspects to Botania"
            addAspectsToBotania = loadPropBool("botaniaAspects.enabled", desc, true)

            desc = "[TC] [GoG] Add a Dendric Suffusion recipe for Greatwood and Silverwood trees"
            addThaumcraftTreeSuffusion = loadPropBool("TCTrees.enabled", desc, true)

            desc = "Allow WireSegal far more power than any one person should have"
            grantWireUnlimitedPower = loadPropBool("wire.overpowered", desc, true)

            desc = "Rate of black loti dropping from Manaseal Creepers"
            blackLotusDropRate = loadPropDouble("voidCreepers.dropRate", desc, 0.05)


            desc = "Which schemas are allowed to be generated"
            schemaArray = loadPropIntArray("schemas.enabled", desc, IntArray(17, { i: Int -> -1 + i }))

            potionIDManaVoid = loadPropPotionId("manaVoid", 110)

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

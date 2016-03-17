package ninja.shadowfox.shadowfox_botany.common.item.creator

//import com.google.common.collect.HashMultimap
//import com.google.common.collect.Multimap
//import net.minecraft.block.Block
//import net.minecraft.client.gui.GuiScreen
//import net.minecraft.entity.Entity
//import net.minecraft.entity.EntityLivingBase
//import net.minecraft.entity.SharedMonsterAttributes
//import net.minecraft.entity.ai.attributes.AttributeModifier
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.item.EnumRarity
//import net.minecraft.item.Item
//import net.minecraft.item.ItemStack
//import net.minecraft.item.ItemSword
//import net.minecraft.util.BlockPos
//import net.minecraft.util.EnumChatFormatting
//import net.minecraft.util.StatCollector
//import net.minecraft.world.World
//import net.minecraftforge.client.event.TextureStitchEvent
//import net.minecraftforge.common.MinecraftForge
//import net.minecraftforge.fml.common.registry.GameRegistry
//import net.minecraftforge.fml.relauncher.FMLLaunchHandler
//import net.minecraftforge.fml.relauncher.SideOnly
//import net.minecraftforge.oredict.OreDictionary
//import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
//import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
//import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
//import ninja.shadowfox.shadowfox_botany.common.utils.helper.InterpolatedIconHelper
//import vazkii.botania.api.BotaniaAPI
//import vazkii.botania.api.mana.IManaUsingItem
//import vazkii.botania.api.mana.ManaItemHandler
//import vazkii.botania.common.item.equipment.tool.ToolCommons

/**
 * @author WireSegal
 * Created at 10:40 AM on 2/8/16.
 */
@Deprecated("1.7.10")
class ItemTrisDagger
//(val name: String = "reactionDagger", val toolMaterial: ToolMaterial = ShadowFoxAPI.RUNEAXE) : ItemSword(toolMaterial), IManaUsingItem {
//
//    companion object {
//        val minBlockLength = 0
//        val maxBlockLength = 10
//    }
//
//    init {
//        setMaxStackSize(1)
//        maxDamage = toolMaterial.maxUses
//        creativeTab = ShadowFoxCreativeTab
//        unlocalizedName = name
//        DaggerEventHandler.register()
//        if (FMLLaunchHandler.side().isClient)
//            MinecraftForge.EVENT_BUS.register(this)
//    }
//
//    override fun setUnlocalizedName(par1Str: String): Item {
//        GameRegistry.registerItem(this, par1Str)
//        return super.setUnlocalizedName(par1Str)
//    }
//
//    override fun getItemStackDisplayName(stack: ItemStack): String {
//        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
//    }
//
//    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
//        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.".toRegex(), "item.shadowfox_botany:")
//    }
//
//    override fun getRarity(stack: ItemStack): EnumRarity = BotaniaAPI.rarityRelic
//    fun getManaPerDamage(): Int = 60
//
//    fun ItemStack.damageStack(damage: Int, entity: EntityLivingBase?) {
//        ToolCommons.damageItem(this, damage, entity, getManaPerDamage())
//    }
//
//    override fun onUpdate(stack: ItemStack, world: World, player: Entity, par4: Int, par5: Boolean) {
//        if (!world.isRemote && player is EntityPlayer && stack.itemDamage > 0 && ManaItemHandler.requestManaExactForTool(stack, player, getManaPerDamage() * 2, true))
//            stack.itemDamage = stack.itemDamage - 1
//    }
//
//    override fun onBlockDestroyed(stack: ItemStack, worldIn: World, blockIn: Block, pos: BlockPos, playerIn: EntityLivingBase): Boolean {
//        if (blockIn.getBlockHardness(worldIn, pos).toDouble() != 0.0) {
//            stack.damageStack(2, playerIn)
//        }
//
//        return true
//    }
//
//    override fun usesMana(stack: ItemStack): Boolean = true
//
//    override fun getMaxItemUseDuration(stack: ItemStack?): Int {
//        return 72000
//    }
//
//    override fun onItemRightClick(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
//        par3EntityPlayer!!.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack))
//        return par1ItemStack
//    }
//
////    override fun getItemAttributeModifiers(): Multimap<Any, Any> {
////        val multimap = HashMultimap.create<Any, Any>()
////        multimap.put(SharedMonsterAttributes.attackDamage.attributeUnlocalizedName, AttributeModifier(Item.field_111210_e, "Weapon modifier", this.toolMaterial.damageVsEntity.toDouble(), 0))
////        return multimap
////    }
//
//    override fun isFull3D(): Boolean = true
//
//    override fun addInformation(stack: ItemStack?, playerIn: EntityPlayer?, tooltip: MutableList<String>?, advanced: Boolean) {
//        super.addInformation(stack, playerIn, tooltip, advanced)
//        val greyitalics = "${EnumChatFormatting.GRAY}${EnumChatFormatting.ITALIC}"
//        val grey = EnumChatFormatting.GRAY
//        if (GuiScreen.isShiftKeyDown()) {
//            addStringToTooltip("$greyitalics${StatCollector.translateToLocal("misc.shadowfox_botany.tline1")}", tooltip)
//            addStringToTooltip("$greyitalics${StatCollector.translateToLocal("misc.shadowfox_botany.tline2")}", tooltip)
//            addStringToTooltip("$greyitalics${StatCollector.translateToLocal("misc.shadowfox_botany.tline3")}", tooltip)
//            addStringToTooltip("${grey}My inward eye sees the depths of my soul!", tooltip)
//            addStringToTooltip("${grey}I accept both sides, and reject my downfall!", tooltip)
//        } else addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), tooltip)
//    }
//
//    fun addStringToTooltip(s: String, tooltip: MutableList<String>?) {
//        tooltip?.add(s.replace("&", "\u00a7"))
//    }
//
//    override fun getItemEnchantability(): Int = this.toolMaterial.enchantability
//    override fun getIsRepairable(stack: ItemStack?, materialstack: ItemStack?): Boolean {
//        val mat = this.toolMaterial.repairItemStack
//        if (mat != null && OreDictionary.itemMatches(mat, materialstack, false)) return true
//        return super.getIsRepairable(stack, materialstack)
//    }
//}

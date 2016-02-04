package ninja.shadowfox.shadowfox_botany.common.item

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import com.google.common.collect.Sets
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.ChatComponentText
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import ninja.shadowfox.shadowfox_botany.api.ShadowFoxAPI
import ninja.shadowfox.shadowfox_botany.common.brew.ShadowFoxPotions
import vazkii.botania.api.mana.ManaItemHandler
import vazkii.botania.common.Botania

/**
 * seeeeeecrets
 */
class ItemWireAxe(val toolMaterial: ToolMaterial = ShadowFoxAPI.RUNEAXE) : ItemMod("axeRevelation") {

    init {
        setMaxStackSize(1)
        setMaxDamage(toolMaterial.maxUses)
    }

    override fun getToolClasses(stack: ItemStack?): MutableSet<String>? {
        return Sets.newHashSet("axe", "sword")
    }

    override fun getItemUseAction(stack: ItemStack): EnumAction {
        return EnumAction.bow
    }

    override fun getMaxItemUseDuration(stack: ItemStack?): Int {
        return 110
    }

    override fun onItemRightClick(par1ItemStack: ItemStack, par2World: World?, par3EntityPlayer: EntityPlayer?): ItemStack {
        par3EntityPlayer!!.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack))
        return par1ItemStack
    }

    override fun onPlayerStoppedUsing(stack: ItemStack, world: World, player: EntityPlayer, inUseTicks: Int) {
        if (inUseTicks >= 100) {
            val entities = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(
                    player.posX + 5, player.posY + 5, player.posZ + 5,
                    player.posX - 5, player.posY - 5, player.posZ - 5))
            var count = 1
            for (entity in entities) {
                if (entity is EntityPlayer && ManaItemHandler.requestManaExact(stack, entity, 1, false)) {
                    count++
                    entity.addPotionEffect(PotionEffect(ShadowFoxPotions.manaVoid.id, 10, 0, true))
                    entity.addChatMessage(ChatComponentText(StatCollector.translateToLocal("misc.shadowfox_botany.wayOfUndoing").replace('&', '\u00a7')))
                }
            }
            if (count > 0) {
                world.playSoundAtEntity(player, "botania:enchanterEnchant", 1f, 1f)
                for (var11 in 0..20) {
                    val var13 = Math.random().toFloat()
                    val var16 = Math.random().toFloat()
                    val var19 = Math.random().toFloat()
                    Botania.proxy.wispFX(world, player.posX, player.posY, player.posZ, var13, var16, var19, Math.random().toFloat() * 0.15f + 0.15f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f, (Math.random() - 0.5).toFloat() * 0.25f)
                }
                player.addPotionEffect(PotionEffect(ShadowFoxPotions.manaVoid.id, 2*count, 0, true))
            }
        }
    }

    override fun func_150893_a(stack: ItemStack?, block: Block): Float =
       if ( block.material == Material.wood ||
            block.material == Material.plants ||
            block.material == Material.vine ||
            block.material == Material.coral ||
            block.material == Material.leaves ||
            block.material == Material.gourd)
           this.getEfficiencyOnProperMaterial()
        else if (block == Blocks.web)
           15f
        else
           1f

    override fun getItemAttributeModifiers(): Multimap<Any, Any> {
        val multimap = HashMultimap.create<Any, Any>()
        multimap.put(SharedMonsterAttributes.attackDamage.attributeUnlocalizedName, AttributeModifier(Item.field_111210_e, "Weapon modifier", this.toolMaterial.damageVsEntity.toDouble(), 0))
        return multimap
    }

    override fun hitEntity(stack: ItemStack, attacked: EntityLivingBase, attacker: EntityLivingBase?): Boolean {
        stack.damageItem(2, attacker)
        return true
    }

    override fun onBlockDestroyed(stack: ItemStack, world: World?, block: Block, x: Int, y: Int, z: Int, player: EntityLivingBase?): Boolean {
        if (block.getBlockHardness(world, x, y, z).toDouble() != 0.0) {
            stack.damageItem(1, player)
        }

        return true
    }

    override fun getItemEnchantability(): Int = this.toolMaterial.enchantability
    fun getEfficiencyOnProperMaterial(): Float = this.toolMaterial.efficiencyOnProperMaterial
    override fun getIsRepairable(stack: ItemStack?, materialstack: ItemStack?): Boolean {
        val mat = this.toolMaterial.repairItemStack
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, materialstack, false)) return true
        return super.getIsRepairable(stack, materialstack)
    }


    override fun getHarvestLevel(stack: ItemStack?, toolClass: String?): Int {
        val level = super.getHarvestLevel(stack, toolClass)
        if (level == -1 && toolClass != null && (toolClass == "axe" || toolClass == "sword")) {
            return this.toolMaterial.harvestLevel
        } else {
            return level
        }
    }

    override fun getDigSpeed(stack: ItemStack, block: Block, meta: Int): Float {
        if (ForgeHooks.isToolEffective(stack, block, meta)) {
            return getEfficiencyOnProperMaterial()
        }
        return this.func_150893_a(stack, block)
    }
}

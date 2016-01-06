package ninja.shadowfox.shadowfox_botany.common.item

import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.IIcon
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.StatCollector
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.oredict.RecipeSorter
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import ninja.shadowfox.shadowfox_botany.common.blocks.tile.TileInvisibleManaFlame
import vazkii.botania.api.internal.IManaBurst
import vazkii.botania.api.mana.BurstProperties
import vazkii.botania.api.mana.ICompositableLens
import vazkii.botania.common.Botania
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.crafting.recipe.LensDyeingRecipe
import vazkii.botania.common.item.lens.ItemLens
import java.awt.Color
import kotlin.text.format
import kotlin.text.replace
import kotlin.text.toRegex


class ItemLensFlashInvisible() : ItemMod("lensPhantomLight"), ICompositableLens {

    init {
        setMaxStackSize(1)
        GameRegistry.addRecipe(LensDyeingRecipe())
        RecipeSorter.register("shadowfox_botany:lensDying", LensDyeingRecipe::class.java, RecipeSorter.Category.SHAPELESS, "")
    }

    @SideOnly(Side.CLIENT)
    override fun registerIcons(par1IconRegister: IIconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("botania:lensLight")
    }

    override fun doParticles(p0: IManaBurst?, p1: ItemStack?): Boolean = true

    override fun updateBurst(burst: IManaBurst, stack: ItemStack) {
        val entity = burst as EntityThrowable
        val storedColor = getStoredColor(stack)
        if (storedColor == 16 && entity.worldObj.isRemote) {
            burst.color = this.getLensColor(stack)
        }
        val compositeLens = getCompositeLens(stack)
        if (compositeLens != null && compositeLens.item is ICompositableLens)
            (compositeLens.item as ICompositableLens).updateBurst(burst, compositeLens)
    }

    override fun getLensColor(stack: ItemStack?): Int {
        val storedColor = getStoredColor(stack!!)
        if (storedColor == -1) {
            return 16777215
        } else if (storedColor == 16) {
            return Color.HSBtoRGB((Botania.proxy.worldElapsedTicks * 2L % 360L).toFloat() / 360.0f, 1.0f, 1.0f)
        } else {
            val color = EntitySheep.fleeceColorTable[storedColor]
            return (Color(color[0], color[1], color[2])).rgb
        }
    }

    fun getStoredColor(stack: ItemStack): Int {
        return ItemNBTHelper.getInt(stack, "color", -1)
    }

    fun setLensColor(stack: ItemStack, color: Int): ItemStack {
        ItemNBTHelper.setInt(stack, "color", color)
        return stack
    }

    fun addStringToTooltip(s: String, tooltip: MutableList<Any?>) {
        tooltip.add(s.replace("&".toRegex(), "\u00a7"))
    }

    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, par4: Boolean) {
        if (par1ItemStack == null) return

        var storedColor = getStoredColor(par1ItemStack)
        var compositeLens = getCompositeLens(par1ItemStack)
        if (storedColor != -1)
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.color").format(StatCollector.translateToLocal("botania.color" + storedColor)), par3List)
        if (compositeLens == null)
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), par3List)
    }

    override fun collideBurst(burst: IManaBurst?, pos: MovingObjectPosition?, isManaBlock: Boolean, dead: Boolean, p4: ItemStack?): Boolean {
        val entity: EntityThrowable? = burst as EntityThrowable

        if (pos != null && entity != null) {
            val coords = burst.burstSourceChunkCoordinates

            if ((coords.posX != pos.blockX || coords.posY != pos.blockY || coords.posZ != pos.blockZ) && !burst.isFake && !isManaBlock) {
                val dir = ForgeDirection.getOrientation(pos.sideHit)

                val x = pos.blockX + dir.offsetX
                val y = pos.blockY + dir.offsetY
                val z = pos.blockZ + dir.offsetZ

                val blockAt = entity.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ)
                val blockAt_ = entity.worldObj.getBlock(x, y, z)

                if (blockAt === ModBlocks.manaFlame || blockAt === ShadowFoxBlocks.invisibleFlame)
                    entity.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.air)
                else if (blockAt_.isAir(entity.worldObj, x, y, z) || blockAt_.isReplaceable(entity.worldObj, x, y, z)) {
                    entity.worldObj.setBlock(x, y, z, ShadowFoxBlocks.invisibleFlame, 0, 0)

                    val tile = entity.worldObj.getTileEntity(x, y, z)
                    if (tile is TileInvisibleManaFlame) {
                        tile.flameColor = burst.color
                    }
                }
            }
        }
        val compositeLens = getCompositeLens(p4)
        if (compositeLens != null && compositeLens.item is ICompositableLens)
            return (compositeLens.item as ICompositableLens).collideBurst(burst, pos, isManaBlock, dead, compositeLens)
        return dead
    }

    override fun apply(p0: ItemStack?, p1: BurstProperties?) {
        if (p0 != null && p1 != null) {
            val storedColor = getStoredColor(p0)
            if (storedColor != -1) {
                p1.color = this.getLensColor(p0)
            }
            val compositeLens = getCompositeLens(p0)
            if (compositeLens != null && compositeLens.item is ICompositableLens)
                (compositeLens.item as ICompositableLens).apply(compositeLens, p1)
        }
    }

    override fun getItemStackDisplayName(stack: ItemStack): String {
        var compositeLens = getCompositeLens(stack) ?: return StatCollector.translateToLocal("item.botania:lensLight.name")
        return StatCollector.translateToLocal("item.botania:compositeLens.name").format(getItemShortTermName(stack), getItemShortTermName(compositeLens))
    }

    fun getItemShortTermName(stack: ItemStack): String {
        return StatCollector.translateToLocal(stack.unlocalizedName.replace("item\\.".toRegex(), "item.botania:") + ".short")
    }

    override fun getColorFromItemStack(par1ItemStack: ItemStack, par2: Int): Int {
        return if (par2 != 1) getLensColor(par1ItemStack) else 0xFFFFFF
    }

    override fun requiresMultipleRenderPasses(): Boolean = true
    override fun getIconFromDamageForRenderPass(par1: Int, par2: Int): IIcon {
        return if (par2 == 1) this.itemIcon else ItemLens.iconGlass
    }

    override fun getCompositeLens(stack: ItemStack?): ItemStack? {
        if (stack != null) {
            var cmp = ItemNBTHelper.getCompound(stack, "compositeLens", false)
            var lens = ItemStack.loadItemStackFromNBT(cmp)
            return lens
        }
        return null
    }

    override fun setCompositeLens(sourceLens: ItemStack, compositeLens: ItemStack): ItemStack {
        var cmp = NBTTagCompound()
        compositeLens.writeToNBT(cmp)
        ItemNBTHelper.setCompound(sourceLens, "compositeLens", cmp)
        return sourceLens
    }

    override fun canCombineLenses(sourceLens: ItemStack, compositeLens: ItemStack): Boolean {
        val sourceItem = sourceLens.item as ICompositableLens
        val compositeItem = compositeLens.item as ICompositableLens
        if (sourceItem == compositeItem && sourceLens.itemDamage == compositeLens.itemDamage)
            return false

        if (!sourceItem.isCombinable(sourceLens) || !compositeItem.isCombinable(compositeLens))
            return false

        if (ItemLens.isBlacklisted(sourceLens, compositeLens))
            return false

        return true
    }

    override fun getProps(stack: ItemStack): Int = 4 or 8 // PROP_TOUCH | PROP_INTERACTION

    override fun isCombinable(stack: ItemStack): Boolean = true
}

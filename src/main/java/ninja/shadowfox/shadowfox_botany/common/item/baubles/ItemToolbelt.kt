package ninja.shadowfox.shadowfox_botany.common.item.baubles

import baubles.api.BaubleType
import baubles.common.lib.PlayerHandler
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.model.ModelBiped
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import net.minecraft.util.StatCollector
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.common.MinecraftForge
import ninja.shadowfox.shadowfox_botany.api.item.IToolbeltBlacklisted
import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
import org.lwjgl.opengl.GL11
import vazkii.botania.api.item.IBaubleRender
import vazkii.botania.api.item.IBlockProvider
import vazkii.botania.common.core.helper.ItemNBTHelper
import vazkii.botania.common.item.equipment.bauble.ItemBauble
import java.util.*

class ItemToolbelt() : ItemBauble("toolbelt"), IBaubleRender, IBlockProvider, IToolbeltBlacklisted {
    // ItemToolbelt will not become an IManaItem. That's... a bit excessivly OP, imo. Store those in your Bauble Case, or keep them in your inventory.
    companion object {
        val glowTexture = ResourceLocation("shadowfox_botany:textures/misc/toolbelt.png")
        val beltTexture = ResourceLocation("shadowfox_botany:textures/model/toolbelt.png")

        @SideOnly(Side.CLIENT)
        var model: ModelBiped? = null

        val SEGMENTS = 12

        val TAG_ITEM_PREFIX = "item"
        val TAG_EQUIPPED = "equipped"
        val TAG_ROTATION_BASE = "rotationBase"

        fun isEquipped(stack: ItemStack): Boolean = ItemNBTHelper.getBoolean(stack, TAG_EQUIPPED, false)
        fun setEquipped(stack: ItemStack, equipped: Boolean) = ItemNBTHelper.setBoolean(stack, TAG_EQUIPPED, equipped)
        fun getRotationBase(stack: ItemStack): Float = ItemNBTHelper.getFloat(stack, TAG_ROTATION_BASE, 0F)
        fun setRotationBase(stack: ItemStack, rotation: Float) = ItemNBTHelper.setFloat(stack, TAG_ROTATION_BASE, rotation)

        fun getSegmentLookedAt(stack: ItemStack, player: EntityLivingBase): Int {
            val yaw = getCheckingAngle(player, getRotationBase(stack))

            val angles = 360
            val segAngles = angles / SEGMENTS
            for (seg in 0..SEGMENTS - 1) {
                val calcAngle = seg.toFloat() * segAngles
                if (yaw >= calcAngle && yaw < calcAngle + segAngles)
                    return seg
            }
            return -1
        }

        fun getCheckingAngle(player: EntityLivingBase): Float = getCheckingAngle(player, 0F)

        // Agreed, V, minecraft's rotation is shit. And no roll? Seriously?
        fun getCheckingAngle(player: EntityLivingBase, base: Float): Float {
            var yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw) + 90F
            val angles = 360
            val segAngles = angles / SEGMENTS
            val shift = segAngles / 2

            if (yaw < 0)
                yaw = 180F + (180F + yaw)
            yaw -= 360F - base
            var angle = 360F - yaw + shift

            if (angle < 0)
                angle += 360F

            return angle
        }

        fun isLookingAtSegment(player: EntityLivingBase): Boolean {
            val pitch = player.rotationPitch

            return pitch > -33.75 && pitch < 45
        }

        fun getItemForSlot(stack: ItemStack, slot: Int): ItemStack? {
            if (slot >= SEGMENTS) return null
            else {
                val cmp = getStoredCompound(stack, slot) ?: return null
                return ItemStack.loadItemStackFromNBT(cmp)
            }
        }

        fun getStoredCompound(stack: ItemStack, slot: Int): NBTTagCompound? = ItemNBTHelper.getCompound(stack, TAG_ITEM_PREFIX + slot, true)
        fun setItem(beltStack: ItemStack, stack: ItemStack?, pos: Int) {
            if (stack == null) ItemNBTHelper.setCompound(beltStack, TAG_ITEM_PREFIX + pos, NBTTagCompound())
            else {
                var tag = NBTTagCompound()
                stack.writeToNBT(tag)
                ItemNBTHelper.setCompound(beltStack, TAG_ITEM_PREFIX + pos, tag)
            }
        }

        fun getEquippedBelt(player: EntityPlayer): ItemStack? {
            val inv = PlayerHandler.getPlayerBaubles(player)
            var beltStack: ItemStack? = null
            for (i in 0..inv.sizeInventory) {
                var stack = inv.getStackInSlot(i)
                if (stack != null && stack.item is ItemToolbelt) {
                    beltStack = stack
                }
            }
            return beltStack
        }
    }

    val handler = ToolbeltEventHandler()

    init {
        MinecraftForge.EVENT_BUS.register(handler)
        FMLCommonHandler.instance().bus().register(handler)
        setHasSubtypes(true)
        creativeTab = ShadowFoxCreativeTab
    }

    override fun registerIcons(par1IconRegister: IIconRegister) {
        itemIcon = IconHelper.forItem(par1IconRegister, this)
    }

    override fun getBlockCount(p0: EntityPlayer?, p1: ItemStack, p2: ItemStack, p3: Block, p4: Int): Int {
        var total = 0
        for (segment in 0..SEGMENTS - 1) {
            val slotStack = getItemForSlot(p2, segment)
            if (slotStack != null) {
                val slotItem = slotStack.item
                if (slotItem is IBlockProvider) {
                    val count = slotItem.getBlockCount(p0, p1, slotStack, p3, p4)
                    setItem(p2, slotStack, segment)
                    if (count == -1) return -1
                    total += count
                } else if (slotItem is ItemBlock && Block.getBlockFromItem(slotItem) == p3 && slotStack.itemDamage == p4) {
                    total += slotStack.stackSize
                }
            }
        }
        return total
    }

    override fun provideBlock(p0: EntityPlayer?, p1: ItemStack, p2: ItemStack, p3: Block, p4: Int, p5: Boolean): Boolean {
        for (segment in 0..SEGMENTS - 1) {
            val slotStack = getItemForSlot(p2, segment)
            if (slotStack != null) {
                val slotItem = slotStack.item
                if (slotItem is IBlockProvider) {
                    val provided = slotItem.provideBlock(p0, p1, slotStack, p3, p4, p5)
                    setItem(p2, slotStack, segment)
                    if (provided) return true
                } else if (slotItem is ItemBlock && Block.getBlockFromItem(slotItem) == p3 && slotStack.itemDamage == p4) {
                    if (p5) slotStack.stackSize--

                    if (slotStack.stackSize == 0) setItem(p2, null, segment)
                    else setItem(p2, slotStack, segment)
                    return true
                }
            }
        }
        return false
    }


    override fun allowedInToolbelt(stack: ItemStack): Boolean = false

    override fun addHiddenTooltip(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, par4: Boolean) {
        if (par1ItemStack != null) {
            val map = HashMap<String, Int>()
            for (segment in 0..SEGMENTS - 1) {
                val slotStack = getItemForSlot(par1ItemStack, segment)
                if (slotStack != null) {
                    var base = 0
                    val name = slotStack.displayName
                    val node = map[name]
                    if (node != null) base = node
                    map.put(name, base + slotStack.stackSize)
                }
            }
            if (map.size > 0) par3List.add("${EnumChatFormatting.AQUA}" + StatCollector.translateToLocal("misc.shadowfox_botany.contains"))
            else par3List.add("${EnumChatFormatting.AQUA}" + StatCollector.translateToLocal("misc.shadowfox_botany.containsNothing"))
            val keys = ArrayList<String>(map.keys)
            Collections.sort(keys)
            for (key in keys) {
                par3List.add("${map[key]}x ${EnumChatFormatting.WHITE}$key")
            }
        }
        super.addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4)
    }

    override fun getBaubleType(stack: ItemStack): BaubleType {
        return BaubleType.BELT
    }

    override fun onWornTick(stack: ItemStack, player: EntityLivingBase) {
        if (player is EntityPlayer) {
            val eqLastTick = isEquipped(stack)
            val eq = player.isSneaking && isLookingAtSegment(player)
            if (eqLastTick != eq)
                setEquipped(stack, eq)

            if (!player.isSneaking) {
                val angles = 360
                val segAngles = angles / SEGMENTS
                val shift = segAngles / 2
                setRotationBase(stack, getCheckingAngle(player) - shift)
            }
        }
    }

    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
    }

    @SideOnly(Side.CLIENT)
    fun getGlowResource(): ResourceLocation = glowTexture

    @SideOnly(Side.CLIENT)
    override fun onPlayerBaubleRender(stack: ItemStack, event: RenderPlayerEvent, type: IBaubleRender.RenderType) {
        if (type == IBaubleRender.RenderType.BODY) {
            Minecraft.getMinecraft().renderEngine.bindTexture(beltTexture)
            IBaubleRender.Helper.rotateIfSneaking(event.entityPlayer)
            GL11.glTranslatef(0F, 0.2F, 0F)

            val s = 1.05F / 16F
            GL11.glScalef(s, s, s)
            if (model == null)
                model = ModelBiped()
            else
                model!!.bipedBody.render(1F)
        }
    }
}

package ninja.shadowfox.shadowfox_botany.common.item.baubles

//import baubles.api.BaubleType
//import baubles.common.lib.PlayerHandler
//import cpw.mods.fml.relauncher.Side
//import cpw.mods.fml.relauncher.SideOnly
//import net.minecraft.client.Minecraft
//import net.minecraft.client.gui.GuiScreen
//import net.minecraft.client.renderer.ItemRenderer
//import net.minecraft.client.renderer.Tessellator
//import net.minecraft.client.renderer.texture.IIconRegister
//import net.minecraft.client.renderer.texture.TextureMap
//import net.minecraft.creativetab.CreativeTabs
//import net.minecraft.entity.EntityLivingBase
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.item.Item
//import net.minecraft.item.ItemStack
//import net.minecraft.potion.Potion
//import net.minecraft.potion.PotionEffect
//import net.minecraft.util.DamageSource
//import net.minecraft.util.IIcon
//import net.minecraft.util.MathHelper
//import net.minecraft.util.StatCollector
//import net.minecraftforge.client.event.RenderPlayerEvent
//import ninja.shadowfox.shadowfox_botany.api.item.ColorOverrideHelper
//import ninja.shadowfox.shadowfox_botany.common.core.ShadowFoxCreativeTab
//import ninja.shadowfox.shadowfox_botany.common.item.ShadowFoxItems
//import ninja.shadowfox.shadowfox_botany.common.utils.helper.IconHelper
//import org.lwjgl.opengl.GL11
//import vazkii.botania.api.item.IBaubleRender
//import vazkii.botania.api.mana.IManaUsingItem
//import vazkii.botania.api.mana.ManaItemHandler
//import vazkii.botania.common.Botania
//import vazkii.botania.common.core.helper.ItemNBTHelper
//import vazkii.botania.common.core.helper.Vector3
//import vazkii.botania.common.item.equipment.bauble.ItemBauble
//import java.awt.Color

@Deprecated("1.7.10")
class ItemPriestEmblem()
//: ItemBauble("priestEmblem"), IBaubleRender, IManaUsingItem {
//
//    companion object {
//        val TYPES = 4
//        fun getEmblem(meta: Int, player: EntityPlayer?): ItemStack? {
//            var baubles = PlayerHandler.getPlayerBaubles(player)
//            var stack = baubles.getStackInSlot(0)
//            return if (stack != null && ((stack.item == ShadowFoxItems.emblem && stack.itemDamage == meta) || stack.item == ShadowFoxItems.aesirEmblem) && isActive(stack)) stack else null
//        }
//
//        fun isActive(stack: ItemStack): Boolean {
//            return ItemNBTHelper.getByte(stack, "active", 0) == 1.toByte()
//        }
//
//        fun setActive(stack: ItemStack, state: Boolean) {
//            ItemNBTHelper.setByte(stack, "active", if (state) 1.toByte() else 0.toByte())
//        }
//
//        fun isDangerous(stack: ItemStack): Boolean {
//            return ItemNBTHelper.getByte(stack, "dangerous", 0) == 1.toByte()
//        }
//
//        fun setDangerous(stack: ItemStack, state: Boolean) {
//            ItemNBTHelper.setByte(stack, "dangerous", if (state) 1.toByte() else 0.toByte())
//        }
//    }
//
//    val COST = 2
//    var icons: Array<IIcon?> = arrayOfNulls(TYPES)
//    var baubleIcons: Array<IIcon?> = arrayOfNulls(TYPES)
//
//    init {
//        setHasSubtypes(true)
//        creativeTab = ShadowFoxCreativeTab
//    }
//
//    override fun getUnlocalizedNameInefficiently(par1ItemStack: ItemStack): String {
//        return super.getUnlocalizedNameInefficiently(par1ItemStack).replace("item\\.botania:".toRegex(), "item.shadowfox_botany:")
//    }
//
//    override fun getItemStackDisplayName(stack: ItemStack): String {
//        return super.getItemStackDisplayName(stack).replace("&".toRegex(), "\u00a7")
//    }
//
//    override fun registerIcons(par1IconRegister: IIconRegister) {
//        for (i in 0..(TYPES - 1))
//            icons[i] = IconHelper.forItem(par1IconRegister, this, i)
//        for (i in 0..(TYPES - 1))
//            baubleIcons[i] = IconHelper.forItem(par1IconRegister, this, "Render" + i.toString())
//    }
//
//    override fun getSubItems(item: Item, tab: CreativeTabs?, list: MutableList<Any?>) {
//        for (i in 0..(TYPES - 1))
//            list.add(ItemStack(item, 1, i))
//    }
//
//    override fun addInformation(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, par4: Boolean) {
//        if (par1ItemStack == null || par2EntityPlayer == null) return
//        if (!GuiScreen.isShiftKeyDown() && !par2EntityPlayer.capabilities.isCreativeMode) {
//            if (par2EntityPlayer.health <= 6.0f)
//                this.addStringToTooltip(StatCollector.translateToLocal("misc.shadowfox_botany.healthDanger"), par3List)
//            else
//                this.addStringToTooltip(StatCollector.translateToLocal("misc.shadowfox_botany.healthWarning"), par3List)
//        }
//        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
//    }
//
//    override fun addHiddenTooltip(par1ItemStack: ItemStack?, par2EntityPlayer: EntityPlayer?, par3List: MutableList<Any?>, par4: Boolean) {
//        if (par1ItemStack == null || par2EntityPlayer == null) return
//        if (!par2EntityPlayer.capabilities.isCreativeMode) {
//            if (par2EntityPlayer.health <= 6.0f)
//                addStringToTooltip(StatCollector.translateToLocal("misc.shadowfox_botany.crisisOfFaith"), par3List)
//            else
//                addStringToTooltip(StatCollector.translateToLocal("misc.shadowfox_botany.lackOfFaith"), par3List)
//        }
//        super.addHiddenTooltip(par1ItemStack, par2EntityPlayer, par3List, par4)
//    }
//
//    fun addStringToTooltip(s: String, tooltip: MutableList<Any?>) {
//        tooltip.add(s.replace("&".toRegex(), "\u00a7"))
//    }
//
//    override fun getIconFromDamage(dmg: Int): IIcon? {
//        return icons[Math.min(TYPES - 1, dmg)]
//    }
//
//    fun getBaubleIconFromDamage(dmg: Int): IIcon? {
//        return baubleIcons[Math.min(TYPES - 1, dmg)]
//    }
//
//    override fun getBaubleType(stack: ItemStack): BaubleType {
//        return BaubleType.AMULET
//    }
//
//    override fun getUnlocalizedName(par1ItemStack: ItemStack): String {
//        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.itemDamage
//    }
//
//    override fun onEquippedOrLoadedIntoWorld(stack: ItemStack, player: EntityLivingBase) {
//        setDangerous(stack, false)
//    }
//
//    override fun onUnequipped(stack: ItemStack, player: EntityLivingBase) {
//        if (!(!isDangerous(stack) || (player is EntityPlayer && player.capabilities.isCreativeMode))) {
//            player.attackEntityFrom(FaithDamageSource.instance, 6.0f)
//            player.addPotionEffect(PotionEffect(Potion.blindness.id, 150, 0))
//            player.addPotionEffect(PotionEffect(Potion.confusion.id, 150, 2))
//            player.addPotionEffect(PotionEffect(Potion.moveSlowdown.id, 300, 2))
//            player.addPotionEffect(PotionEffect(Potion.weakness.id, 300, 2))
//        }
//        setDangerous(stack, false)
//    }
//
//    class FaithDamageSource : DamageSource("shadowfox_botany.lackOfFaith") {
//        companion object {
//            val instance = FaithDamageSource()
//        }
//
//        init {
//            this.setDamageBypassesArmor()
//        }
//    }
//
//    fun getHeadOrientation(entity: EntityLivingBase): Vector3 {
//        val f1 = MathHelper.cos(-entity.rotationYaw * 0.017453292F - Math.PI.toFloat())
//        val f2 = MathHelper.sin(-entity.rotationYaw * 0.017453292F - Math.PI.toFloat())
//        val f3 = -MathHelper.cos(-(entity.rotationPitch - 90) * 0.017453292F)
//        val f4 = MathHelper.sin(-(entity.rotationPitch - 90) * 0.017453292F)
//        return Vector3((f2 * f3).toDouble(), f4.toDouble(), (f1 * f3).toDouble())
//    }
//
//    override fun onWornTick(stack: ItemStack, player: EntityLivingBase) {
//        if (player.ticksExisted % 10 == 0) {
//
//            if (player is EntityPlayer) {
//                setActive(stack, ManaItemHandler.requestManaExact(stack, player, COST, true))
//                setDangerous(stack, true)
//            }
//        }
//    }
//
//    override fun usesMana(stack: ItemStack): Boolean {
//        return true
//    }
//
//    @SideOnly(Side.CLIENT)
//    override fun onPlayerBaubleRender(stack: ItemStack, event: RenderPlayerEvent, type: IBaubleRender.RenderType) {
//        if (type == IBaubleRender.RenderType.BODY) {
//            val player = event.entityPlayer
//            if (player.ticksExisted % 10 == 0 && isActive(stack)) {
//                when (stack.itemDamage) {
//                    0 -> {
//                        var playerHead = Vector3.fromEntityCenter(player).add(0.0, 0.75, 0.0)
//                        val playerShift = playerHead.copy().add(getHeadOrientation(player))
//                        val color = Color(ColorOverrideHelper.getColor(player, 0x0079C4))
//                        val innerColor = Color(color.rgb).brighter().brighter()
//                        Botania.proxy.lightningFX(player.worldObj, playerHead, playerShift, 2.0f, color.rgb, innerColor.rgb)
//                    }
//                    1 -> {
//                        for (i in 0..6) {
//                            var xmotion = (Math.random() - 0.5).toFloat() * 0.15f
//                            var zmotion = (Math.random() - 0.5).toFloat() * 0.15f
//                            // 964B00 is brown
//                            val color = Color(ColorOverrideHelper.getColor(player, 0x964B00))
//                            val r = color.red.toFloat() / 255F
//                            val g = color.green.toFloat() / 255F
//                            val b = color.blue.toFloat() / 255F
//                            Botania.proxy.wispFX(player.worldObj, player.posX, player.posY - player.yOffset, player.posZ, r, g, b, Math.random().toFloat() * 0.15f + 0.15f, xmotion, 0.0075f, zmotion)
//                        }
//                    }
//                    2 -> {
//                        for (i in 0..6) {
//                            val vec = getHeadOrientation(player).multiply(0.52)
//                            val color = Color(ColorOverrideHelper.getColor(player, 0x0000FF))
//                            val r = color.red.toFloat() / 255F
//                            val g = color.green.toFloat() / 255F
//                            val b = color.blue.toFloat() / 255F
//                            Botania.proxy.sparkleFX(player.worldObj, player.posX + vec.x, player.posY + vec.y, player.posZ + vec.z, r, g, b, 1.0f, 5)
//                        }
//                    }
//                }
//            }
//
//            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture)
//            IBaubleRender.Helper.rotateIfSneaking(player)
//            var armor = player.getCurrentArmor(2) != null
//            GL11.glRotatef(180F, 1F, 0F, 0F)
//            GL11.glTranslatef(-0.26F, -0.4F, if (armor) 0.21F else 0.15F)
//            GL11.glScalef(0.5F, 0.5F, 0.5F)
//
//            var icon = getBaubleIconFromDamage(stack.itemDamage)
//            if (icon != null)
//                ItemRenderer.renderItemIn2D(Tessellator.instance, icon.maxU, icon.minV, icon.minU, icon.maxV, icon.iconWidth, icon.iconHeight, 1F / 32F)
//        }
//    }
//}

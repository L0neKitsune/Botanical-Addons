package ninja.shadowfox.shadowfox_stuff.common.item

import com.google.common.collect.HashMultimap
import com.google.common.collect.Lists
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityPotion
import net.minecraft.init.Items
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.potion.PotionHelper
import net.minecraft.stats.StatList
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * 1.8 mod
 * created on 2/21/16
 */
class ItemPotionPaste() : ItemMod("potionPaste"), IPotionContainer {
    init {

    }

    override fun getEffects(stack: ItemStack): List<PotionEffect> {
        if (stack.hasTagCompound() && stack.tagCompound.hasKey("CustomPotionEffects", 9)) {
            val list1 = Lists.newArrayList<PotionEffect>()
            val nbttaglist = stack.tagCompound.getTagList("CustomPotionEffects", 10)

            for (i in 0..nbttaglist.tagCount() - 1) {
                val nbttagcompound = nbttaglist.getCompoundTagAt(i)
                val potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound)

                if (potioneffect != null) {
                    list1.add(potioneffect)
                }
            }

            return list1
        } else {
            return Lists.newArrayList<PotionEffect>()
        }
    }

    override fun getMaxItemUseDuration(stack: ItemStack?): Int {
        return 32
    }

    override fun getItemUseAction(stack: ItemStack?): EnumAction {
        return EnumAction.EAT
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack?, playerIn: EntityPlayer?, tooltip: MutableList<String>?, advanced: Boolean) {
        if (stack!!.metadata != 0) {
            val list = Items.potionitem.getEffects(stack)
            val multimap = HashMultimap.create<String, AttributeModifier>()

            if (list != null && !list.isEmpty()) {
                for (potioneffect in list) {
                    var s1 = StatCollector.translateToLocal(potioneffect.effectName).trim { it <= ' ' }
                    val potion = Potion.potionTypes[potioneffect.potionID]
                    val map = potion.attributeModifierMap

                    if (map != null && map.size > 0) {
                        for (entry in map.entries) {
                            val attributemodifier1 = AttributeModifier(entry.value.name, potion.getAttributeModifierAmount(potioneffect.amplifier, entry.value), entry.value.operation)
                            multimap.put((entry.key as IAttribute).attributeUnlocalizedName, attributemodifier1)
                        }
                    }

                    if (potioneffect.amplifier > 0) {
                        s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.amplifier).trim { it <= ' ' }
                    }

                    if (potioneffect.duration > 20) {
                        s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")"
                    }

                    if (potion.isBadEffect) {
                        tooltip!!.add("${EnumChatFormatting.RED}$s1")
                    } else {
                        tooltip!!.add("${EnumChatFormatting.GRAY}$s1")
                    }
                }
            } else {
                val s = StatCollector.translateToLocal("potion.empty").trim { it <= ' ' }
                tooltip!!.add("${EnumChatFormatting.GRAY}$s")
            }

            if (!multimap.isEmpty) {
                tooltip!!.add("")
                tooltip.add("${EnumChatFormatting.DARK_PURPLE}${StatCollector.translateToLocal("potion.effects.whenDrank")}")

                for (entry1 in multimap.entries()) {
                    val d0 = entry1.value.amount
                    var d1: Double

                    if (entry1.value.operation != 1 && entry1.value.operation != 2) {
                        d1 = entry1.value.amount
                    } else {
                        d1 = entry1.value.amount * 100.0
                    }

                    if (d0 > 0.0) {
                        tooltip.add("${EnumChatFormatting.BLUE}${StatCollector.translateToLocalFormatted("attribute.modifier.plus." + entry1.value.operation, *arrayOf<Any>(ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + entry1.key as String)))}")
                    } else if (d0 < 0.0) {
                        d1 *= -1.0
                        tooltip.add("${EnumChatFormatting.RED}${StatCollector.translateToLocalFormatted("attribute.modifier.take." + entry1.value.operation, *arrayOf<Any>(ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + entry1.key as String)))}")
                    }
                }
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    override fun onItemRightClick(itemStackIn: ItemStack, worldIn: World?, playerIn: EntityPlayer?): ItemStack {
        playerIn!!.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn))
        return itemStackIn
    }

    override fun onItemUseFinish(stack: ItemStack, worldIn: World?, playerIn: EntityPlayer?): ItemStack {
        if (!playerIn!!.capabilities.isCreativeMode) {
            --stack.stackSize
        }

        if (!worldIn!!.isRemote) {
            val list = this.getEffects(stack)

            if (list != null) {
                for (potioneffect in list) {
                    playerIn.addPotionEffect(PotionEffect(potioneffect))
                }
            }
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)])

        if (!playerIn.capabilities.isCreativeMode) {
            if (stack.stackSize <= 0) {
                return ItemStack(Items.glass_bottle)
            }

            playerIn.inventory.addItemStackToInventory(ItemStack(Items.glass_bottle))
        }

        return stack
    }

    override fun addEffect(stack: ItemStack, effect: PotionEffect) {
        val nbttaglist: NBTTagList

        if (stack.tagCompound == null) {
            stack.tagCompound = NBTTagCompound()
        }

        if (stack.tagCompound.hasKey("CustomPotionEffects")) {
            nbttaglist = stack.tagCompound.getTagList("CustomPotionEffects", 10)
        } else {
            nbttaglist = NBTTagList()
        }

        nbttaglist.appendTag(effect.writeCustomPotionEffectToNBT(NBTTagCompound()))

        stack.tagCompound.setTag("CustomPotionEffects", nbttaglist)

    }

//    override fun getItemStackDisplayName(stack: ItemStack): String {
//        if (stack.metadata == 0) {
//            return StatCollector.translateToLocal("item.emptyPotion.name").trim { it <= ' ' }
//        } else {
//            var s = ""
//
//            val list: List<PotionEffect> = (ModItems.potionPaste as ItemPotionPaste).getEffects(stack)
//
//            if (list != null && !list.isEmpty()) {
//                var s2 = list[0].effectName
//                s2 += ".postfix"
//                return s + StatCollector.translateToLocal(s2).trim { it <= ' ' }
//            } else {
//                val s1 = PotionHelper.getPotionPrefix(stack.metadata)
//                return StatCollector.translateToLocal(s1).trim { it <= ' ' } + " " + super.getItemStackDisplayName(stack)
//            }
//        }
//    }


}

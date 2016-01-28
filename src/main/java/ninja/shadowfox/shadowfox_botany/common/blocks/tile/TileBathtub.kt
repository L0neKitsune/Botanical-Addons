package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.StatCollector
import org.lwjgl.opengl.GL11
import vazkii.botania.api.BotaniaAPI
import vazkii.botania.api.internal.VanillaPacketDispatcher
import vazkii.botania.api.item.IDyablePool
import vazkii.botania.api.item.IManaDissolvable
import vazkii.botania.api.mana.*
import vazkii.botania.api.mana.spark.ISparkAttachable
import vazkii.botania.api.mana.spark.ISparkEntity
import vazkii.botania.api.recipe.RecipeManaInfusion
import vazkii.botania.client.core.handler.HUDHandler
import vazkii.botania.client.core.handler.LightningHandler
import vazkii.botania.client.core.helper.RenderHelper
import vazkii.botania.common.Botania
import vazkii.botania.common.block.ModBlocks
import vazkii.botania.common.block.tile.mana.TileBellows
import vazkii.botania.common.core.handler.ConfigHandler
import vazkii.botania.common.core.handler.ManaNetworkHandler
import vazkii.botania.common.core.helper.Vector3
import vazkii.botania.common.item.ItemManaTablet
import vazkii.botania.common.item.ModItems
import vazkii.botania.common.lib.LibMisc
import java.awt.Color

/**
 * Created by l0nekitsune on 1/25/16.
 */
class TileBathtub(): TileEntity(), IManaPool, IDyablePool, IKeyLocked, ISparkAttachable, IThrottledPacket {
    val MAX_MANA = 1000000
    val MAX_MANA_DILLUTED = 10000
    internal var outputting = false
    var alchemy = false
    var conjuration = false
    internal var catalystsRegistered = false
    var _color = 0
    internal var mana: Int = 0
    internal var knownMana = -1
    var manaCap = -1
    internal var soundTicks = 0
    internal var canAccept = true
    internal var canSpare = true
    var fragile = false
    var isDoingTransfer = false
    var ticksDoingTransfer = 0
    internal var inputKey = ""
    internal var outputKey = ""
    internal var ticks = 0
    internal var sendPacket = false


    override fun isFull(): Boolean {
        val blockBelow = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord)
        return blockBelow !== ModBlocks.manaVoid && this.currentMana >= this.manaCap
    }

    override fun recieveMana(mana: Int) {
        this.mana = Math.max(0, Math.min(this.getCurrentMana() + mana, this.manaCap))
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord))
        this.markDispatchable()
    }

    override fun invalidate() {
        super.invalidate()
        ManaNetworkEvent.removePool(this)
    }

    override fun onChunkUnload() {
        super.onChunkUnload()
        this.invalidate()
    }

    fun collideEntityItem(item: EntityItem): Boolean {
        if (item.isDead) {
            return false
        } else {
            var didChange = false
            val stack = item.entityItem
            if (stack == null) {
                return false
            } else {
                if (stack.item is IManaDissolvable) {
                    (stack.item as IManaDissolvable).onDissolveTick(this, stack, item)
                    if (stack.stackSize == 0) {
                        item.setDead()
                    }
                }

                if ((item.age <= 100 || item.age >= 130) && this.catalystsRegistered) {
                    val var4 = BotaniaAPI.manaInfusionRecipes.iterator()

                    while (var4.hasNext()) {
                        if (var4.next().matches(stack) && (!var4.next().isAlchemy || this.alchemy) && (!var4.next().isConjuration || this.conjuration)) {
                            val mana = var4.next().manaToConsume
                            if (this.currentMana >= mana) {
                                this.recieveMana(-mana)
                                if (!this.worldObj.isRemote) {
                                    --stack.stackSize
                                    if (stack.stackSize == 0) {
                                        item.setDead()
                                    }

                                    val output = var4.next().output.copy()
                                    val outputItem = EntityItem(this.worldObj, this.xCoord.toDouble() + 0.5, this.yCoord.toDouble() + 1.5, this.zCoord.toDouble() + 0.5, output)
                                    outputItem.age = 105
                                    this.worldObj.spawnEntityInWorld(outputItem)
                                }

                                this.craftingFanciness()
                                didChange = true
                            }
                            break
                        }
                    }

                    return didChange
                } else {
                    return false
                }
            }
        }
    }

    fun craftingFanciness() {
        if (this.soundTicks == 0) {
            this.worldObj.playSoundEffect(this.xCoord.toDouble(), this.yCoord.toDouble(), this.zCoord.toDouble(), "botania:manaPoolCraft", 0.4f, 4.0f)
            this.soundTicks = 6
        }

        for (i in 0..24) {
            val red = Math.random().toFloat()
            val green = Math.random().toFloat()
            val blue = Math.random().toFloat()
            Botania.proxy.sparkleFX(this.worldObj, this.xCoord.toDouble() + 0.5 + Math.random() * 0.4 - 0.2, (this.yCoord + 1).toDouble(), this.zCoord.toDouble() + 0.5 + Math.random() * 0.4 - 0.2, red, green, blue, Math.random().toFloat(), 10)
        }

    }

    override fun updateEntity() {
        val wasDoingTransfer = this.isDoingTransfer
        this.isDoingTransfer = false
        if (this.manaCap == -1) {
            this.manaCap = 1000000
        }

        if (!ManaNetworkHandler.instance.isPoolIn(this) && !this.isInvalid) {
            ManaNetworkEvent.addPool(this)
        }

        if (this.soundTicks > 0) {
            --this.soundTicks
        }

        if (this.worldObj.isRemote) {
            val items = 1.0 - this.currentMana.toDouble() / this.manaCap.toDouble() * 0.1
            val item = Color('웿'.toInt())
            if (Math.random() > items) {
                Botania.proxy.wispFX(this.worldObj, this.xCoord.toDouble() + 0.3 + Math.random() * 0.5, this.yCoord.toDouble() + 0.6 + Math.random() * 0.25, this.zCoord.toDouble() + Math.random(), item.red.toFloat() / 255.0f, item.green.toFloat() / 255.0f, item.blue.toFloat() / 255.0f, Math.random().toFloat() / 3.0f, (-Math.random()).toFloat() / 25.0f, 2.0f)
            }
        }

        if (this.sendPacket && this.ticks % 10 == 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this)
            this.sendPacket = false
        }

        this.alchemy = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) === ModBlocks.alchemyCatalyst
        this.conjuration = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) === ModBlocks.conjurationCatalyst
        this.catalystsRegistered = true
        val var14 = this.worldObj.getEntitiesWithinAABB(EntityItem::class.java, AxisAlignedBB.getBoundingBox(this.xCoord.toDouble(), this.yCoord.toDouble(), this.zCoord.toDouble(), (this.xCoord + 1).toDouble(), (this.yCoord + 1).toDouble(), (this.zCoord + 1).toDouble()))
        val var3 = var14.iterator()

        while (true) {
            var stack: ItemStack?
            var mana: IManaItem
            do {
                do {
                    do {
                        var var15: EntityItem
                        do {
                            if (!var3.hasNext()) {
                                if (this.isDoingTransfer) {
                                    ++this.ticksDoingTransfer
                                } else {
                                    this.ticksDoingTransfer = 0
                                    if (wasDoingTransfer) {
                                        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this)
                                    }
                                }

                                ++this.ticks
                                return
                            }

                            var15 = var3.next() as EntityItem
                        } while (var15.isDead)

                        stack = var15.entityItem
                    } while (stack == null)
                } while (stack!!.item !is IManaItem)

                mana = stack.item as IManaItem
            } while ((!this.outputting || !mana.canReceiveManaFromPool(stack, this)) && (this.outputting || !mana.canExportManaToPool(stack, this)))

            var didSomething = false
            var bellowCount = 0
            var itemVec: Int
            if (this.outputting) {
                val transfRate = LibMisc.CARDINAL_DIRECTIONS
                itemVec = transfRate.size

                for (tileVec in 0..itemVec - 1) {
                    val dir = transfRate[tileVec]
                    val tile = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ)
                    if (tile != null && tile is TileBellows && (tile as TileBellows).linkedTile === this) {
                        ++bellowCount
                    }
                }
            }

            val var16 = 1000 * (bellowCount + 1)
            if (this.outputting) {
                if (this.canSpare) {
                    if (this.currentMana > 0 && mana.getMana(stack) < mana.getMaxMana(stack)) {
                        didSomething = true
                    }

                    itemVec = Math.min(var16, Math.min(this.currentMana, mana.getMaxMana(stack) - mana.getMana(stack)))
                    if (!this.worldObj.isRemote) {
                        mana.addMana(stack, itemVec)
                    }

                    this.recieveMana(-itemVec)
                }
            } else if (this.canAccept) {
                if (mana.getMana(stack) > 0 && !this.isFull) {
                    didSomething = true
                }

                itemVec = Math.min(var16, Math.min(this.manaCap - this.currentMana, mana.getMana(stack)))
                if (!this.worldObj.isRemote) {
                    mana.addMana(stack, -itemVec)
                }

                this.recieveMana(itemVec)
            }

            if (didSomething) {
                if (this.worldObj.isRemote && ConfigHandler.chargingAnimationEnabled && this.worldObj.rand.nextInt(20) == 0) {
                    val var17 = Vector3.fromTileEntity(this).add(0.5, 0.5 + Math.random() * 0.3, 0.5)
                    val var18 = Vector3.fromTileEntity(this).add(0.2 + Math.random() * 0.6, 0.0, 0.2 + Math.random() * 0.6)
                    LightningHandler.spawnLightningBolt(this.worldObj, if (this.outputting) var18 else var17, if (this.outputting) var17 else var18, 80.0f, this.worldObj.rand.nextLong(), 1140881820, 1140901631)
                }

                this.isDoingTransfer = this.outputting
            }
        }
    }

    override fun writeToNBT(cmp: NBTTagCompound?) {
        cmp!!.setInteger("mana", this.mana)
        cmp.setBoolean("outputting", this.outputting)
        cmp.setInteger("color", this.color)
        cmp.setInteger("manaCap", this.manaCap)
        cmp.setBoolean("canAccept", this.canAccept)
        cmp.setBoolean("canSpare", this.canSpare)
        cmp.setBoolean("fragile", this.fragile)
        cmp.setString("inputKey", this.inputKey)
        cmp.setString("outputKey", this.outputKey)
    }

    override fun readFromNBT(cmp: NBTTagCompound?) {
        this.mana = cmp!!.getInteger("mana")
        this.outputting = cmp.getBoolean("outputting")
        this.color = cmp.getInteger("color")
        if (cmp.hasKey("manaCap")) {
            this.manaCap = cmp.getInteger("manaCap")
        }

        if (cmp.hasKey("canAccept")) {
            this.canAccept = cmp.getBoolean("canAccept")
        }

        if (cmp.hasKey("canSpare")) {
            this.canSpare = cmp.getBoolean("canSpare")
        }

        this.fragile = cmp.getBoolean("fragile")
        if (cmp.hasKey("inputKey")) {
            this.inputKey = cmp.getString("inputKey")
        }

        if (cmp.hasKey("outputKey")) {
            this.inputKey = cmp.getString("outputKey")
        }

        if (cmp.hasKey("knownMana")) {
            this.knownMana = cmp.getInteger("knownMana")
        }
    }

    fun onWanded(player: EntityPlayer?, wand: ItemStack) {
        if (player != null) {
            if (player.isSneaking) {
                this.outputting = !this.outputting
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
            }

            if (!this.worldObj.isRemote) {
                val nbttagcompound = NBTTagCompound()
                this.writeToNBT(nbttagcompound)
                nbttagcompound.setInteger("knownMana", this.currentMana)
                if (player is EntityPlayerMP) {
                    player.playerNetServerHandler.sendPacket(S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound))
                }
            }

            this.worldObj.playSoundAtEntity(player, "botania:ding", 0.11f, 1.0f)
        }
    }

    fun renderHUD(mc: Minecraft, res: ScaledResolution) {
        val pool = ItemStack(ModBlocks.pool, 1, this.getBlockMetadata())
        val name = StatCollector.translateToLocal(pool.unlocalizedName.replace("tile.".toRegex(), "tile.botania:") + ".name")
        val color = 4474111
        HUDHandler.drawSimpleManaHUD(color, this.knownMana, this.manaCap, name, res)
        val x = res.scaledWidth / 2 - 11
        val y = res.scaledHeight / 2 + 30
        val u = if (this.outputting) 22 else 0
        val v: Byte = 38
        GL11.glEnable(3042)
        GL11.glBlendFunc(770, 771)
        mc.renderEngine.bindTexture(HUDHandler.manaBar)
        RenderHelper.drawTexturedModalRect(x, y, 0.0f, u, v.toInt(), 22, 15)
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        val tablet = ItemStack(ModItems.manaTablet)
        ItemManaTablet.setStackCreative(tablet)
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting()
        RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, tablet, x - 20, y)
        RenderItem.getInstance().renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, pool, x + 26, y)
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting()
        GL11.glDisable(2896)
        GL11.glDisable(3042)
    }

    override fun canRecieveManaFromBursts(): Boolean {
        return true
    }

    override fun isOutputtingPower(): Boolean {
        return this.outputting
    }

    override fun getCurrentMana(): Int {
        return if (this.worldObj != null && this.getBlockMetadata() == 1) 1000000 else this.mana
    }

    override fun getInputKey(): String {
        return this.inputKey
    }

    override fun getOutputKey(): String {
        return this.outputKey
    }

    override fun canAttachSpark(stack: ItemStack): Boolean {
        return true
    }

    override fun attachSpark(entity: ISparkEntity) {
    }

    override fun getAttachedSpark(): ISparkEntity? {
        val sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity::class.java, AxisAlignedBB.getBoundingBox(this.xCoord.toDouble(), (this.yCoord + 1).toDouble(), this.zCoord.toDouble(), (this.xCoord + 1).toDouble(), (this.yCoord + 2).toDouble(), (this.zCoord + 1).toDouble()))
        if (sparks.size == 1) {
            return sparks[0] as ISparkEntity
        } else {
            return null
        }
    }

    override fun areIncomingTranfersDone(): Boolean {
        return false
    }

    override fun getAvailableSpaceForMana(): Int {
        return Math.max(0, this.manaCap - this.currentMana)
    }

    override fun getColor(): Int {
        return this._color
    }

    override fun setColor(color: Int) {
        this._color = color
    }

    override fun markDispatchable() {
        this.sendPacket = true
    }
}

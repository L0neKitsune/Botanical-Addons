package ninja.shadowfox.shadowfox_botany.client.core.render.tile

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


@SideOnly(Side.CLIENT)
@Deprecated("1.7.10")
class RenderTileItemDisplay()

//: TileEntitySpecialRenderer() {
//    internal var renderBlocks = RenderBlocks()
//
//    fun renderEntity(display: TileItemDisplay?, x: Double, y: Double, z: Double, partticks: Float) {
//        val seed = Minecraft.getMinecraft()
//
//        if (display != null && display.worldObj != null && seed != null) {
//
//            GL11.glPushMatrix()
//            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
//            GL11.glTranslated(x, y, z)
//
//            val var27 = (ClientTickHandler.ticksInGame.toFloat() + partticks).toDouble()
//
//            GL11.glPushMatrix()
//            GL11.glScalef(0.5f, 0.5f, 0.5f)
//            GL11.glTranslatef(1.0f, 1.25f, 1.0f)
//            GL11.glRotatef(360f + var27.toFloat(), 0.0f, 1.0f, 0.0f)
//            GL11.glTranslatef(0.0f, 0.0f, 0.5f)
//            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f)
//            GL11.glTranslated(0.0, 0.15 * Math.sin(var27 / 7.5), 0.0)
//            val scale = display.getStackInSlot(0)
//
//            if (scale != null) {
//                seed.renderEngine.bindTexture(if (scale.item is ItemBlock) TextureMap.locationBlocksTexture else TextureMap.locationItemsTexture)
//                GL11.glScalef(2.0f, 2.0f, 2.0f)
//                GL11.glTranslatef(0.25f, 0f, 0f)
//                if (!ForgeHooksClient.renderEntityItem(EntityItem(display.worldObj, display.xCoord.toDouble(), display.yCoord.toDouble(), display.zCoord.toDouble(), scale), scale, 0.0f, 0.0f, display.worldObj.rand, seed.renderEngine, this.renderBlocks, 1)) {
//                    GL11.glTranslatef(-0.25f, 0f, 0f)
//                    GL11.glScalef(0.5f, 0.5f, 0.5f)
//                    if (scale.item is ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(scale.item).renderType)) {
//                        GL11.glScalef(0.5f, 0.5f, 0.5f)
//                        GL11.glTranslatef(1.0f, 1.1f, 0.0f)
//                        this.renderBlocks.renderBlockAsItem(Block.getBlockFromItem(scale.item), scale.itemDamage, 1.0f)
//                        GL11.glTranslatef(-1.0f, -1.1f, 0.0f)
//                        GL11.glScalef(2.0f, 2.0f, 2.0f)
//                    } else if (scale.item is ItemBlock && !RenderBlocks.renderItemIn3d(Block.getBlockFromItem(scale.item).renderType)) {
//                        var entityitem: EntityItem?
//                        GL11.glPushMatrix()
//
//                        GL11.glScalef(2.0f, 2.0f, 2.0f)
//                        GL11.glTranslatef(.25f, .275f, 0.0f)
//
//
//                        val `is` = scale.copy()
//                        `is`.stackSize = 1
//                        entityitem = EntityItem(display.worldObj, 0.0, 0.0, 0.0, `is`)
//                        entityitem.hoverStart = 0.0f
//                        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0, 0.0, 0.0, 0.0f, 0.0f)
//
//                        GL11.glTranslatef(-.25f, -.275f, 0.0f)
//
//                        GL11.glPopMatrix()
//                    } else {
//                        var renderPass = 0
//
//                        do {
//                            val icon = scale.item.getIcon(scale, renderPass)
//                            if (icon != null) {
//                                val color = Color(scale.item.getColorFromItemStack(scale, renderPass))
//                                GL11.glColor3ub(color.red.toByte(), color.green.toByte(), color.blue.toByte())
//                                val f = icon.minU
//                                val f1 = icon.maxU
//                                val f2 = icon.minV
//                                val f3 = icon.maxV
//                                ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.iconWidth, icon.iconHeight, 0.0625f)
//                                GL11.glColor3f(1.0f, 1.0f, 1.0f)
//                            }
//
//                            ++renderPass
//                        } while (renderPass < scale.item.getRenderPasses(scale.itemDamage))
//                    }
//                }
//            }
//
//            GL11.glPopMatrix()
//
//
//            GL11.glDisable(3008)
//            GL11.glPushMatrix()
//            GL11.glTranslatef(0.5f, 1.8f, 0.5f)
//            GL11.glRotatef(180.0f, 1.0f, 0.0f, 1.0f)
//            GL11.glPopMatrix()
//            GL11.glTranslatef(0.0f, 0.2f, 0.0f)
//
//            GL11.glEnable(3008)
//            GL11.glPopMatrix()
//        }
//    }
//
//    override fun renderTileEntityAt(par1TileEntity: TileEntity?, par2: Double, par4: Double, par6: Double, par8: Float) {
//        this.renderEntity(par1TileEntity as TileItemDisplay, par2, par4, par6, par8)
//    }
//}

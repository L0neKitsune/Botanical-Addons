package ninja.shadowfox.shadowfox_botany.common.blocks.tile

import codechicken.core.CommonUtils
import codechicken.nei.NEIClientConfig
import codechicken.nei.NEIClientUtils
import cpw.mods.fml.relauncher.FMLLaunchHandler
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChatComponentText
import net.minecraftforge.common.util.ForgeDirection
import ninja.shadowfox.shadowfox_botany.common.blocks.ShadowFoxBlocks
import vazkii.botania.client.core.handler.ClientTickHandler
import vazkii.botania.common.Botania
import java.awt.Color
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*


class TileSchema() : ShadowFoxTile() {

    private var ticksAlive: Int = 0

    var pos_x: Pos? = null
    var pos_y: Pos? = null
    var pos_z: Pos? = null

    var mark_x: Pos? = null
    var mark_z: Pos? = null

    var pos_xoz: Pos? = null
    var pos_xyx: Pos? = null
    var pos_zyz: Pos? = null
    var pos_xyz: Pos? = null

    var validDir: MutableList<ForgeDirection> = ForgeDirection.VALID_DIRECTIONS.toArrayList()

    class Pos(val x: Int, val y: Int, val z: Int, val dir: ForgeDirection)

    override fun updateEntity() {
        20.tickDelay {
            for (v in arrayOf(1, 2, 3)) {
                isPosValid(v)
            }
        }

        if (pos_x != null && pos_y != null && pos_z != null) {
            if (pos_xoz == null && pos_xyx == null && pos_zyz == null && pos_xyz == null) {
                pos_xoz = Pos(pos_x!!.x, yCoord,    pos_z!!.z, ForgeDirection.UNKNOWN)
                pos_xyz = Pos(pos_x!!.x, pos_y!!.y, pos_z!!.z, ForgeDirection.UNKNOWN)
                pos_xyx = Pos(pos_x!!.x, pos_y!!.y, pos_x!!.z, ForgeDirection.UNKNOWN)
                pos_zyz = Pos(pos_z!!.x, pos_y!!.y, pos_z!!.z, ForgeDirection.UNKNOWN)

            } else {
//                20.tickDelay {
//                    if (mark_x == null) {
//                        for(x in xCoord..pos_x!!.x) {
//                            for (y in yCoord..pos_y!!.y) {
//                                val block = worldObj.getBlock(x, y, zCoord)
//
//                                if (block === ShadowFoxBlocks.markerBlock)
//                                    mark_x = Pos(x, y, zCoord, ForgeDirection.UNKNOWN)
//                            }
//                        }
//                    }
//                    if (mark_z == null) {
//                        for(z in zCoord..pos_z!!.z) {
//                            for (y in yCoord..pos_y!!.y) {
//                                val block = worldObj.getBlock(xCoord, y, z)
//
//                                if (block === ShadowFoxBlocks.markerBlock)
//                                    mark_z = Pos(xCoord, y, z, ForgeDirection.UNKNOWN)
//                            }
//                        }
//                    }
//                }

                10.tickDelay {
                    drawBoundingLine(null, pos_x!!)
                    drawBoundingLine(null, pos_y!!)
                    drawBoundingLine(null, pos_z!!)

                    drawBoundingLine(pos_z!!, pos_zyz!!)
                    drawBoundingLine(pos_z!!, pos_xoz!!)

                    drawBoundingLine(pos_x!!, pos_xyx!!)
                    drawBoundingLine(pos_x!!, pos_xoz!!)

                    drawBoundingLine(pos_y!!, pos_xyx!!)
                    drawBoundingLine(pos_y!!, pos_zyz!!)

                    drawBoundingLine(pos_xyx!!, pos_xyz!!)
                    drawBoundingLine(pos_xoz!!, pos_xyz!!)
                    drawBoundingLine(pos_zyz!!, pos_xyz!!)

//                    if (mark_x != null) {
//                        drawBoundingLine(mark_x, Pos(mark_x!!.x, mark_x!!.y, pos_z!!.z, ForgeDirection.UNKNOWN))
//                    }
//                    if (mark_z != null) {
//                        drawBoundingLine(mark_z, Pos(pos_x!!.x, mark_z!!.y, mark_z!!.z, ForgeDirection.UNKNOWN))
//                    }
                }
            }
        } else {
            10.tickDelay {
                for (v in arrayOf(1, 2, 3)) {
                    val pos = getPos(v)
                    if (pos != null) drawBoundingLine(null, pos)
                }

                for (dir in validDir)
                    for (i in 0..64) {
                        Botania.proxy.wispFX(worldObj,
                                xCoord + (dir.offsetX * i) + .5,
                                yCoord + (dir.offsetY * i) + .5,
                                zCoord + (dir.offsetZ * i) + .5,
                                Math.abs(dir.offsetY).toFloat(), Math.abs(dir.offsetX).toFloat(),
                                Math.abs(dir.offsetZ).toFloat(), 0.3f, -0.01f)
                    }
            }

            30.tickDelay {
                loop@for (dir in ForgeDirection.VALID_DIRECTIONS) {
                    if (dir in validDir) {
                        for (i in 1..65) {
                            var x = xCoord + (dir.offsetX * i)
                            var y = yCoord + (dir.offsetY * i)
                            var z = zCoord + (dir.offsetZ * i)

                            var block = worldObj.getBlock(x, y, z)

                            if (block === ShadowFoxBlocks.markerBlock) {
                                setPos(getType(dir), Pos(x, y, z, dir))
                                validDir.remove(dir)
                                validDir.remove(dir.opposite)
                                continue@loop
                            }
                        }
                    }
                }
            }
        }

        ticksAlive++
    }

    private fun isPosValid(i: Int) {
        val pos = getPos(i)
        if (pos != null) {
            var block = worldObj.getBlock(pos.x, pos.y, pos.z)

            if (block !== ShadowFoxBlocks.markerBlock) {
                validDir.add(pos.dir)
                validDir.add(pos.dir.opposite)
                clearInferred()
                setPos(i, null)
            }
        }
    }

    public fun getType(dir: ForgeDirection): Int {
        when (dir) {
            ForgeDirection.UP, ForgeDirection.DOWN -> return 2
            ForgeDirection.NORTH, ForgeDirection.SOUTH -> return 3
            ForgeDirection.EAST, ForgeDirection.WEST -> return 1
            else -> return -1
        }
    }

    private fun getPos(i :Int): Pos? {
        when (i) {
            1 -> return pos_x
            2 -> return pos_y
            else -> return pos_z
        }
    }

    private fun setPos(i :Int, pos: Pos?) {
        when (i) {
            1 -> pos_x = pos
            2 -> pos_y = pos
            else -> pos_z = pos
        }
    }

    private fun clearInferred(){
        pos_xoz = null
        pos_xyx = null
        pos_zyz = null
        pos_xyz = null

        mark_x = null
        mark_z = null
    }

    private fun drawBoundingLine(pos1: Pos?, pos2: Pos?) {
        val dir = getDir(pos1, pos2)

        val x = if (pos1 != null) pos1.x else xCoord
        val y = if (pos1 != null) pos1.y else yCoord
        val z = if (pos1 != null) pos1.z else zCoord

        val x2 = if (pos2 != null) pos2.x else xCoord
        val y2 = if (pos2 != null) pos2.y else yCoord
        val z2 = if (pos2 != null) pos2.z else zCoord

        if (checkPos(x2.toDouble(), y2.toDouble(), z2.toDouble(), x.toDouble(), y.toDouble(), z.toDouble())) {
            drawBoundingLine(pos2, pos1)
            return
        }


        loop@for (i in 0..256) {
            coloredFlame(x + .5 + (dir.offsetX * (i * .25)),
                    y + .5 + (dir.offsetY * (i * .25)),
                    z + .5 + (dir.offsetZ * (i * .25)))

            if (checkPos(
                    x2.toDouble(), y2.toDouble(), z2.toDouble(),
                    x + (dir.offsetX * (i * .25)),
                    y + (dir.offsetY * (i * .25)),
                    z + (dir.offsetZ * (i * .25))))
                break@loop

        }
    }

    public fun checkPos(x: Double, y: Double, z: Double, x2: Double, y2: Double, z2: Double): Boolean {
        return Math.abs(x) < Math.abs(x2) || Math.abs(y) < Math.abs(y2) || Math.abs(z) < Math.abs(z2)
    }

    private fun getDir(pos1: Pos?, pos2: Pos?): ForgeDirection {
        val x = if (pos1 != null) pos1.x else xCoord
        val y = if (pos1 != null) pos1.y else yCoord
        val z = if (pos1 != null) pos1.z else zCoord

        val x2 = if (pos2 != null) pos2.x else xCoord
        val y2 = if (pos2 != null) pos2.y else yCoord
        val z2 = if (pos2 != null) pos2.z else zCoord

        if (x > x2) return ForgeDirection.WEST
        if (x < x2) return ForgeDirection.EAST
        if (y > y2) return ForgeDirection.DOWN
        if (y < y2) return ForgeDirection.UP
        if (z > z2) return ForgeDirection.NORTH
        if (z < z2) return ForgeDirection.SOUTH

        return ForgeDirection.UNKNOWN
    }

    private fun coloredFlame(x: Double, y: Double, z: Double) {

        val v = 0.1f
        val r = (getColor() shr 16 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
        val g = (getColor() shr 8 and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v
        val b = (getColor() and 255).toFloat() / 255.0f + (Math.random() - 0.5).toFloat() * v

        Botania.proxy.wispFX(this.worldObj, x, y, z, r, g, b, 0.3f, -0.01f)

    }

    private fun getColor(): Int {
        if (FMLLaunchHandler.side().isServer) return 0xFFFFFF
        var time = ClientTickHandler.ticksInGame + ClientTickHandler.partialTicks
        time += Random((this.xCoord xor this.yCoord xor this.zCoord).toLong()).nextInt(100000)
        return Color.HSBtoRGB(time * 0.005F, 1F, 1F)
    }

    public fun blockActivated() {
        if (ticksAlive > 60) dumpFile()
    }

    fun dumpFile() {
        try {
            val e = getNewFile()
            dumpTo(e)

            NEIClientUtils.printChatMessage(ChatComponentText("Schema dumped to: ${e.path}"))
        } catch (var2: Exception) {
            NEIClientConfig.logger.error("Error dumping schema", var2)
        }
    }

    fun getNewFile(file: Int = 0): File {
        val e = File(CommonUtils.getMinecraftDir(), "dumps/" + getFileName("schema_dump_$file"))
        if (!e.parentFile.exists()) {
            e.parentFile.mkdirs()
        }

        if (!e.exists()) {
            e.createNewFile()
        } else {
            return getNewFile(file + 1)
        }

        return e
    }

    fun getFileName(prefix: String): String {
        return prefix + getFileExtension()
    }

    fun getFileExtension(): String {
        return ".txt"
    }

    @Throws(IOException::class)
    fun dumpTo(file: File) {
        val w = PrintWriter(file)
        w.println("Stuff should be here")


        w.close()
    }

    fun Int.tickDelay(lambda: () -> Any) {
        if (ticksAlive % this == 0) {
            lambda.invoke()
        }
    }

    override fun writeCustomNBT(nbttagcompound: NBTTagCompound) {
    }

    override fun readCustomNBT(nbttagcompound: NBTTagCompound) {
    }
}

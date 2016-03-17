package ninja.shadowfox.shadowfox_botany.common.blocks.schema


@Deprecated("1.7.10")
class BlockSchematicOak()
//: BlockColoredSapling(name = "schematicOak") {
//
//    override fun growTree(world: World?, x: Int, y: Int, z: Int, random: Random?) {
//        if (world != null) {
//
//            val plantedOn: Block = world.getBlock(x, y - 1, z)
//
//            if (canGrowHere(plantedOn)) {
//                val l = if (plantedOn === ShadowFoxBlocks.coloredDirtBlock) world.getBlockMetadata(x, y - 1, z) else -1
//
//                if (l in ConfigHandler.schemaArray) {
//                    val schemaText = getSchema(l)
//
//                    if (schemaText != null) {
//                        val type = object : TypeToken<List<BlockElement>>() {}.type
//
//                        var arr = Gson().fromJson<List<BlockElement>>(schemaText, type)
//                        world.setBlock(x, y, z, Blocks.air, 0, 4)
//
//                        for (ele in arr) {
//                            for (loc in ele.location) {
//                                world.setBlock(x + loc.x, y + loc.y, z + loc.z, Block.getBlockFromName(ele.block), loc.meta, 3)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    override fun canGrowHere(block: Block): Boolean {
//        return block.material == Material.ground || block.material == Material.grass
//    }
//
//    fun getSchema(meta: Int = -1): String? {
//        val e = File(CommonUtils.getMinecraftDir(), "config/BotanicalAddons/schema${if (meta < 0) "" else "_$meta"}.txt")
//        if (!e.parentFile.exists()) e.parentFile.mkdirs()
//
//        if (e.exists()) {
//            return e.readText()
//        } else return null
//    }
//
//    class BlockElement(val block: String, val location: List<LocationElement>)
//    class LocationElement(val x: Int, val y: Int, val z: Int, val meta: Int)
//}

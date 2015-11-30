/*

class BlockManaBathtub : ShadowFoxBlockMod(Material.rock) {


    override fun getIcon(par1: Int, par2: Int): IIcon {
        return ModBlocks.livingrock.getIcon(par1, 0)
    }

    override fun registerBlockIcons(par1IconRegister: IIconRegister) {}

    override fun addCollisionBoxesToList(world: World, x: Int, y: Int, z: Int, axis: AxisAlignedBB, list: MutableList<Any?>, entity: Entity) {
        val pixelwidth = 1F / 16F
        if (isSideSolid(world, x, y, z, ForgeDirection.DOWN)) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, pixelwidth, 1.0F)
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity)
        }
        if (isSideSolid(world, x, y, z, ForgeDirection.WEST)) {
            setBlockBounds(0.0F, 0.0F, 0.0F, pixelwidth, 1.0F, 1.0F)
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity)
        }
        if (isSideSolid(world, x, y, z, ForgeDirection.NORTH)) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, pixelwidth)
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity)
        }
        if (isSideSolid(world, x, y, z, ForgeDirection.EAST)) {
            setBlockBounds(1.0F - pixelwidth, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity)
        }
        if (isSideSolid(world, x, y, z, ForgeDirection.SOUTH)) {
            setBlockBounds(0.0F, 0.0F, 1.0F - pixelwidth, 1.0F, 1.0F, 1.0F)
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity)
        }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
    }

    override fun isSideSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean {
        return side == ForgeDirection.DOWN;
    }

    fun isCornerSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection): Boolean {
        val block = world.getBlock(x+side.offsetX, y+side.offsetY, z+side.offsetZ)
        return block == this
    }

    fun isEdgeConnected(world: IBlockAccess, x: Int, y: Int, z: Int, edge: EdgeDirection): Boolean {
        val sidePrimary = isSideConnected(world, x, y, z, edge.primary)
        if (!sidePrimary) return false
        val sideSecondary = isSideConnected(world, x, y, z, edge.secondary)
        if (!sideSecondary) return false
        val block = world.getBlock(x+edge.offsetX, y+edge.offsetY, z+edge.offsetZ)
        return block == this
    }

    override fun getRenderType(): Int = Constants.bathtubRenderID
    override fun isOpaqueCube(): Boolean = false
    override fun renderAsNormalBlock(): Boolean = false

    // public class BathtubRenderer: ISimpleBlockRenderingHandler {
    //     public override fun getRenderId(): Int {
    //         return Constants.bathtubRenderID
    //     }

    //     public override fun shouldRender3DInInventory(modelId: Int): Boolean {return false}

    //     public override fun renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks){}

    //     public override fun renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean {
    //         GL11.glPushMatrix()
    //         GL11.glEnable(GL11.GL_BLEND)
    //         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    //         GL11.glEnable(GL12.GL_RESCALE_NORMAL)
    //         val a = if (MultiblockRenderHandler.rendering) 0.6f else 1f
    //         GL11.glColor4f(1f, 1f, 1f, a)
    //         GL11.glTranslated(d0, d1, d2)
    //         Minecraft.getMinecraft().renderEngine.bindTexture(texture)
    //         GL11.glTranslatef(0.5f, 1.5f, 0.5f)
    //         GL11.glScalef(1f, -1f, -1f)
    //         model.render()
    //         GL11.glScalef(1f, -1f, -1f)
    //         GL11.glEnable(GL12.GL_RESCALE_NORMAL)
    //         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture)
    //         val s = 1f / 256f * 14f
    //         GL11.glPushMatrix()
    //         GL11.glEnable(GL11.GL_BLEND)
    //         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    //         GL11.glDisable(GL11.GL_ALPHA_TEST)
    //         GL11.glColor4f(1f, 1f, 1f, a)
    //         GL11.glTranslatef(w, -0.53f, w)
    //         GL11.glRotatef(90f, 1f, 0f, 0f)
    //         GL11.glScalef(s, s, s)
    //         ShaderHelper.useShader(ShaderHelper.manaPool)
    //         renderIcon(0, 0, BlockPool.manaIcon, 16, 16, 240)
    //         ShaderHelper.releaseShader()
    //         GL11.glEnable(GL11.GL_ALPHA_TEST)
    //         GL11.glDisable(GL11.GL_BLEND)
    //         GL11.glPopMatrix()
    //         GL11.glPopMatrix()
    //
    //         return true
    //     }
    // }

    class EdgeDirection(val primary: ForgeDirection, val secondary: ForgeDirection) {
        val offsetX: Int
        val offsetY: Int
        val offsetZ: Int
        val flag: Int
        init {
            offsetX = primary.offsetX + secondary.offsetX
            offsetY = primary.offsetY + secondary.offsetY
            offsetZ = primary.offsetZ + secondary.offsetZ
            flag = primary.flag && secondary.flag
        }
    }
}
*/
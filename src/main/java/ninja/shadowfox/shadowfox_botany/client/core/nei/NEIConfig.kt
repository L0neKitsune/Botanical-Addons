package ninja.shadowfox.shadowfox_botany.client.core.nei

import ninja.shadowfox.shadowfox_botany.lib.Constants
import codechicken.nei.api.API
import codechicken.nei.api.IConfigureNEI

public class NEIConfig: IConfigureNEI {

    override fun getName(): String = Constants.MODNAME
    override fun getVersion(): String = Constants.VERSION

    override fun loadConfig() {
        API.registerRecipeHandler(RecipeHandlerTreeCrafting())
        API.registerUsageHandler(RecipeHandlerTreeCrafting())
    }

}

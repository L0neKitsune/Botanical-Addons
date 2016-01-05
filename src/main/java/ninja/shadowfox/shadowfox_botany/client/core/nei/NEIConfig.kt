package ninja.shadowfox.shadowfox_botany.client.core.nei

import codechicken.nei.api.API
import codechicken.nei.api.IConfigureNEI
import ninja.shadowfox.shadowfox_botany.lib.Constants

public class NEIConfig : IConfigureNEI {

    override fun getName(): String = Constants.MODNAME
    override fun getVersion(): String = Constants.VERSION

    override fun loadConfig() {
        API.registerRecipeHandler(RecipeHandlerTreeCrafting())
        API.registerUsageHandler(RecipeHandlerTreeCrafting())
    }

}

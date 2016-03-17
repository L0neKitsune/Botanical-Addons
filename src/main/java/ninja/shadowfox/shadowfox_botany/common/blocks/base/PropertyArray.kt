package ninja.shadowfox.shadowfox_botany.common.blocks.base

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Maps
import com.sun.jna.StringArray
import net.minecraft.block.properties.PropertyHelper
import net.minecraft.util.IStringSerializable

/**
 * kitsune
 * created on 3/11/16
 */
class PropertyArray protected constructor(name: String, allowedValues: Array<Property>) : PropertyHelper<Property>(name, Property::class.java) {
    private val allowedValues: ImmutableSet<Property>
    private val nameToValue = Maps.newHashMap<String, Property>()

    init {
        this.allowedValues = ImmutableSet.copyOf(allowedValues)

        for (t in allowedValues) {
            val s = (t as IStringSerializable).name

            if (this.nameToValue.containsKey(s)) {
                throw IllegalArgumentException("Multiple values have the same name \'" + s + "\'")
            }

            this.nameToValue.put(s, t)
        }
    }

    override fun getAllowedValues(): ImmutableSet<Property> {
        return this.allowedValues
    }

    /**
     * Get the name for the given value.
     */
    override fun getName(value: Property): String {
        return (value as IStringSerializable).name
    }

    companion object {

        fun create(name: String, values: Array<Property>): PropertyArray {
            return PropertyArray(name, values)
        }
    }
}

class Property(val _name: String, val meta: Int) : Comparable<Property>, IStringSerializable {
    override fun compareTo(other: Property): Int = meta.compareTo(other.meta)
    override fun getName(): String = _name

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true

        when(other) {
            is Int -> return meta == other
            is String -> return _name == other
            is Property -> {
                if (_name != other._name) return false
                if (meta != other.meta) return false
            }
        }
        return false
    }

    override fun hashCode(): Int{
        var result = _name.hashCode()
        result += 31 * result + meta
        return result
    }


    companion object {
        fun fromStringArray(vararg stringArray: String): Array<Property>{
            return Array(stringArray.size, {i -> Property(stringArray[i], i) })
        }
    }
}

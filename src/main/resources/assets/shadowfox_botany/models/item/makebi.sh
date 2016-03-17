#!/bin/sh

# Vazkii's JSON creator for items
# Ported to sh by WireSegal
# Put in your /resources/assets/$MODID/models/item
# Makes basic item JSON files
# Requires a _standard_item.json to be present
# Can make multiple items at once
#
# Usage:
# makei (item name 1) (item name 2) (item name x)

# Change this to your mod's ID
MODID="kitsune"

if [ $# -eq 0 ]
  then
    echo "Usage:"
    echo "makeb [item name] [item name] etc..."
fi

for BLOCK in "$@"
do
	echo "Making $BLOCK.json item"
	echo "{
    \"parent\": \"$MODID:block/$BLOCK\",
    \"display\": {
            \"thirdperson\": {
            \"rotation\": [ 10, -45, 170 ],
            \"translation\": [ 0, 1.5, -2.75 ],
            \"scale\": [ 0.375, 0.375, 0.375 ]
        }
    }
}
"	> "../item/$BLOCK.json"
done

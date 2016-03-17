#!/bin/sh

# Vazkii's JSON creator for blocks
# Ported to sh by WireSegal
# Put in your /resources/assets/$MODID/models/block
# Makes basic block JSON files as well as the acossiated item and simple blockstate
# Can make multiple blocks at once
#
# Usage:
# makeb (block name 1) (block name 2) (block name x)

# Change this to your mod's ID
MODID="mod"

if [ $# -eq 0 ]
  then
    echo "Usage:"
    echo "makeb [item name] [item name] etc..."
fi

for BLOCK in "$@"
do
	echo "Making $BLOCK.json block"
	echo "{
    \"parent\": \"block/cube_all\",
    \"textures\": {
        \"all\": \"$MODID:blocks/$BLOCK\"
    }
}
" > "$BLOCK.json"
done

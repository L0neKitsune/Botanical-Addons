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
MODID="kitsune"

if [ $# -eq 0 ]
  then
    echo "Usage:"
    echo "makeb [item name] [item name] etc..."
fi

for BLOCK in "$@"
do
	echo "Making $BLOCK.json blockstate"
	echo "{
  \"forge_marker\": 1,
  \"defaults\": {
    \"model\": \"$MODID:$BLOCK\"
  },
  \"variants\": {
    \"normal\": [{}],
    \"inventory\": [{}]
  }
}
" > "$BLOCK.json"
done

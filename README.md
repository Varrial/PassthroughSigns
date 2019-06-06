# Passthrough Signs

Simple mod that, right-clicking on a wall sign, interacts with the block it's attached to.

Example: Right-clicking on a sign attached to a chest will open the chest

Download on [CurseForge](https://minecraft.curseforge.com/projects/passthrough-signs)

How to get Passthrough Signs through maven
---
Add to your build.gradle:
```gradle
repositories {
  maven {
    // url of the maven that hosts Passthrough Signs files
    url "https://girafi.dk/maven/"
  }
}

dependencies {
  // compile against Passthrough Signs
  deobfCompile "com.girafi.passthroughsigns:passthroughsigns_${mc_version}:${mc_version}-${passthroughsigns_version}"
}
```

`${mc_version}` & `${passthroughsigns_version}` can be found [here](https://girafi.dk/maven/com/girafi/passthroughsigns/), check the file name of the version you want.

Passthrough Signs also have intermod communicaiton, example of how to use it: 
`FMLInterModComms.sendMessage("passthroughsigns", "registerPassable", "block/entity registry name");`

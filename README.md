# Compose-Round-CheckBox
[![](https://jitpack.io/v/kosher9/Compose-Round-CheckBox.svg)](https://jitpack.io/#kosher9/Compose-Round-CheckBox)

RoundCheckboxes allow users to select one or more items from a set. RoundCheckboxes can turn an option on or off.
It has a great and smooth animation that can make your app have a better look. :)

## Preview
https://github.com/kosher9/Compose-Round-CheckBox/assets/34873327/dee17090-e20a-4309-9595-2b16f9028206

## Gradle Setup

To get a Git project into your build:

* Step 1. Add the JitPack repository to your build file Add it in your root build.gradle at the end
  of repositories:

```
allprojects {
  repositories {
      ...
      maven { url 'https://jitpack.io' }
  }
}
```

* Step 2. Add the dependency

```
dependencies {
	  implementation 'com.github.kosher9:Compose-Round-CheckBox:Tag'
}
```

To use the Compose-Round-CheckBox inside your Composable, here is a quick example:
```
var roundCheckBoxState by remember { mutableStateOf(false) }
RoundCheckBox(
                modifier = Modifier.width(60.dp),
                isChecked = roundCheckBoxState,
                onClick = { roundCheckBoxState = !roundCheckBoxState },
                enabled = true
            )
```

Check out more examples [here](https://github.com/kosher9/Compose-Round-CheckBox/blob/main/app/src/main/java/com/kosher9/app/MainActivity.kt)

## 📝 License

This project is [MIT](./LICENSE.md) licensed.
# SpringActionMenu
This is a spring ActionMenu - 一个弹性的功能菜单

Preview  预览
-------------
![SpringActionMenu](https://github.com/lilei644/SpringActionMenu/blob/master/preview/preview.gif)

  
  Gif there is distortion, the actual effect is more flexible

  gif存在失真，实际效果更加丝滑有弹性

## Installation &nbsp;安装
* gradle

```
Step 1. Add the JitPack repository to your build file

allprojects {
    repositories {
        jcenter()

        maven { url "https://jitpack.io" }
    }
}
```
```
Step 2. Add the dependency

dependencies {
            compile 'com.github.lilei644:SpringActionMenu:1.0.0'
    }
```

## Usage &nbsp;用法
* xml init &nbsp; xml初始化
```
<com.lilei.springactionmenu.ActionMenu
        android:id="@+id/actionMenu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentRight="true"
        android:layout_marginBottom="20dp"
        app:circleRadius="25dp"
        app:dimens="10dp"
        app:animationDuration="2500"
        app:expandDirect="expandDirectTop"
        app:buttonNormalColor="#ff5bc0de"
        app:buttonPressColor="#ff39b3d7"
        app:actionMenuIcon="@drawable/add"
        app:actionMenuOnIcon="@drawable/close"
        />
```

* add menu items &nbsp; 添加菜单组件添加菜单组件添加

```
ActionMenu actionMenu = (ActionMenu) findViewById(R.id.actionMenu);

// add menu items
actionMenu.addView(R.drawable.search, getItemColor(R.color.menuNormalInfo), getItemColor(R.color.menuPressInfo));
actionMenu.addView(R.drawable.like, getItemColor(R.color.menuNormalRed), getItemColor(R.color.menuPressRed));
actionMenu.addView(R.drawable.write);
```

* delegate &nbsp;代理监听
```
actionMenu.setItemClickListener(new OnActionItemClickListener() {
    @Override
    public void onItemClick(int index) {

    }

    @Override
    public void onAnimationEnd(boolean isOpen) {

    }
});
```

* attr property &nbsp; 属性

|name|format|description|default|
|:---:|:---:|:---:|:---:|
| circleRadius| dimension| Round menu radius| 30
| dimens| dimension| The spacing of the menu items| 30
| animationDuration| integer| Animation duration| 500
| buttonNormalColor| color| Button The color in normal condition|  Color.RED
| buttonPressColor| color|The color of the button click status|Color.RED
| actionMenuIcon| reference| Menu icon| --
| actionMenuOnIcon| reference| The icon when the menu is open| --
| expandDirect| enum | The direction of the menu extension|expandDirectTop

## Requirements &nbsp;版本要求
Android  14+

## License
```
   Copyright 2016 lilei644

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```


# Implmentação do Login com meuID (Android)

### Resumo: 
* Baixar arquivos de configuração e adicionar na pasta drawable;
* Criar _custom view_ (_**`MeuIdButton.kt`**_)  e setar parâmetros;
* Inserir _custom view_  (_**`MeuIdButton.kt`**_)  no layout da activity/fragment;
* Setar _**`androidManifest.xml`**_ para receber resposta do meuID;
* Obter os parâmetros da query do deeplink enviado pelo meuID.

### 1). Arquivos de configuração
#### 1.a) Baixe [aqui](https://gitlab.idwall.space/mobile/android/sampleloginmeuid-android/-/tree/master/app/src/main/res/drawable) os arquivos relacionados abaixo e adicione-os na sua pasta _drawable_ :
_**`bg_meuid_btn_dark.xml`**_

_**`bg_meuid_btn_light.xml`**_

_**`bg_meuid_btn_light_outline.xml`**_

_**`ic_meuid_logo_dark.xml`**_

_**`ic_meuid_logo_light.xml`**_

_**`ic_meuid_text_dark.xml`**_


### 2). Criar _Custom View_ e setar parâmetros:
#### 2.a). Crie uma CustomView _`MeuIdButton.kt`_ com o seguinte código:
#### _**`MeuIdButton.kt`**_
```kotlin
// TODO: (“Updtade with your package path”)
package com.sampleloginmeuid_android

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat

class MeuIdButton(context: Context, attrs: AttributeSet? = null) : AppCompatButton(context, attrs) {

   init {
       setBtnAsEnable()
       setBtnLayoutGravity()
       setBtnStyle()
       setBtnSize()
       setBtnClickListener()
   }

   private fun setBtnAsEnable() {
       isEnabled = true
   }

   private fun setBtnLayoutGravity() {
       gravity = Gravity.CENTER
   }

   private fun setBtnStyle() {
       val style = buttonStyle
       setBtnBackground(style)
       setBtnContent(style)
   }

   private fun setBtnBackground(style: String) {
       setBackgroundResource(
           when (style) {
               "button_light" -> R.drawable.bg_meuid_btn_light
               "button_light_outline" -> R.drawable.bg_meuid_btn_light_outline
               "button_dark" -> R.drawable.bg_meuid_btn_dark
               else -> R.drawable.bg_meuid_btn_dark
           }
       )
   }

   private fun setBtnContent(style: String) {
       val icons: Pair<Drawable?, Drawable?> = when (style) {
           "button_light", "button_light_outline" -> Pair(
               getDrawable(R.drawable.ic_meuid_logo_light),
               getDrawable(R.drawable.ic_meuid_text_light)
           )
           "button_dark" -> Pair(
               getDrawable(R.drawable.ic_meuid_logo_dark),
               getDrawable(R.drawable.ic_meuid_text_dark)
           )
           else -> Pair(
               getDrawable(R.drawable.ic_meuid_logo_dark),
               getDrawable(R.drawable.ic_meuid_text_dark)
           )
       }
       val logoIcon = icons.first
       val textIcon = icons.second
       setCompoundDrawablesWithIntrinsicBounds(logoIcon, null, textIcon, null)
   }

   private fun getDrawable(resource: Int) = ResourcesCompat.getDrawable(resources, resource, null)

   private fun setBtnSize() {
       val hrzPadding: Int
       val vrtPadding: Int

       when (buttonSize) {
           "small" -> {
               hrzPadding = convertToDp(24)
               vrtPadding = convertToDp(8)
           }
           else -> {
               hrzPadding = convertToDp(48)
               vrtPadding = convertToDp(12)
           }
       }
       minimumHeight = convertToDp(40)
       minHeight = convertToDp(40)
       compoundDrawablePadding = convertToDp(6)

       setPadding(hrzPadding, vrtPadding, hrzPadding, vrtPadding)
   }

   private fun convertToDp(px: Int) = px * context.resources.displayMetrics.density.toInt()

   private fun setBtnClickListener() {
       setOnClickListener {
           val intent = Intent(Intent.ACTION_VIEW)
           intent.data = Uri.parse("meuid://meuid?action=MEUID_AUTHENTICATION&applicationId=$applicationId&parameters=eyJvcmlnaW4iOiJNT0JJTEVfQVBQIn0%3D")
           if (isSafeToCall(intent).not()) {
               intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.meuid")
           }
           context.startActivity(intent)
       }
   }

   private fun isSafeToCall(intent: Intent): Boolean {
       return context.packageManager.queryIntentActivities(
           intent,
           PackageManager.MATCH_DEFAULT_ONLY
       ).isNotEmpty()
   }

   companion object {
       // TODO: ("Enter your application ID here!")
       const val applicationId = "{YOUR_APP_ID}"

       // TODO: ("Set button style") -> options: "button_dark" / "button_light" / "button_light_outline"
       const val buttonStyle = "button_dark"

       // TODO: ("Set button size") -> options: "default" / "small"
       const val buttonSize = "default"
   }
}

```
#### 2.b). Na CustomView _`MeuIdButton.kt`_ criada, faça o setup dos seguintes parâmetros ao final do código:
```kotlin
      // TODO: ("Enter your application ID here!")
       const val applicationId = "{YOUR_APP_ID}"

       // TODO: ("Set button style") -> options: "button_dark" / "button_light" / "button_light_outline"
       const val buttonStyle = "button_dark"

       // TODO: ("Set button size") -> options: "default" / "small"
       const val buttonSize = "default"
```

* **Insira sua applicationID**
* **Escolha entre 3 estilos (dark/light outline/light) e 2 tamanhos (default/small) de botão:**

##### _dark_
![alt text](assets/button_dark.png "button_dark")

##### _light outline_
![alt text](assets/button_light_outline.png "button_light_outline")

##### _light_
![alt text](assets/button_light.png "button_light")

##### _dark small_
![alt text](assets/button_dark_small.png "button_dark small")



#### 2.c). Atualize também o _package path_ na classe _`MeuIdButton.kt`_ de acordo com o _package path_ do seu app:
```kotlin
// TODO: (“Updtade with your package path”)
package com.sampleloginmeuid_android
```
### 3). Inserir _Custom View_ no layout da activity/fragment

#### 3.a). Dentro do Layout da Activity que irá implementar o botão _`Login com MeuID`_, adicione o código xml abaixo:
```xml
<!-- TODO: ("Update with your MeuIdButton package path") -->
<com.sampleloginmeuid_android.MeuIdButton
   android:layout_width="wrap_content"
   android:layout_height="wrap_content" />
```
**Importante:** Atualize o trecho `com.sampleloginmeuid_android` acima com o _package path_ onde se encontra a classe _`MeuIdButton.kt`_ no seu app

### 4). Setar _**`androidManifest.xml`**_ para receber resposta do meuID
#### 4.a). Para poder receber a resposta do meuID para a solicitação realizada, insira o código abaixo no seu arquivo _**`androidManifest.xml`**_:
```xml
<activity android:name=".HomeActivity">
   <intent-filter android:label="LoginMeuID">
       <action android:name="android.intent.action.VIEW" />

       <category android:name="android.intent.category.DEFAULT" />
       <category android:name="android.intent.category.BROWSABLE" />

       <!--TODO: ("Enter your applicationID here!")-->
       <data
           android:host="authorize"
           android:scheme="meuid-{YOUR_APP_ID}" />
   </intent-filter>
</activity>
``` 

**Importante:** No código acima, substitua:
* `.HomeActivity` pela activity do seu app que irá tratar o deeplink recebido;
* `{YOUR_APP_ID}` pelo seu _**applicationId**_

### 5). Obter os parâmetros da query do deeplink enviado pelo meuID
#### 5.a) Na activity que irá receber o deeplink (configurado no passo anterior) extraia os parâmetros da query enviada pelo meuID utilizando o código abaixo dentro do método `onCreate`:
_**Kotlin**_
```kotlin
intent?.data?.apply {
   val code = getQueryParameter("code")
   val codeVerifier = getQueryParameter("code_verifier")

   // Handle parameters here...
}
```
_**Java**_
```java
Uri deeplink = getIntent().getData();
String code = deeplink.getQueryParameter("code");
String codeVerifier = deeplink.getQueryParameter("code_verifier");

// Handle parameters here...
```


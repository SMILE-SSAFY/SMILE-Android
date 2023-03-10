# βπ§‘π·μ€λ§μΌ(βSMILE)π·π§‘

![img](../wiki/image/logo.PNG)


- SSAFY 8th PJT **Team D102**β π
- νλ‘μ νΈ κΈ°κ° : `2023.01.03` ~ `2023.02.17`


<br>

# :green_book:βContents

[:one:β Specification](#one-specification)<br>
[:two:β Flow Chart](#two-flow-chart)<br>
[:three:β Package Structure](#three-package-structure)<br>
[:four:β WIKI](#four-wiki)<br>
[:five:β ν΅μ¬ κΈ°λ₯ κ΅¬ν λ°©λ² μ€λͺ](#five-ν΅μ¬-κΈ°λ₯-κ΅¬ν-λ°©λ²-μ€λͺ)<br>
[:six:β Contributor](#six-contributor)<br>




<br>

## β:one:β Specification

<table class="tg">
<tbody>
  <tr>
    <td><b>Architecture</b></td>
    <td>Clean Architecture, MVVM</td>
  </tr>
<tr>
    <td><b>Design Pattern</b></td>
<td>SSA(Single-Activity-Architecture), Singleton</td>
</tr>
<tr>
    <td><b>Jetpack Components</b></td>
<td>Navigation Component, ViewBinding, LiveData, ViewModel, Lifecycle, Room/SQLite</td>
</tr>
<tr>
    <td><b>Network</b></td>
<td>Retrofit2, OkHttp</td>
</tr>
<tr>
    <td><b>Third Party Library</b></td>
    <td>Coroutine, Glide, Image Cropper, TedPermission, Lottie, Spin-kit, Naver map API, Kakao Address API, Firebase(FCM & Hosting)</td>
</tr>
<tr>
    <td><b>Other Tool</b></td>
<td>Jira, Git, Notion, Slack, Figma, Zeplin</td>
</tr>
<tr>
    <td><b>Strategy</b></td>
<td>Jira & Git Flow</td>
</tr>
</tbody>
</table>

<br>

<br>

## :two:β Flow Chart

![img](../wiki/image/flow-chart.png)



<br>

## :three:β Package Structure

```
π¦ com.ssafy.smile
 β£ π common
 β£ π data
 β β π local
 β β β£ π database
 β β β β£ π dao
 β β β β£ π entity
 β β β π datasource
 β β β π repository
 β β π remote
 β β β π model
 β β β π datasource
 β β β π repository
 β β β π datasource
 β β β π service
 β£ π domain
 β β π model
 β β π repository
 β£ π presentation
 β β π adapter
 β β π base
 β β π view
 β β β£ π home
 β β β£ π map
 β β β£ π mypage
 β β β£ π portfolio
 β β β£ π user
 β β β£ π splash
 β β π viewmodel
 β π MainActivity.kt
 β π Application.kt
```



<br>

## :four:β WIKI
```
π Team Rules(Git, Coding Convention) λ° νμλ‘
```
- [HOME](https://lab.ssafy.com/s08-webmobile4-sub1/S08P11D102/-/wikis/Home)
  - [1. Team Rules](https://lab.ssafy.com/s08-webmobile4-sub1/S08P11D102/-/wikis/1.-Team-Rules)
  - [2. Android Coding Convention](https://lab.ssafy.com/s08-webmobile4-sub1/S08P11D102/-/wikis/2.-Android-Coding-Convention)
  - [3. Server Coding Convention]()
  - [4. νμλ‘]()



<br>

## :five:β ν΅μ¬ κΈ°λ₯ κ΅¬ν λ°©λ² μ€λͺ

```
π WIKIμ ν΅μ¬ κΈ°λ₯ κ΅¬ν μ½λ λ° λ°©λ² μ λ¦¬
```

[1. μ€νλμ]()

[2. λ‘κ·ΈμΈ]()

[3. νμκ°μ]()

[4. μ£Όμλ‘]()

[5. μκ° ν¬νΈν΄λ¦¬μ€]()

[6. κ²μκΈ λ° μ§λ]()

[7. κ²μ λ° νν°λ§]()

[8. μμ½ λ° κ²°μ ]()

[9. λμ κ΄μ¬ λ΄μ­]()

[10. μκ° μΆμ²]()




<br>

## :six:β Contributor

```
π νμ μκ°μ μ­ν  λΆλ΄
```

<table class="tg">
<tbody>
    <tr>
        <td>μ΄μ§μ€</td>
        <td>μ΄λ―Όν</td>
    </tr>
    <tr>
        <td><a href="https://https://github.com/jiy00nLee">@jiy00nLee</a></td>
        <td><a href="https://github.com/minha721">@minha721</a></td>
    </tr>
    <tr>
        <td><img src="../wiki/contributor/profile_jiyun.jpg" width="300px"/></td>
        <td><img src="../wiki/contributor/profile_minha.jpg" width="300px"/></td>
    </tr>
    <tr>
        <td>Android Dev</td>
        <td>Android Dev</td>
    </tr>
    <tr>
        <td>κ΅¬μ‘° μ€κ³, μ£Όμλ‘, μ§λ, μ£Όλ³ κ²μκΈ λͺ©λ‘, μκ° ν¬νΈν΄λ¦¬μ€/κ²μκΈ μμ±, μ μ  λ¦¬λ·° μ‘°ν, λ΄κ° μ°ν λͺ©λ‘</td>
        <td>νμκ°μ, λ‘κ·ΈμΈ/μμλ‘κ·ΈμΈ, μ£Όλ³ μκ° λͺ©λ‘, μκ° ν¬νΈν΄λ¦¬μ€/κ²μκΈ κ²μ λ° μ‘°ν, μμ½/κ²°μ , μκ° μΆμ²</td>
    </tr>
</tbody>
</table>



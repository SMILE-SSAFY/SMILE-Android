# βπ§‘π·μ€λ§μΌ(βSMILE) - Androidπ·π§‘

![μ€λ§μΌ λ‘κ³ ](https://github.com/SMILE-SSAFY/.github/blob/main/image/logo.PNG.gif)

- SSAFY 8th PJT **Team D102**β π
- νλ‘μ νΈ κΈ°κ° : `2023.01.03` ~ `2023.02.17`
- κ΅¬μ±μ : μ΄μ§μ€, μ΄λ―Όν

<br>

# :green_book:βContents

[:one:β Specification](#one-specification)<br>
[:two:β Flow Chart](#two-flow-chart)<br>
[:three:β Package Structure](#three-package-structure)<br>
[:four:β ν΅μ¬ κΈ°λ₯ κ΅¬ν λ°©λ² μ€λͺ](#four-ν΅μ¬-κΈ°λ₯-κ΅¬ν-λ°©λ²-μ€λͺ)<br>
[:five:β Contributor](#five-contributor)<br>

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
    <td>Coroutine, Glide, Image Cropper, TedPermission, Lottie, Spin-kit, Naver map API, Kakao API, BootPay API Firebase(FCM & Hosting)</td>
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

![img](https://github.com/SMILE-SSAFY/.github/blob/main/image/flow_chart.png)

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

## :four:β ν΅μ¬ κΈ°λ₯ κ΅¬ν λ°©λ² μ€λͺ

```
π WIKIμ ν΅μ¬ κΈ°λ₯ κ΅¬ν μ½λ λ° λ°©λ² μ λ¦¬
```

[0. κ΅¬μ‘° μ€κ³](https://github.com/SMILE-SSAFY/.github/wiki/4.4.0-κ΅¬μ‘°%20μ€κ³)

[1. μ€νλμ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.1-μ€νλμ)

[2. λ‘κ·ΈμΈ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.2-λ‘κ·ΈμΈ)

[3. νμκ°μ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.3-νμκ°μ)

[4. μ£Όμλ‘](https://github.com/SMILE-SSAFY/.github/wiki/4.4.4-μ£Όμλ‘)

[5. μκ° ν¬νΈν΄λ¦¬μ€](https://github.com/SMILE-SSAFY/.github/wiki/4.4.5-μκ°%20ν¬νΈν΄λ¦¬μ€)

[6. κ²μκΈ λ° μ§λ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-κ²μκΈ%20λ°%20μ§λ)

[7. κ²μ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.7-κ²μ)

[8. μμ½ λ° κ²°μ ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.8-μμ½%20λ°%20κ²°μ )

[9. λμ κ΄μ¬ λ΄μ­](https://github.com/SMILE-SSAFY/.github/wiki/4.4.9-λμ%20κ΄μ¬%20λ΄μ­)

[10. μκ° μΆμ²](https://github.com/SMILE-SSAFY/.github/wiki/4.4.10-μκ°μΆμ²)

[11. μκ° λ¦¬λ·°](https://github.com/SMILE-SSAFY/.github/wiki/4.4.11-μκ°%20λ¦¬λ·°)

[12. μ΄λ―Έμ§ μ νΈ](https://github.com/SMILE-SSAFY/.github/wiki/4.4.12-μ΄λ―Έμ§%20μ νΈ)

[13. κΆν μ²λ¦¬](https://github.com/SMILE-SSAFY/.github/wiki/4.4.13-κΆν%20μ²λ¦¬)
        

<br>

## :five:β Contributor

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
        <td><a href="https://github.com/jiy00nLee">@jiy00nLee</a></td>
        <td><a href="https://github.com/minha721">@minha721</a></td>
    </tr>
    <tr>
        <td><img src="https://github.com/SMILE-SSAFY/.github/blob/main/image/profile_jiyun.jpeg" width="300px"/></td>
        <td><img src="https://github.com/SMILE-SSAFY/.github/blob/main/image/profile_minha.jpeg" width="300px"/></td>
    </tr>
    <tr>
        <td>Android Dev</td>
        <td>Android Dev</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.0-κ΅¬μ‘°%20μ€κ³">κ΅¬μ‘° μ€κ³</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.4-μ£Όμλ‘">μ£Όμλ‘</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.5-μκ°%20ν¬νΈν΄λ¦¬μ€">μκ° ν¬νΈν΄λ¦¬μ€ λ±λ‘/μμ </a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-κ²μκΈ%20λ°%20μ§λ">μ£Όλ³ κ²μκΈ λͺ©λ‘(μ§λ)</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-κ²μκΈ%20λ°%20μ§λ">κ²μκΈ λ±λ‘/μμ </a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.9-λμ%20κ΄μ¬%20λ΄μ­">λμ κ΄μ¬ λ΄μ­</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.11-μκ°%20λ¦¬λ·°">μκ° λ¦¬λ·° λ±λ‘/μ‘°ν</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.12-μ΄λ―Έμ§%20μ νΈ">μ΄λ―Έμ§ μ νΈ(ImageUtils)</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.13-κΆν%20μ²λ¦¬">κΆν μ²λ¦¬(PermissionUtils)</a><br></td>
        <td>μ€νλμ<br>
        νμκ°μ<br>
        λ‘κ·ΈμΈ<br>
        μ£Όλ³ μκ° λͺ©λ‘<br>
        μκ° ν¬νΈν΄λ¦¬μ€ μ‘°ν<br>
        κ²μκΈ μ‘°ν<br>
        κ²μ(μκ°, κ²μκΈ)<br>
        μμ½ λ° κ²°μ <br>
        μκ° μΆμ²</td>
    </tr>
</tbody>
</table>

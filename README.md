# ​🧡📷스마일(​SMILE) - Android📷🧡

![스마일 로고](https://github.com/SMILE-SSAFY/.github/blob/main/image/logo.PNG.gif)

- SSAFY 8th PJT **Team D102**​ 🌞
- 프로젝트 기간 : `2023.01.03` ~ `2023.02.17`
- 구성원 : 이지윤, 이민하

<br>

# :green_book:​Contents

[:one:​ Specification](#one-specification)<br>
[:two:​ Flow Chart](#two-flow-chart)<br>
[:three:​ Package Structure](#three-package-structure)<br>
[:four:​ 핵심 기능 구현 방법 설명](#four-핵심-기능-구현-방법-설명)<br>
[:five:​ Contributor](#five-contributor)<br>

<br>

## ​:one:​ Specification

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

## :two:​ Flow Chart

![img](https://github.com/SMILE-SSAFY/.github/blob/main/image/flow_chart.png)

<br>

## :three:​ Package Structure

```
📦 com.ssafy.smile
 ┣ 📂 common
 ┣ 📂 data
 ┃ ┗ 📂 local
 ┃ ┃ ┣ 📂 database
 ┃ ┃ ┃ ┣ 📂 dao
 ┃ ┃ ┃ ┣ 📂 entity
 ┃ ┃ ┗ 📂 datasource
 ┃ ┃ ┗ 📂 repository
 ┃ ┗ 📂 remote
 ┃ ┃ ┗ 📂 model
 ┃ ┃ ┗ 📂 datasource
 ┃ ┃ ┗ 📂 repository
 ┃ ┃ ┗ 📂 datasource
 ┃ ┃ ┗ 📂 service
 ┣ 📂 domain
 ┃ ┗ 📂 model
 ┃ ┗ 📂 repository
 ┣ 📂 presentation
 ┃ ┗ 📂 adapter
 ┃ ┗ 📂 base
 ┃ ┗ 📂 view
 ┃ ┃ ┣ 📂 home
 ┃ ┃ ┣ 📂 map
 ┃ ┃ ┣ 📂 mypage
 ┃ ┃ ┣ 📂 portfolio
 ┃ ┃ ┣ 📂 user
 ┃ ┃ ┣ 📂 splash
 ┃ ┗ 📂 viewmodel
 ┗ 📜 MainActivity.kt
 ┗ 📜 Application.kt
```

<br>

## :four:​ 핵심 기능 구현 방법 설명

```
👉 WIKI에 핵심 기능 구현 코드 및 방법 정리
```

[0. 구조 설계](https://github.com/SMILE-SSAFY/.github/wiki/4.4.0-구조%20설계)

[1. 스플래시](https://github.com/SMILE-SSAFY/.github/wiki/4.4.1-스플래시)

[2. 로그인](https://github.com/SMILE-SSAFY/.github/wiki/4.4.2-로그인)

[3. 회원가입](https://github.com/SMILE-SSAFY/.github/wiki/4.4.3-회원가입)

[4. 주소록](https://github.com/SMILE-SSAFY/.github/wiki/4.4.4-주소록)

[5. 작가 포트폴리오](https://github.com/SMILE-SSAFY/.github/wiki/4.4.5-작가%20포트폴리오)

[6. 게시글 및 지도](https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-게시글%20및%20지도)

[7. 검색](https://github.com/SMILE-SSAFY/.github/wiki/4.4.7-검색)

[8. 예약 및 결제](https://github.com/SMILE-SSAFY/.github/wiki/4.4.8-예약%20및%20결제)

[9. 나의 관심 내역](https://github.com/SMILE-SSAFY/.github/wiki/4.4.9-나의%20관심%20내역)

[10. 작가 추천](https://github.com/SMILE-SSAFY/.github/wiki/4.4.10-작가추천)

[11. 작가 리뷰](https://github.com/SMILE-SSAFY/.github/wiki/4.4.11-작가%20리뷰)

[12. 이미지 유틸](https://github.com/SMILE-SSAFY/.github/wiki/4.4.12-이미지%20유틸)

[13. 권한 처리](https://github.com/SMILE-SSAFY/.github/wiki/4.4.13-권한%20처리)
        

<br>

## :five:​ Contributor

```
👉 팀원 소개와 역할 분담
```

<table class="tg">
<tbody>
    <tr>
        <td>이지윤</td>
        <td>이민하</td>
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
        <td><a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.0-구조%20설계">구조 설계</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.4-주소록">주소록</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.5-작가%20포트폴리오">작가 포트폴리오 등록/수정</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-게시글%20및%20지도">주변 게시글 목록(지도)</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.6-게시글%20및%20지도">게시글 등록/수정</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.9-나의%20관심%20내역">나의 관심 내역</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.11-작가%20리뷰">작가 리뷰 등록/수정/조회</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.12-이미지%20유틸">이미지 유틸(ImageUtils)</a><br>
        <a href="https://github.com/SMILE-SSAFY/.github/wiki/4.4.13-권한%20처리">권한 처리(PermissionUtils)</a><br></td>
        <td>스플래시<br>
        회원가입<br>
        로그인<br>
        주변 작가 목록<br>
        작가 포트폴리오 조회<br>
        게시글 조회<br>
        검색(작가, 게시글)<br>
        예약 및 결제<br>
        작가 추천</td>
        커스텀 뷰</td>
    </tr>
</tbody>
</table>

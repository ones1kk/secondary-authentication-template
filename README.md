# Secondary-authentication-template
> 1st and 2nd authentication login. 

> Multilingual message, label handling.
> 
<br>
<div style="display=inline;">
  <a href="https://spring.io/blog/2022/05/19/spring-boot-2-7-5-available-now">
    <img alt="spring" src="https://img.shields.io/badge/spring--boot-2.7.5-green?style=flat-square">
  </a>
  <a href="https://gradle.org/">
    <img alt="gradle" src="https://img.shields.io/badge/build-gradle-skyblue?style=flat-square">
  </a>
  <a href="https://www.azul.com/downloads/?version=java-11-lts&package=jdk">
    <img alt="jdk11" src="https://img.shields.io/badge/jdk-11-orange?style=flat-square">
  </a>
  <a href="http://www.h2database.com/html/download.html">
    <img alt="h2" src="https://img.shields.io/badge/DB-h2-white?style=flat-square">
  </a>
</div>
<br>

## Overview

* Two Authentication Required for Login.
* When the application runs, it reads all the contents stored in the Label, Message table and creates .properties files set for each locale in the resources subdirectory.
* Handle Exception messages using MessageSource.
<#import "/spring.ftl" as s>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<title><#if title??>${title} | </#if>Annanjin</title>
<!--Bootstrap jQuery-->
<script type="text/javascript" src="<@s.url '/vendor/jquery/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<@s.url '/vendor/bootstrap/popper.min.js'/>"></script>
<script type="text/javascript" src="<@s.url '/vendor/bootstrap/bootstrap.min.js'/>"></script>
<!--Bootstrap css-->
<link rel="stylesheet" type="text/css" href="<@s.url '/vendor/bootstrap/bootstrap.min.css'/>">
<!--font awesome-->
<link rel="stylesheet" type="text/css" href="<@s.url '/vendor/fontawesome-5.9.0/css/all.min.css'/>">

<link rel="stylesheet" href="<@s.url '/client/style.css'/>">

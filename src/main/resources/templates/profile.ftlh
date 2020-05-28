<#import "parts/common.ftlh" as c>

<@c.page>
<h3>${userChannel.username}</h3>
<#if isCurrentUser>
    <a class="btn btn-dark" href="">Edit Profile</a>
</#if>

</@c.page>
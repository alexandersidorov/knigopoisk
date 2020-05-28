<#import "parts/common.ftlh" as c>

<@c.page>

<div class = "form-group row">
    <div class = "col-sm-6">
        <div class="card" style = "width:10rem; height:10rem">

            <#if userChannel.avatar??>
                <img src = "/img/${userChannel.avatar}" class = "card-img-top">
            <#else>
                <img src = "/img/default_avatar.jpg" class = "card-img-top">
            </#if>

            <div class = "card-body">
                <h3 class = "card-text">${userChannel.username}</h3>
            </div>
        </div>
    </div>
    <div class = "col-sm-6">
        <#if isCurrentUser??>
            <a href="#" class="btn btn-dark">Edit Profile</a>
        </#if>
    </div>
</div>



</@c.page>
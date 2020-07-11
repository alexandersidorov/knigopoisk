<#import "parts/common.ftlh" as c>

<@c.page>
<#if message??>
${message}
</#if>

<div class = "form-group row">
    <div class = "col-sm-2">
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

    <div class = "col-sm-5">

        <div class = "form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <label class="col-sm-5 col-form-label">${userChannel.email}</label>
        </div>

        <#if isCurrentUser>
            <div class = "form-group row"><a href="/profile/${userChannel.id}/edit" class="btn btn-dark">Edit Profile</a></div>
        <#else>
            <div class = "form-group row">
                <#if isSubscriber>
                    <a class="btn btn-danger" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
                <#else>
                    <a class="btn btn-success" href="/user/subscribe/${userChannel.id}">Subscribe</a>
                </#if>
            </div>
        </#if>

    </div>

</div>



</@c.page>
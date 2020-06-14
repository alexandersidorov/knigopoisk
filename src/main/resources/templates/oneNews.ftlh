<#import "parts/common.ftlh" as c>

<style>
    .img-style{
        width:300px;
        height:300px;
        background-position: center;
        -webkit-background-size: cover;
        background-size: cover;
        margin: 0 auto;
    }
</style>

<@c.page>

<div class="card text-center my-3">

    <div class = "card-header text-muted">
        <h5 class="card-title">${news.head}</h5>
    </div>

    <#if news.headPic??>
        <div class = "card mt-3 mb-3 ml-5 mr-5">
            <img src = "/img/${news.headPic}" class = "card-img-top">
        </div>
    </#if>

    <div class="card-body">
        <p class = "card-text">${news.text}</p>
        <#list news.paragraphs as paragraph>
            <#if paragraph.pic??>
                <div class = "card mt-3 mb-3 ml-5 mr-5">
                    <img src = "/img/${paragraph.pic}" class = "card-img-top">
                </div>
                <p class="card-text">${paragraph.text}</p>
            </#if>
        </#list>
    </div>

    <div class = "card-footer text-muted">
        <a href = "/">Back</a>
        <#if isUsersNews == true>
            <td><a href = "/editNews/${news.id}" class = "btn btn-dark">edit</a></td>
            <td><a href = "/deleteNews/${news.id}" class = "btn btn-dark">delete</a></td>
        </#if>
    </div>

</div>

</@c.page>
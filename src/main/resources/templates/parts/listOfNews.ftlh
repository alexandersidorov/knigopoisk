<#list allNews as news>
        <div class="card text-center my-3" style = "height: 20rm;">

            <#if news.headPic??>
                <img src = "/img/${news.headPic}" class = "card-img-top">
            </#if>
            <div class="card-body">
                <h5 class="card-title">${news.head}</h5>
                <p class="card-text">${news.text}</p>
            </div>
            <div class = "card-footer text-muted">
                <a href = "/allNews/${news.id}">See more...</a>
            </div>

        </div>
            <#else>
            No News.
</#list>


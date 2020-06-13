<#list allNews as news>
        <div class="card text-center my-3" style = "height: 20rm;">

            <div class = "card-header text-muted">
                <h5 class="card-title">${news.head}</h5>
            </div>

            <#if news.headPic??>
                <div class = "card mt-3 mb-3 ml-5 mr-5">
                    <img src = "/img/${news.headPic}" class = "card-img-top">
                </div>
            </#if>

            <div class="card-body">
                <p class="card-text">${news.text}</p>
            </div>

            <div class = "card-footer text-muted">
                <a href = "/allNews/${news.id}">See more...</a>
            </div>

        </div>
            <#else>
            No News.
</#list>


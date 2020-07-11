<style>
    .img-style{
        width:100px;
        height:100px;
        background-position: center;
        -webkit-background-size: cover;
        background-size: cover;
        margin: 0 auto;
    }
</style>

<table class = "table table-bordered">
    <thead class = "thead-dark">
        <tr>
            <#if isAdmin || isModer>
                <th>Id</th>
            </#if>

            <th>Avatar</th>
            <th>Name</th>

            <#if isAdmin == true>
                <th>Role</th>
                <th></th>
            </#if>
            <#if isModer == true><th>Blocked</th></#if>

        </tr>
    </thead>

    <tbody>
        <#list users as user>
            <tr>
                <#if isAdmin || isModer>
                    <td>${user.id}</td>
                </#if>

                <#if user.avatar??>
                    <td><div class = "img-style" style="background-image: url(/img/${user.avatar})"></div></td>
                <#else>
                    <td><div class = "img-style" style="background-image: url(/img/default_avatar.jpg)"></div></td>
                </#if>

                <td><a href="/profile/${user.id}">${user.username}</a></td>

                <#if isAdmin == true>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href = "/user/${user.id}" class = "btn btn-dark">edit</a></td>
                <#else>
                    <#if isModer == true>
                        <td><a href = "#" class = "btn btn-success">User not blocked</a></td>
                    </#if>
                </#if>

            </tr>

        </#list>
    </tbody>
</table>

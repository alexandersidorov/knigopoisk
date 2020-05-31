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
            <th>Id</th>
            <th>Avatar</th>
            <th>Name</th>
            <#if isAdmin == true><th>Role</th></#if>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <#list users as user>
            <tr>
                <td>${user.id}</td>

                <td><div class = "img-style" style="background-image: url(/img/${user.avatar})"></div></td>

                <td>${user.username}</td>

                <#if isAdmin == true>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href = "/user/${user.id}" class = "btn btn-dark">edit</a></td>
                <#else>
                    <td><a href = "#" class = "btn btn-success">User not blocked</a></td>
                </#if>

            </tr>
        </#list>
    </tbody>
</table>

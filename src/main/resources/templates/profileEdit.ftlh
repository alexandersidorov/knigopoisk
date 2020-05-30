<#import "parts/common.ftlh" as c>
<@c.page>
<form method = "post" enctype = "multipart/form-data">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Name:</label>
        <div class="col-sm-6">
            <input type="text" name="username" class="form-control" placeholder="Username" value = "${user.username}"/>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
    </div>

    <div class = "form-group row">
        <label class="col-sm-2 col-form-label">Avatar</label>
        <div class = "col-sm-6">
        <div class = "custom-file">
            <input type = "file" name = "avatar" id = "customFile">
            <label class = "custom-file-label" for="customFile">Avatar</label>
        </div>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" placeholder="Email" value="${user.email!''}"/>
        </div>
    </div>

    <button type="submit" class="btn btn-dark">Save</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</form>
</@c.page>
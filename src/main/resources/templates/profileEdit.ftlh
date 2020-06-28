<#import "parts/common.ftlh" as c>
<@c.page>
<form method = "post" enctype = "multipart/form-data">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Name:</label>
        <div class="col-sm-6">
            <input type="text" name="username" value="${user.username}"
            class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="User name" />
            <#if usernameError??>
            <div class="invalid-feedback">
            ${usernameError}
            </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
            class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Password" />
            <#if passwordError??>
            <div class="invalid-feedback">
            ${passwordError}
            </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2"
            class="form-control ${(password2Error??)?string('is-invalid', '')}"placeholder="Retype password" />
            <#if password2Error??>
            <div class="invalid-feedback">
            ${password2Error}
            </div>
            </#if>
        </div>
    </div>


    <div class = "form-group row">
        <label class="col-sm-2 col-form-label">Avatar</label>
        <div class = "col-sm-6">
        <div class = "custom-file">
            <input type = "file" name = "MainPic" id = "customFile">
            <label class = "custom-file-label" for="customFile">Avatar</label>
        </div>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" value="${user.email}"
            class="form-control ${(emailError??)?string('is-invalid', '')}"placeholder="some@some.com" />
            <#if emailError??>
            <div class="invalid-feedback">
            ${emailError}
            </div>
            </#if>
        </div>
    </div>

    <button type="submit" class="btn btn-dark">Save</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</form>
</@c.page>
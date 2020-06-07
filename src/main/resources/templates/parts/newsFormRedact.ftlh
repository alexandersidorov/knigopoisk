
<form method = "post" enctype = "multipart/form-data">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Head:</label>
        <div class="col-sm-6">
            <input type="text" name="head" class="form-control" placeholder="head of news" />
        </div>
    </div>

    <div class = "form-group row">
            <label class="col-sm-2 col-form-label">Head Picture</label>
            <div class = "col-sm-6">
            <div class = "custom-file">
                <input type = "file" name = "headPic" id = "customFile">
                <label class = "custom-file-label" for="customFile">Head Picture</label>
            </div>
            </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Text:</label>
        <div class="col-sm-6">
            <textarea class="form-control" name = "text" aria-label="Text"></textarea>
        </div>
    </div>

    <button type="submit" class="btn btn-dark">Save</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</form>

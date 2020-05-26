<div class = "form-row">
<div class = "form-group col">
    <form method = get action =  "/main" class = "form-inline">
        <input type = "text" name = "filter" class = "form-control col-sm-6" value = "${filter?ifExists}" placeholder = "Search by name" />
        <button type = "submit" class = "btn btn-dark ml-2" >Search</button>
    </form>
</div>
</div>

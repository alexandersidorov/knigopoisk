
<form method = "post" enctype = "multipart/form-data">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Head:</label>
        <div class="col-sm-6">
            <input type="text" name="head" class="form-control" placeholder="head of news" <#if editNews??>value="${editNews.head}"</#if>   />
        </div>
    </div>

    <div class = "form-group row">
        <label class="col-sm-2 col-form-label">Head Picture</label>
        <div class = "col-sm-6">
        <div class = "custom-file">
            <input type = "file" name = "headPic" id = "headPicFile">
            <label class = "custom-file-label" for="headPicFile">Head Picture</label>
        </div>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Text:</label>
        <div class="col-sm-6">
            <textarea class="form-control" name = "text" aria-label="Text" > <#if editNews??>${editNews.text}</#if> </textarea>
        </div>
    </div>

    <#if editNews??>

        <#list editNews.paragraphs as paragraph>

            <a class="btn btn-dark" data-toggle="collapse" href="#collapseParagraph${paragraph.num}" role="button"
             aria-expanded="false" aria-controls="collapseParagraph${paragraph.num}">
                Paragraph ${paragraph.num}
            </a>
            <div class="collapse show" id="collapseParagraph${paragraph.num}">

                <div class = "form-group row">
                    <label class="col-sm-2 col-form-label">Paragraph${paragraph.num} Picture</label>
                    <div class = "col-sm-6">
                    <div class = "custom-file">
                        <input type = "file" name = "ParPic${paragraph.num}" id = "parPicFile${paragraph.num}">
                        <label class = "custom-file-label" for="parPicFile${paragraph.num}">Paragraph${paragraph.num} Picture</label>
                    </div>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Text:</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" name = "ParText${paragraph.num}" aria-label="Text" > ${paragraph.text} </textarea>
                    </div>
                </div>

            </div>

        </#list>

    <#else>

        <a class="btn btn-dark" data-toggle="collapse" href="#collapseParagraph1" role="button" aria-expanded="false" aria-controls="collapseParagraph1">
            Paragraph 1
        </a>
        <div class="collapse" id="collapseParagraph1">
            <div class = "form-group row">
                <label class="col-sm-2 col-form-label">Paragraph1 Picture</label>
                <div class = "col-sm-6">
                <div class = "custom-file">
                    <input type = "file" name = "ParPic1" id = "parPicFile1">
                    <label class = "custom-file-label" for="parPicFile1">Paragraph1 Picture</label>
                </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Text:</label>
                <div class="col-sm-6">
                    <textarea class="form-control" name = "ParText1" aria-label="Text"></textarea>
                </div>
            </div>
        </div>

        <a class="btn btn-dark" data-toggle="collapse" href="#collapseParagraph2" role="button" aria-expanded="false" aria-controls="collapseParagraph2">
            Paragraph 2
        </a>
        <div class="collapse" id="collapseParagraph2">
            <div class = "form-group row">
                <label class="col-sm-2 col-form-label">Paragraph1 Picture</label>
                <div class = "col-sm-6">
                <div class = "custom-file">
                    <input type = "file" name = "ParPic2" id = "parPicFile2">
                    <label class = "custom-file-label" for="parPicFile2">Paragraph1 Picture</label>
                </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Text:</label>
                <div class="col-sm-6">
                    <textarea class="form-control" name = "ParText2" aria-label="Text"></textarea>
                </div>
            </div>
        </div>

    </#if>

    <div class = "mt-3">
        <button type="submit" class="btn btn-dark">Save</button>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</form>

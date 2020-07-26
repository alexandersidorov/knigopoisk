<#import "parts/common.ftlh" as c>

<@c.page>

<#if message??>
    <div class="alert alert-danger" role="alert">
        ${message}
    </div>
</#if>

<h3>Add news:</h3>
<#include "parts/newsFormRedact.ftlh" />

</@c.page>

<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-success">
  <a class="navbar-brand" href="/">KnigoPoisk</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">

      <li class="nav-item">
        <a class="nav-link" href="/">Home</a>
      </li>

      <#if user??>
        <li class="nav-item">
            <a class="nav-link" href="/profile/${currentUserId}">My Profile</a>
        </li>
      </#if>

      <#if isAdmin>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ADMIN
            </a>
            <div class="dropdown-menu bg-success" aria-labelledby="navbarDropdown">
                <a class="dropdown-item bg-success" href="/user">User List</a>
            </div>
        </li>
      </#if>

      <#if isModerator>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                MODERATOR
            </a>
            <div class="dropdown-menu bg-success" aria-labelledby="navbarDropdown">
                <a class="dropdown-item bg-success" href="/control-user">Control Users</a>
                <a class="dropdown-item bg-success" href="/myAddNews">My Added News</a>
                <a class="dropdown-item bg-success" href="/addNews">Add News</a>
            </div>
        </li>
      </#if>

    </ul>

    <div class = "navbar-text mr-3">${name}</div>
    <@l.logout />

  </div>

</nav>
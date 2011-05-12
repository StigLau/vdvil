<%@ page import="vdvil.server.ImageDescription" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
    <img src="${fieldValue(bean: imageDescriptionInstance, field: "src")}" alt="${fieldValue(bean: imageDescriptionInstance, field: "text")}" />
    </body>
</html>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="resource" required="true"%>

<c:url var="url-css" value="/styles/${resource}.css" />
<link rel="stylesheet" type="text/css" href="/styles/${resource}.css" />
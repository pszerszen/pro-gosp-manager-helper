<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="resource" required="true"%>

<c:url var="url-js" value="/js/${resource}.js" />
<script type="text/javascript" src="/js/${resource}.js"></script>
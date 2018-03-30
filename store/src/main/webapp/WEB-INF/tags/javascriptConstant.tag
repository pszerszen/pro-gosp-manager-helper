<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sjs" uri="/WEB-INF/storeTagLibs.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="var" required="true"%>
<%@ attribute name="value" required="true"%>

<c:set var="getter" value="get${var}" />
<script>
var Constants = ((function(out) {

    out["${getter}"] = (function() { return "${sjs:escapeJs(value)}"; });

    return out;
})((Constants || {})));
</script>

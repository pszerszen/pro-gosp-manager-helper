<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="labelText" required="false" %>
<%@ attribute name="labelName" required="false" %>
<%@ attribute name="extraClass" required="false" %>

<div class="form-group ${extraClass}">
    <label class="control-label col-sm-2" for="${labelName}">
        <c:out value="${labelText}"/>
    </label>
    <div class="col-sm-10 control-group">
        <jsp:doBody/>
    </div>
</div>
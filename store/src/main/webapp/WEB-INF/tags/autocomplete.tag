<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="labelName" required="true" %>
<%@attribute name="labelText" required="true" %>
<%@attribute name="path" required="true" %>
<%@attribute name="url" %>
<%@attribute name="maxResults" %>

<c:if test="${empty maxResults}">
    <c:set var="maxResults" value="10"/>
</c:if>

<t:formRow labelName="${labelName}" labelText="${labelText}">
    <c:url var="serviceUrl" value="${url}"/>
    <select id="${labelName}" class="selectize" placeholder="Start Typing to see options..." data-max-results="${maxResults}" data-url="${serviceUrl}">
    </select>
    <f:hidden path="${path}.id" cssClass="object-id"/>
    <f:hidden path="${path}.name" cssClass="object-name"/>

</t:formRow>
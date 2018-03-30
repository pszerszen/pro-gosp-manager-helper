<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@attribute name="withTime" required="false" type="java.lang.Boolean"%>
<%@attribute name="labelName" required="true"%>
<%@attribute name="labelText" required="true"%>

<c:set var="cssClass" value="${withTime ? 'datetimepicker' : 'datepicker'}" />

<t:formRow labelName="${labelName}" labelText="${labelText}">
	<f:input path="${labelName}" cssClass="form-control ${cssClass}" />
</t:formRow>
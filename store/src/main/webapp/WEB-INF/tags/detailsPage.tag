<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="formContent" required="true" fragment="true" %>

<%@ attribute name="activePage" required="true"
	type="com.manager.store.model.PageSection"%>
<%@ attribute name="idPrefix" required="true" %>

<%@ attribute name="additionalJs" fragment="true" required="false" %>
<%@ attribute name="additionalCss" fragment="true" required="false" %>

<t:baseWrapper activePage="${activePage}">
    <jsp:attribute name="additionalJs">
        <t:js resource="submit"/>
        <jsp:invoke fragment="additionalJs"/>
    </jsp:attribute>
    <jsp:attribute name="additionalCss">
        <jsp:invoke fragment="additionalCss"/>
    </jsp:attribute>

    <jsp:body>
        <t:messageBox type="information"/>
        <t:messageBox type="error"/>
        <f:form id="${idPrefix}-details-form" modelAttribute="model" cssClass="form form-horizontal ketchup-form">
            <t:formRow></t:formRow>
            <f:hidden path="id"/>

            <jsp:invoke fragment="formContent"/>

            <div class="form-group">
                <div class="col-sm-6">
                    <c:url var="backUrl" value="/${idPrefix}"/>
                    <a href="${backUrl}" class="btn btn-default">BACK</a>
                </div>
                <div class="col-sm-6">
                    <c:set var="newInstance" value="${empty model.id}"/>
                    <button id="${newInstance ? 'save' : 'edit'}-button" type="button" class="btn btn-default">${newInstance ? "CREATE" : "EDIT"}</button>
                </div>
            </div>
        </f:form>
    </jsp:body>
</t:baseWrapper>
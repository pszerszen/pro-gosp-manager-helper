<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:baseWrapper activePage="CHANGE_PASSWORD">
	<jsp:attribute name="additionalCss">
		<t:css resource="login"/>
	</jsp:attribute>
	<jsp:attribute name="additionalJs">
		<t:js resource="changePassword"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container change-password-container">
            <t:messageBox type="information"/>
            <t:messageBox type="error"/>
            <div class="starter-template">
                <p class="lead">You can change your password here. After that you will have log in with new credentials.</p>
            </div>
            <c:url var="changePasswordUrl" value="/changePassword"/>
            <f:form cssClass="form-signin" action="${changePasswordUrl}" id="changePassword" modelAttribute="passwordToken" method="post">

                <f:password path="oldPassword" cssClass="form-control" placeholder="Old password" autofocus="autofocus"/>

                <f:password path="password" id="password-field" cssClass="form-control" placeholder="Password"
                            data-validate="validate(required,strongPassword,rangelength(8,20))"/>

                <f:password path="passwordConfirmation" cssClass="form-control" placeholder="Confirm password"
                            data-validate="validate(matchPassword(password-field))"/>

                <button id="change-password-button" class="btn btn-lg btn-primary btn-block" type="submit">Change Password</button>

            </f:form>
        </div>
    </jsp:body>
</t:baseWrapper>
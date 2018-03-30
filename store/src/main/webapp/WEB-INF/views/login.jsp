<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="iso-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<t:headerJS />
<t:css resource="login" />
<title>${title}</title>
</head>
<body>
	<div class="container login-container">
		<h1 class="page-header">
			<a href="<c:url value='/welcome' />"><span
				class="label label-primary form-control">Store Manager</span></a>
		</h1>
		<c:if test="${not empty error}">
			<p>
				<c:out value="${error}" />
			</p>
		</c:if>
		<c:url var="spring_login" value="/j_spring_security_check" />
		<f:form cssClass="form-signin" action="${spring_login}" id="login"
			method="post">

			<input class="form-control" type="text" name="mail" id="mail"
				placeholder="LOGIN" required="required" autofocus="autofocus" />

			<input class="form-control" type="password" id="password"
				name="password" placeholder="PASSWORD" required="required" />

			<button class="btn btn-lg btn-primary btn-block" type="submit">ENTER
				APPLICATION</button>

		</f:form>
	</div>

	<t:commonJS />
</body>
</html>
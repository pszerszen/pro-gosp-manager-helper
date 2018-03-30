<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@ attribute name="activePage" required="true"
	type="com.manager.store.model.PageSection"%>

<%@ attribute name="additionalJs" fragment="true" required="false"%>
<%@ attribute name="additionalCss" fragment="true" required="false"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<t:headerJS />
<jsp:invoke fragment="additionalCss" />
<title>${title}</title>
</head>

<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container" style="width: auto;">
			<div class="navbar-header">
				<c:url var="baseUrl" value="/login_processor" />
				<a class="navbar-brand" href="${baseUrl}">Store Manager</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav navbar-right">
					<c:forEach var="title" items="${pageTitles}">
						<li class="${title eq activePage ? 'active' : ''}"><a href="${title.link}"><c:out value="${title.name}" /></a></li>
					</c:forEach>

				</ul>
			</div>
		</div>
	</nav>
	<div class="container">

		<jsp:doBody />

	</div>
	<t:commonJS />
	<jsp:invoke fragment="additionalJs" />
</body>
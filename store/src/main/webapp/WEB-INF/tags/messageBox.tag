<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="type" required="true" %>

<div id="${type}-container" class="alert alert-${type eq 'error' ? 'danger' : 'success'} ${type}-container message-box" role="alert" style="display: none;">
    <button type="button" class="close close-message-box" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <div class="heading">
        <h3></h3>
    </div>
    <div class="content">
    </div>
</div>
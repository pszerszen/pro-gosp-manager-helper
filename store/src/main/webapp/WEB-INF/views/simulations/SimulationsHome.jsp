<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:baseWrapper activePage="SIMULATION">
	<jsp:attribute name="additionalCss">
        <t:css resource="simulations" />
    </jsp:attribute>
	<jsp:body>
        <div id="prototype" class="hidden">
            <t:simulationSection />
        </div>
        <div id="simulations" class="container">
            <t:simulationSection />
        </div>
    </jsp:body>
</t:baseWrapper>

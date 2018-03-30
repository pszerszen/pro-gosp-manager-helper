<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:baseWrapper activePage="ANALYSIS">
	<jsp:attribute name="additionalCss">
        <t:css resource="simulations"/>
        <link href="/js/charts/c3/c3.css" rel="stylesheet" type="text/css">
    </jsp:attribute>
    <jsp:attribute name="additionalJs">
        <script type="text/javascript" src="http://gabelerner.github.io/canvg/rgbcolor.js"></script>
        <script type="text/javascript" src="http://gabelerner.github.io/canvg/StackBlur.js"></script>
        <script type="text/javascript" src="http://gabelerner.github.io/canvg/canvg.js"></script>
        <t:js resource="charts/d3/d3.min"/>
        <t:js resource="charts/c3/c3.min"/>
        <t:js resource="analysis"/>
    </jsp:attribute>
    <jsp:body>
        <div id="image-dialog" class="hidden" title="The chart as an image."><canvas id="canvas"></canvas></div>
        <div id="prototype" class="hidden">
            <t:analisysSection chartId="proto"/>
        </div>
        <div id="simulations" class="container">
            <t:analisysSection chartId="section0"/>
        </div>
    </jsp:body>
</t:baseWrapper>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<div class="row section-row">
	<div class="col-sm-7">
		<textarea class="form-control left-side"></textarea>
	</div>
	<div class="col-sm-5">
		<div class="row small-row">
			<div class="col-sm-4">
				<label class="control-label">MODE</label>
			</div>
			<div class="col-sm-8">
				<select class="form-control">
					<%--OPTIONS--%>
				</select>
			</div>
		</div>
		<div class="row small-row">
			<div class="col-sm-4">
				<button class="btn btn-default btn-block">ADD Simulation</button>
			</div>
			<div class="col-sm-4">
				<button class="btn btn-default btn-block">SAVE Results</button>
			</div>
			<div class="col-sm-4">
				<button class="btn btn-default btn-block">REMOVE Simulation</button>
			</div>
		</div>
		<div class="row small-row">
			<div class="col-sm-12">
				<textarea class="form-control right-side"></textarea>
			</div>
		</div>
	</div>
</div>
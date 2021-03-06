<%--
 * list.jsp
<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="container">
<div class="row"> 
<div class="col-sm-12 col-md-12 col-lg-12">
<fieldset class="col-md-6 col-md-offset-3">

<form:form action="request/member/edit.do" modelAttribute="request"  class="form-horizontal" method="post">
	<div class="form-group ">
	
	<form:hidden path="id"/>
	<acme:select items="${parades}" itemLabel="title" code="request.parade" path="parade"/>
	<acme:textbox type="number" min="0" code="request.row" path="row"/>
	<acme:textbox type="number" min="0" code="request.column" path="column"/>
	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="/request/member/list.do" code="position.cancel"/>

</div>	
</form:form>
</fieldset>

</div>	
</div>	
</div>
	

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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="container">
<div class="row"> 
<div class="col-sm-12 col-md-12 col-lg-12">
<fieldset class="col-md-6 col-md-offset-3">

<form:form action="finder/member/view.do" modelAttribute="finder"  class="form-horizontal" method="post">
	<div class="form-group ">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<acme:textbox code="finder.keyword" path="keyword"/>
	<acme:textbox type="date" code="finder.minDate" path="minDate"/>
	<acme:textbox type="date" code="finder.maxDate" path="maxDate"/>
	<acme:select items="${areas}" itemLabel="name" code="finder.areas" path="area"/>
	<acme:submit name="save" code="finder.search"/>
	<acme:submit name="clean" code="finder.clean"/>

</div>	
</form:form>
</fieldset>
<div class="table-responsive">	

<display:table pagesize="5" name="parades" id="parade"
	requestURI="${requestURI}">
	<display:column titleKey="parade.title"  property="title" />
	<display:column titleKey="parade.description" property="description" />
	<display:column titleKey="parade.moment" property="moment" />
	<display:column titleKey="parade.ticker" property="ticker" />
	<display:column titleKey="parade.mode" property="mode" />

</display:table>

</div>
</div>	
</div>	
</div>	
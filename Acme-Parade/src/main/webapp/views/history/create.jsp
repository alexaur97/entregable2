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



<form:form action="history/brotherhood/edit.do"
	modelAttribute="historyCreateForm" class="form-horizontal"
	method="POST">
	
	<p><spring:message code="history.text.inception"/></p>
	
	<acme:textbox code="inceptionRecord.title" path="inceptionRecordTitle" />
	<acme:textarea code="inceptionRecord.description" path="inceptionRecordDescription" />
	<br/>
	<spring:message code="history.text.photo" />
	<acme:textarea code="inceptionRecord.pictures" path="inceptionRecordPictures" />
	
	<acme:submit name="save" code="history.save"/>
	
</form:form>


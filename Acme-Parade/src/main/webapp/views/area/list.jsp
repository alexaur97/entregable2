<%--
 * list.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="areas" id="area"
	requestURI="${requestURI}">
	<display:column titleKey="area.name"  property="name" />
	<
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column titleKey="area.edit">
		<a href="area/administrator/edit.do?areaId=${area.id}">
		<spring:message code="area.edit" />
		</a>
		</display:column>
		</security:authorize>
		
	
</display:table>
		<security:authorize access="hasRole('ADMINISTRATOR')">
		<acme:cancel url="/area/administrator/create.do" code="area.create"/>
		</security:authorize>

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

<display:table pagesize="5" name="parades" id="parade"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="parade.title"  property="title" />
	<display:column titleKey="parade.description" property="description" />
	<display:column titleKey="parade.moment" property="moment" />
	<display:column titleKey="parade.ticker" property="ticker" />
	<display:column titleKey="parade.mode" property="mode" />
	<display:column titleKey="parade.status" property="status" />
	
	
		<security:authorize access="hasRole('CHAPTER')">
	<display:column titleKey="parade.accept">
	<jstl:if test="${parade.status==s}">
			<acme:cancel url="/chapter/parade/accept.do?paradeId=${parade.id}" code="parade.accept" />
		</jstl:if>
		
		</display:column>
		<display:column titleKey="parade.reject">
	<jstl:if test="${parade.status==s}">
			<acme:cancel url="/chapter/parade/reject.do?paradeId=${parade.id}" code="parade.reject" />
		</jstl:if>
		
		</display:column>
		</security:authorize>
		
	
</display:table>

<br>
<br>
<br>

	<security:authorize access="hasRole('CHAPTER')">
	<jstl:if test="${empty chapter.area}">
		<spring:message code="parade.chapter.addArea" />
		<acme:button url="/chapter/assign.do" code="parade.addArea"/>
		</jstl:if>
</security:authorize>

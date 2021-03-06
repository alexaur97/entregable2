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

<h3>
	<spring:message code="history.inceptionRecord" />
</h3>

<display:table name="inceptionRecords" id="inceptionRecord"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="inceptionRecord.pictures" > <img src="${inceptionRecord.pictures}" alt="floatLogo" height="200" width="350"/></display:column>
	<display:column titleKey="inceptionRecord.title" property="title" />
	<display:column titleKey="inceptionRecord.description" property="description" />
	<display:column titleKey = "history.display" >
		<a href="inceptionRecord/brotherhood/display.do?inceptionRecordId=${inceptionRecord.id}">
		<spring:message code="history.display" />
		</a>
	</display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey = "inceptionRecord.edit" >
		<a href="inceptionRecord/brotherhood/edit.do?inceptionRecordId=${inceptionRecord.id}">
		<spring:message code="history.edit" />
		</a>
	</display:column>
	</security:authorize>
		
</display:table>

<h3>
	<spring:message code="history.legalRecord" />
</h3>

<display:table name="legalRecords" id="legalRecord"
	requestURI="${requestURI}" pagesize="5">
	<display:column titleKey="legalRecord.title" property="title" />
	<display:column titleKey="legalRecord.description" property="description" />
	<display:column titleKey="legalRecord.legalName" property="legalName" />
	<display:column titleKey="legalRecord.vatNumber" property="vatNumber" />
	<display:column titleKey="legalRecord.laws" property="laws" />
	<display:column titleKey = "history.display" >
		<a href="legalRecord/brotherhood/display.do?legalRecordId=${legalRecord.id}">
		<spring:message code="history.display" />
		</a>
	</display:column>
		<security:authorize access="hasRole('BROTHERHOOD')">
	<display:column titleKey = "history.edit" >
		<a href="legalRecord/brotherhood/edit.do?legalRecordId=${legalRecord.id}">
		<spring:message code="history.edit" />
		</a>
	</display:column>
	</security:authorize>
	</display:table>
	<a href="legalRecord/brotherhood/create.do"><spring:message code="history.legalRecord.create" /></a>

<h3>
	<spring:message code="history.LinkRecord" />
</h3>

<display:table name="linkRecords" id="linkRecord"
	requestURI="${requestURI }" pagesize="5">
	<display:column titleKey="linkRecord.title" property="title" />
	<display:column titleKey="linkRecord.description" property="description" />
	<display:column titleKey="linkRecord.brotherhood" property="brotherhood.title" />	
	<display:column titleKey = "history.display" >
		<a href="linkRecord/brotherhood/display.do?linkRecordId=${linkRecord.id}">
		<spring:message code="history.display" />
		</a>
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
<display:column titleKey = "history.edit" >
		<a href="linkRecord/brotherhood/edit.do?linkRecordId=${linkRecord.id}">
		<spring:message code="history.edit" />
		</a>
	</display:column>		
	</security:authorize>
</display:table>
<a href="linkRecord/brotherhood/create.do"><spring:message code="history.linkRecord.create" /></a>

<h3>
	<spring:message code="history.MiscellaneousRecord" />
</h3>

<display:table name="miscellaneousRecords" id="miscellaneousRecord"
	requestURI="${requestURI }" pagesize="5">
	<display:column titleKey="miscellaneousRecord.title" property="title" />
	<display:column titleKey="miscellaneousRecord.description" property="description" />
	<display:column titleKey = "history.display" >
		<a href="miscellaneousRecord/brotherhood/display.do?miscellaneousRecordId=${miscellaneousRecord.id}">
		<spring:message code="history.display" />
		</a>
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
<display:column titleKey = "history.edit" >
		<a href="miscellaneousRecord/brotherhood/edit.do?miscellaneousRecordId=${miscellaneousRecord.id}">
		<spring:message code="history.edit" />
		</a>
	</display:column>		
	</security:authorize>
</display:table>
<a href="miscellaneousRecord/brotherhood/create.do"><spring:message code="history.miscellaneousRecord.create" /></a>

<h3>
	<spring:message code="history.periodRecord" />
</h3>

<display:table name="periodRecords" id="periodRecord"
	requestURI="${requestURI }" pagesize="5">
	<display:column titleKey="periodRecord.title" property="title" />
	<display:column titleKey="periodRecord.description" property="description" />
	<display:column titleKey="periodRecord.startYear" property="startYear" />
	<display:column titleKey="periodRecord.endYear" property="endYear" />
	<display:column titleKey="periodRecord.pictures" > <img src="${periodRecord.pictures}" alt="floatLogo" height="200" width="350"/></display:column>
		<display:column titleKey = "history.display" >
		<a href="periodRecord/brotherhood/display.do?periodRecordId=${periodRecord.id}">
		<spring:message code="history.display" />
		</a>
	</display:column>
	<security:authorize access="hasRole('BROTHERHOOD')">
<display:column titleKey = "history.edit" >
		<a href="periodRecord/brotherhood/edit.do?periodRecordId=${periodRecord.id}">
		<spring:message code="history.edit" />
		</a>
	</display:column>		
	</security:authorize>
</display:table>
<a href="periodRecord/brotherhood/create.do"><spring:message code="history.periodRecord.create" /></a>

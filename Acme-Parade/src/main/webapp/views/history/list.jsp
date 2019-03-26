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

<display:table pagesize="5" name="inceptionRecords" id="inceptionRecord"
	requestURI="${requestURI }">
	<display:column titleKey="inceptionRecord.pictures" > <img src="${inceptionRecord.pictures}" alt="floatLogo" height="200" width="350"/></display:column>
	<display:column titleKey="inceptionRecord.title" property="title" />
	<display:column titleKey="inceptionRecord.description" property="description" />		
</display:table>

<h3>
	<spring:message code="history.legalRecord" />
</h3>

<display:table pagesize="5" name="legalRecords" id="legalRecord"
	requestURI="${requestURI }">
	<display:column titleKey="legalRecord.title" property="title" />
	<display:column titleKey="legalRecord.description" property="description" />
	<display:column titleKey="legalRecord.legalName" property="legalName" />
	<display:column titleKey="legalRecord.vatNumber" property="vatNumber" />
	<display:column titleKey="legalRecord.laws" property="laws" />

	</display:table>
	


<h3>
	<spring:message code="history.LinkRecord" />
</h3>

<display:table pagesize="5" name="linkRecords" id="linkRecord"
	requestURI="${requestURI }">
	<display:column titleKey="linkRecord.title" property="title" />
	<display:column titleKey="linkRecord.description" property="description" />
	<display:column titleKey="linkRecord.brotherhood" property="brotherhood.title" />	
	</display:table>



<h3>
	<spring:message code="history.MiscellaneousRecord" />
</h3>

<display:table pagesize="5" name="miscellaneousRecords" id="miscellaneousRecord"
	requestURI="${requestURI }">
	<display:column titleKey="miscellaneousRecord.title" property="title" />
	<display:column titleKey="miscellaneousRecord.description" property="description" />
	</display:table>



<h3>
	<spring:message code="history.periodRecord" />
</h3>

<display:table pagesize="5" name="periodRecords" id="periodRecord"
	requestURI="${requestURI }">
	<display:column titleKey="periodRecord.title" property="title" />
	<display:column titleKey="periodRecord.description" property="description" />
	<display:column titleKey="periodRecord.startYear" property="startYear" />
	<display:column titleKey="periodRecord.endYear" property="endYear" />
	<display:column titleKey="periodRecord.pictures" > <img src="${periodRecord.pictures}" alt="floatLogo" height="200" width="350"/></display:column>
		</display:table>



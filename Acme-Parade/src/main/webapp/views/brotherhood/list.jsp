<%--
 * list.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
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

<display:table pagesize="5" name="brotherhoods" id="brotherhood"
	requestURI="${requestURI }" class="displaytag table">
	<display:column titleKey="brotherhood.photo" > <img src="${brotherhood.photo}" alt="BrotherhoodLogo" height="200" width="350"/></display:column>
	<display:column titleKey="brotherhood.title" property="title" />
	<display:column titleKey="brotherhood.establishmentDate" property="establishmentDate" />
	<display:column titleKey="brotherhood.address" property="address" />
			<display:column titleKey="brotherhood.area" property="area.name" />
	
	<display:column titleKey="brotherhood.members">
		<acme:cancel url="/member/listByBrotherhood.do?brotherhoodId=${brotherhood.id}" code="brotherhood.members" />
	</display:column>
	<display:column titleKey="brotherhood.parades">
		<acme:cancel url="/parade/listByBrotherhood.do?brotherhoodId=${brotherhood.id}" code="brotherhood.parades" />
	</display:column>
	<display:column titleKey="brotherhood.floats">
		<acme:cancel url="/float/listByBrotherhood.do?brotherhoodId=${brotherhood.id}" code="brotherhood.floats" />
	</display:column>
	<display:column titleKey="brotherhood.history">
		<acme:cancel url="/history/list.do?brotherhoodId=${brotherhood.id}" code="brotherhood.history" />
	</display:column>
	
	<display:column titleKey="brotherhood.display">
		<acme:cancel url="/brotherhood/display.do?brotherhoodId=${brotherhood.id}" code="brotherhood.display" />
	</display:column>
	
	<security:authorize access="hasRole('BROTHERHOOD')">
		<acme:cancel url="/brotherhood/parade/create.do" code="parade.create"/>
		</security:authorize>
	
</display:table>

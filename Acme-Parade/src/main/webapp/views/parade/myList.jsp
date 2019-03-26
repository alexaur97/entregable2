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
	<spring:message code="parade.submittedParades" />
</h3>

<display:table pagesize="5" name="paradesSubmitted" id="parade"
	requestURI="${requestURI}">
	<display:column style="color:grey" titleKey="parade.title"  property="title" />
	<display:column style="color:grey" titleKey="parade.description" property="description" />
	<display:column style="color:grey" titleKey="parade.moment" property="moment" />
	<display:column style="color:grey" titleKey="parade.ticker" property="ticker" />
	<display:column style="color:grey" titleKey="parade.mode" property="mode" />
	<security:authorize access="hasRole('BROTHERHOOD')">
	
	<display:column titleKey="parade.show">
		<a href="brotherhood/parade/show.do?paradeId=${parade.id}">
		<spring:message code="parade.show" />
		</a>
	</display:column>
	<display:column titleKey="parade.paths">
		<a href="path/listByParade.do?paradeId=${parade.id}">
		<spring:message code="parade.paths" />
		</a>
	</display:column>
		<display:column titleKey="parade.copy">
		<a href="brotherhood/parade/copy.do?paradeId=${parade.id}">
		<spring:message code="parade.copy" />
		</a>
	</display:column>
	
	<display:column titleKey="parade.edit">
		<a href="brotherhood/parade/edit.do?paradeId=${parade.id}">
		<spring:message code="parade.edit" />
		</a>
	</display:column>
	</security:authorize>
</display:table>

<h3>
	<spring:message code="parade.acceptedParades" />
</h3>

<display:table pagesize="5" name="paradesAccepted" id="parade"
	requestURI="${requestURI}">
	<display:column style="color:green"  titleKey="parade.title"  property="title" />
	<display:column style="color:green"  titleKey="parade.description" property="description" />
	<display:column style="color:green"  titleKey="parade.moment" property="moment" />
	<display:column style="color:green"  titleKey="parade.ticker" property="ticker" />
	<display:column style="color:green"  titleKey="parade.mode" property="mode" />	
	<security:authorize access="hasRole('BROTHERHOOD')">	
	
	
			
	<display:column titleKey="parade.show">
		<a href="brotherhood/parade/show.do?paradeId=${parade.id}">
		<spring:message code="parade.show" />
		</a>
	</display:column>
	<display:column titleKey="parade.paths">
		<a href="path/listByParade.do?paradeId=${parade.id}">
		<spring:message code="parade.paths" />
		</a>
	</display:column>
	<display:column titleKey="parade.copy">
		<a href="brotherhood/parade/copy.do?paradeId=${parade.id}">
		<spring:message code="parade.copy" />
		</a>
	</display:column>
	
		<display:column titleKey="parade.edit">
		<a href="brotherhood/parade/edit.do?paradeId=${parade.id}">
		<spring:message code="parade.edit" />
		</a>
	</display:column>
	
	</security:authorize>
</display:table>

<h3>
	<spring:message code="parade.rejectedParades" />
</h3>

<display:table pagesize="5" name="paradesRejected" id="parade"
	requestURI="${requestURI}">
	<display:column style="color:red" titleKey="parade.title"  property="title" />
	<display:column style="color:red" titleKey="parade.description" property="description" />
	<display:column style="color:red" titleKey="parade.moment" property="moment" />
	<display:column style="color:red" titleKey="parade.ticker" property="ticker" />
	<display:column style="color:red" titleKey="parade.mode" property="mode" />
		
	<security:authorize access="hasRole('BROTHERHOOD')">	
	
	
			
	<display:column titleKey="parade.show">
		<a href="brotherhood/parade/show.do?paradeId=${parade.id}">
		<spring:message code="parade.show" />
		</a>
	</display:column>
	<display:column titleKey="parade.copy">
		<a href="brotherhood/parade/copy.do?paradeId=${parade.id}">
		<spring:message code="parade.copy" />
		</a>
	</display:column>
	
		<display:column titleKey="parade.edit">
		<a href="brotherhood/parade/edit.do?paradeId=${parade.id}">
		<spring:message code="parade.edit" />
		</a>
	</display:column>
	</security:authorize>
</display:table>


<h3>
	<spring:message code="parade.clearedParades" />
</h3>

<display:table pagesize="5" name="paradesCleared" id="parade"
	requestURI="${requestURI}">
	<display:column style="color:blue" titleKey="parade.title"  property="title" />
	<display:column style="color:blue" titleKey="parade.description" property="description" />
	<display:column style="color:blue" titleKey="parade.moment" property="moment" />
	<display:column style="color:blue" titleKey="parade.ticker" property="ticker" />
	<display:column style="color:blue" titleKey="parade.mode" property="mode" />
		
	<security:authorize access="hasRole('BROTHERHOOD')">	
	
	
			
	<display:column titleKey="parade.show">
		<a href="brotherhood/parade/show.do?paradeId=${parade.id}">
		<spring:message code="parade.show" />
		</a>
	</display:column>
	<display:column titleKey="parade.paths">
		<a href="path/listByParade.do?paradeId=${parade.id}">
		<spring:message code="parade.paths" />
		</a>
	</display:column>
	

	<display:column titleKey="parade.copy">
		<a href="brotherhood/parade/copy.do?paradeId=${parade.id}">
		<spring:message code="parade.copy" />
		</a>
	</display:column>
	
		<display:column titleKey="parade.edit">
		<a href="brotherhood/parade/edit.do?paradeId=${parade.id}">
		<spring:message code="parade.edit" />
		</a>
	</display:column>
			
	
	</security:authorize>
</display:table>
				
<acme:button url="/brotherhood/parade/create.do" code="parade.create"/>



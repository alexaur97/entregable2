<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" width="450" alt="Acme Madrug� Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configurationParameters/administrator/edit.do"><spring:message code="master.page.administrator.config" /></a></li>

					<li><a href="position/administrator/list.do"><spring:message code="master.page.administrator.position" /></a></li>

					<li><a href="stats/administrator/display.do"><spring:message code="master.page.administrator.stats" /></a></li>

				</ul>
			</li>
			<li><a><spring:message	code="master.page.area" /></a>
			<ul>
				<li class="arrow"></li>
					<li><a href="area/administrator/list.do"><spring:message code="master.page.all" /></a></li>
			</ul>
			</li>
			
		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="brotherhood/list.do"><spring:message code="master.page.brotherhoods" /></a>
				<li><a class="fNiv"><spring:message	code="master.page.signup" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="brotherhood/create.do"><spring:message code="master.page.signup.brotherhood" /></a></li>
					<li><a href="member/create.do"><spring:message code="master.page.signup.member" /></a></li>
					<li><a href="chapter/create.do"><spring:message code="master.page.signup.chapter" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
		
					<li><a class="fNiv"><spring:message	code="master.page.brotherhoods" /></a>
				<ul>
					<li class="arrow"></li>
			<li><a href="brotherhood/list.do"><spring:message code="master.page.allbrotherhoods" /></a></li>

		<security:authorize access="hasRole('MEMBER')">
			<li><a href="brotherhood/member/myList.do"><spring:message code="master.page.mybrotherhoods" /></a></li>
		</security:authorize>				</ul>
			</li>

		<security:authorize access="hasRole('MEMBER')">
			<li><a href="request/member/list.do"><spring:message code="master.page.requests" /></a></li>
			<li><a href="finder/member/view.do"><spring:message code="master.page.finder" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a href="brotherhood/parade/list.do"><spring:message code="master.page.parades" /></a></li>

			<li><a href="brotherhood/parade/myList.do"><spring:message code="master.page.myparades" /></a></li>

		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a href="history/brotherhood/myList.do"><spring:message code="master.page.history" /></a></li>
		</security:authorize>
		
				<security:authorize access="hasRole('BROTHERHOOD')">

			<li><a href="member/brotherhood/list.do"><spring:message code="master.page.mymembers" /></a></li>
			<li><a href="request/brotherhood/list.do"><spring:message code="master.page.requests" /></a></li>
			<li><a href="brotherhood/float/list.do"><spring:message code="master.page.floats" /></a></li>
			
			<jstl:choose>
			<jstl:when test="${brotherhood.area eq null}">
			<li><a href="brotherhood/addArea.do"><spring:message code="masterpage.addarea" /></a></li>
	</jstl:when>
		<jstl:otherwise>
	<li style="color: white; font-size: 12px;" >AREA: ${brotherhood.area.name}</li>
		</jstl:otherwise>
		</jstl:choose>
		</security:authorize>
		
		<security:authorize access="hasRole('CHAPTER')">
			<li><a href="chapter/parade/list.do"><spring:message code="master.page.parades" /></a></li>
			<jstl:choose>
			<jstl:when test="${chapter.area eq NULL}">
					<li><a href="chapter/assign.do"><spring:message code="master.page.assign" /></a></li>
	</jstl:when>
		<jstl:otherwise>
	<li style="color: white; font-size: 12px;" >AREA: ${chapter.area.name}</li>
		</jstl:otherwise>
		</jstl:choose>
		</security:authorize>
			<li>
				<a class="fNiv">
					<spring:message code="master.page.profile" />
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
							<security:authorize access="isAuthenticated()">
					<li><a href="actor/edit.do"><spring:message code="master.page.editPersonalData" /></a></li>
		                   </security:authorize>
		                    <security:authorize access="hasRole('ADMINISTRATOR')">
		            <li><a href="administrator/administrator/create.do"><spring:message code="master.page.signup.admin" /></a></li>
		                    </security:authorize>
		                   
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

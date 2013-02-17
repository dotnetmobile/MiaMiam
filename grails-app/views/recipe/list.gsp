
<%@ page import="miamiam.Recipe" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recipe.label', default: 'Recipe')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-recipe" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav roundedBorder" role="navigation" >
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<sec:ifAllGranted roles="ROLE_ADMIN">
					<li><g:link class="create" action="create"><g:message code="recipe.new.label" args="[entityName]" /></g:link></li>
				</sec:ifAllGranted>
			    <div id="quickSearch">
			    	<g:form action="search" >
			    		<div class="inputSearch">  
 				        	<input type="text" name="q" placeholder="Trouver" value="${params.q}"/><button type="submit" title="${message(code: 'recipe.search.label')}" class="icon-search btn gray" />
		           		</div>	
			        </g:form>
			    </div>				
			</ul>
		</div>
		<div id="list-recipe" class="content scaffold-list roundedBorder" role="main">
			<h1>
				<g:message code="recipe.list.label"/>
			</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>					
						<g:sortableColumn property="name" title="${message(code: 'recipe.name.label', default: 'Name')}" />					
						<th><g:message code="recipe.category.label" default="Category" /></th>				
					</tr>
				</thead>
				<tbody>
				<g:each in="${recipeInstanceList}" status="i" var="recipeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>
							<g:if test="${recipeInstance?.photoSteps}">							
 								<img src="${createLink(controller:'photoStep', action:'viewImage', id:"${recipeInstance?.photoSteps?.toArray()[0]?.id}")}" width="50" height="50" class="verticalyCentered"/> 								  
							</g:if>
							<g:link action="show" id="${recipeInstance.id}" class="verticalyCentered" style="font-size:1.25em;">${fieldValue(bean: recipeInstance, field: "name")}</g:link>
						</td>
						<td class="verticalyCentered">${fieldValue(bean: recipeInstance, field: "category.name")}
						</td>					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${recipeInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

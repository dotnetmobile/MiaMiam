
<%@ page import="miamiam.Recipe" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recipe.label', default: 'Recipe')}" />
		<g:set var="recipeName" value="${recipeInstance?.name}"/>
		<title><g:message code="recipe.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-recipe" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav roundedBorder" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="recipe.list.label" args="[entityName]" /></g:link></li>
				<sec:ifAllGranted roles="ROLE_ADMIN">
					<li><g:link class="create" action="create"><g:message code="recipe.new.label" args="[entityName]" /></g:link></li>
				</sec:ifAllGranted>
			</ul>
		</div>
		<div id="show-recipe" class="content scaffold-show" role="main">
			<h1><g:message code="recipe.show.label" args="[recipeName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list recipe">
				<g:if test="${recipeInstance?.category}">
				<li class="fieldcontain">
					<span id="category-label" class="property-label"><g:message code="recipe.category.label" default="Category" /></span>
					
					<span class="property-value" aria-labelledby="category-label"><g:fieldValue bean="${recipeInstance?.category}" field="name" /></span>  
					
				</li>
				</g:if>

				<g:if test="${recipeInstance?.ingredient}">
				<li class="fieldcontain">
					<span id="ingredients-label" class="property-label"><g:message code="recipe.ingredients.label" default="Ingredients" /></span>
					<span class="property-value" aria-labelledby="ingredients-label">${recipeInstance?.ingredientAsHTML()}</span>  
				</li>
				</g:if>

				<g:if test="${recipeInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="recipe.description.label" default="Description" /></span>
					<span class="property-value" aria-labelledby="ingredients-label">${recipeInstance?.recipeAsHTML()}</span>  
				</li>
				</g:if>
									
				<g:if test="${recipeInstance?.photoSteps}">
				<li class="fieldcontain">
					<span id="photoSteps-label" class="property-label"><g:message code="recipe.photoSteps.label" default="Photos" /></span>				
					<g:each in="${recipeInstance.photoSteps}" var="p">
						<span class="property-value" aria-labelledby="photoSteps-label"><g:link controller="photoStep" action="show" id="${p.id}"><img src="${createLink(controller:'photoStep', action:'viewImage', id:"${p?.id}")}" width="250" height="200"/></g:link></span>
					</g:each>
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons roundedBorder">
					<g:hiddenField name="id" value="${recipeInstance?.id}" />
					<sec:ifAllGranted roles="ROLE_ADMIN">
						<g:link class="edit" action="edit" id="${recipeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</sec:ifAllGranted>						
				</fieldset>
			</g:form>
		</div>
	</body>
</html>

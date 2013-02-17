<%@ page import="miamiam.Recipe" %>



<div class="fieldcontain ${hasErrors(bean: recipeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="recipe.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${recipeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recipeInstance, field: 'category', 'error')} required">
	
	<label for="category">
		<g:message code="recipe.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label>

	<g:select id="category" name="category.id" from="${miamiam.Category.list()}" optionKey="id" optionValue="name" required="" value="${recipeInstance?.category?.id}" class="many-to-one"/>
	<g:link controller="category" action="create" params="['recipe.id': recipeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'category.label', default: 'Category')])}</g:link>
</div>

<div class="fieldcontain ${hasErrors(bean: recipeInstance, field: 'ingredient', 'error')} required">
	<label for="ingredient">
		<g:message code="recipe.ingredients.label" default="Ingredients" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="ingredient" cols="40" rows="5" maxlength="3000" required="" value="${recipeInstance?.ingredient}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recipeInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="recipe.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="3000" required="" value="${recipeInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recipeInstance, field: 'photoSteps', 'error')} ">
	<label for="photoSteps">
		<g:message code="recipe.photoSteps.label" default="Photos" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${recipeInstance?.photoSteps?}" var="p">
    <li><g:link controller="photoStep" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="photoStep" action="create" params="['recipe.id': recipeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'photoStep.label', default: 'PhotoStep')])}</g:link>
</li>
</ul>

</div>


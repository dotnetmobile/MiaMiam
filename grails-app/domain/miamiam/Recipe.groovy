package miamiam

class Recipe {

	String name
	String ingredient
	String description
	Category category

	static searchable = {
		photoSteps component: true
		category component: true
	}

	static belongsTo = Category
	static hasMany = [ photoSteps : PhotoStep]
	
	
    static constraints = {
		name(nullable: false, size: 1..100, blank: false)
		ingredient(size: 1..3000, nullable:true)
		description(size: 1..3000, blank: false)
	}


		
	String toString() {
		return name
	}

	String ingredientAsHTML() {
		return ingredient.encodeAsHTML().replaceAll("\n", "<br>").replaceAll("\"","")
	}

	String recipeAsHTML() {
		return description.encodeAsHTML().replaceAll("\n", "<br>").replaceAll("\"","")
	}
}

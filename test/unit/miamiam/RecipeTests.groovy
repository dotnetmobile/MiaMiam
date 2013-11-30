package miamiam



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Recipe)
class RecipeTests {

    void testSingleRecipe() {
		Category successfulCategory = new Category(name:'mot successful')
		
		Recipe recipe = new Recipe(name:'pizza',ingredient:'melt, eggs, milk, salt, ...',description:'call mama for preparation', category:successfulCategory)
		recipe.save()
		
       assert Recipe.count == 1
	   
	   assert recipe.name == 'pizza'
	   assert recipe.ingredient == 'melt, eggs, milk, salt, ...'
	   assert recipe.description == 'call mama for preparation'
	   assert recipe.category == successfulCategory
    }
}

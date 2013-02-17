package miamiam

class Category {
	
	String name
	String description


  	static searchable = {
		root false	
	}
	
	static hasMany = [ recipes: Recipe]
	
    static constraints = {
		name(size: 1..100, blank: false, unique: true)
		description(size: 1..200, blank: true)
    }
	
	String toString() {
		return name
	}
}

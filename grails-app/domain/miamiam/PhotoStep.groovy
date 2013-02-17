package miamiam

class PhotoStep {

	String name
	String description
	byte[] photo
	Recipe recipe
	
	static searchable = {
		root false
	}

	static hasOne = [recipe : Recipe]
	
    static constraints = {
		name(size: 1..100, blank: false, unique: false)
		description(size: 1..200, blank: true)
		photo(nullable:false, maxSize:10000000)
    }
	
	String toString() {
		return name
	}
}
